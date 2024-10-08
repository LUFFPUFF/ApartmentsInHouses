package apartment.in.houses.util.orm.manager.entitymanager;

import apartment.in.houses.util.orm.manager.entitymanager.util.EntityManagerUtil;
import apartment.in.houses.util.orm.manager.entitymanager.util.FormatSQLUtil;
import apartment.in.houses.util.orm.manager.querymanager.query.Query;
import apartment.in.houses.util.orm.manager.querymanager.query.QueryImpl;
import apartment.in.houses.util.orm.session.interf.Session;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private final Session session;

    public EntityManagerImpl(Session session) {
        this.session = session;
    }

    @Override
    public <T> void persist(T entity) throws SQLException {
        Class<?> entityClass = entity.getClass();
        String tableName = EntityManagerUtil.getTableName(entityClass);
        List<String> columnNames = EntityManagerUtil.getColumnNames(entityClass);
        List<Object> values = EntityManagerUtil.getValues(entity);

        String sql = FormatSQLUtil.insert(tableName, columnNames);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            for (int i = 1; i <= values.size(); i++) {
                statement.setObject(i, values.get(i - 1));
            }
            statement.executeUpdate();
        }
    }

    @Override
    public <T> void remove(T entity) throws SQLException {
        Class<?> entityClass = entity.getClass();

        String primaryKeyName = EntityManagerUtil.getPrimaryKeyName(entityClass);
        Object primaryKeyValue = EntityManagerUtil.getPrimaryKeyValue(entity, primaryKeyName);

        String tableName = EntityManagerUtil.getTableName(entityClass);
        String sql = FormatSQLUtil.delete(tableName, primaryKeyName);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, primaryKeyValue);
            statement.executeUpdate();
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object id) throws SQLException {
        String tableName = EntityManagerUtil.getTableName(entityClass);
        String primaryKeyName = EntityManagerUtil.getPrimaryKeyName(entityClass);

        String sql = FormatSQLUtil.get(tableName, primaryKeyName);

        try(PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance();
                    EntityManagerUtil.populateEntityFields(entity, resultSet, entityClass);
                    return entity;
                } else {
                    return null;
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException("Error finding entity: " + e);
        }
    }

    @Override
    public <T> Query<T> createQuery(String sql, Class<T> entityClass) {
        return new QueryImpl<>(sql, session, entityClass);
    }
}

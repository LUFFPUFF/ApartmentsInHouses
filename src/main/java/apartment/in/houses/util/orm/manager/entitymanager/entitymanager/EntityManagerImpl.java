package apartment.in.houses.util.orm.manager.entitymanager.entitymanager;

import apartment.in.houses.util.XMLparser.ConfiguratorLoader;
import apartment.in.houses.util.orm.annotation.Entity;
import apartment.in.houses.util.orm.annotation.GeneratedValue;
import apartment.in.houses.util.orm.manager.entitymanager.util.*;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.query.Query;
import apartment.in.houses.util.orm.manager.querymanager.query.QueryImpl;
import apartment.in.houses.util.orm.session.interf.Session;

import java.lang.reflect.Field;
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

        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }

        String tableName = ConfiguratorLoader.getTableName(entityClass);
        List<String> columnNames = TableUtil.getColumnNames(entityClass);
        List<Object> values = FieldUtil.getValues(entity);

        Field primaryKeyField = FieldUtil.getPrimaryKeyField(entityClass);
        GeneratedValue generatedValue = primaryKeyField.getAnnotation(GeneratedValue.class);

        if (generatedValue != null) {
            switch (generatedValue.strategy()) {
                case IDENTITY:
                    columnNames.remove(primaryKeyField.getName());
                    values.remove(FieldUtil.getPrimaryKeyValue(entity, primaryKeyField.getName()));
                    break;
                case SEQUENCE:
                    Long nextId = GeneratedValueUtil.getNextSequenceValue(session);
                    FieldUtil.setFieldValue(entity, primaryKeyField, nextId);
                    break;
                case TABLE:
                    Long tableId = GeneratedValueUtil.getNextTableValue(session);
                    FieldUtil.setFieldValue(entity, primaryKeyField, tableId);
                    break;
            }
        }

        String sql = FormatSQLUtil.insert(tableName, columnNames);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            for (int i = 1; i <= values.size(); i++) {
                statement.setObject(i, values.get(i - 1));
            }
            statement.executeUpdate();
        }

        RelationshipUtil.processRelationships(entity, entityClass, this, session);
    }

    @Override
    public <T> void remove(T entity) throws SQLException {
        Class<?> entityClass = entity.getClass();

        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }

        String primaryKeyName = FieldUtil.getPrimaryKeyName(entityClass);
        Object primaryKeyValue = FieldUtil.getPrimaryKeyValue(entity, primaryKeyName);

        String tableName = ConfiguratorLoader.getTableName(entityClass);
        String sql = FormatSQLUtil.delete(tableName, primaryKeyName);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, primaryKeyValue);
            statement.executeUpdate();
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object id) throws SQLException {
        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }


        String tableName = ConfiguratorLoader.getTableName(entityClass);
        String primaryKeyName = FieldUtil.getPrimaryKeyName(entityClass);

        String sql = FormatSQLUtil.get(tableName, primaryKeyName);

        try(PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance();
                    ResultSetUtil.populateEntityFields(entity, resultSet, entityClass, this);
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
        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }
        String processedSQL = RelationshipUtil.processSQLWithRelationships(sql, entityClass);
        return new QueryImpl<>(processedSQL, session, entityClass);
    }

    @Override
    public <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        String sql = criteriaQuery.build();
        return new QueryImpl<>(sql, session, criteriaQuery.getEntityClass());
    }
}

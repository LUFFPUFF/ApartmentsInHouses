package apartment.in.houses.framework.spring.orm.manager.entitymanager.util;

import apartment.in.houses.framework.spring.orm.manager.entitymanager.entitymanager.EntityManager;
import apartment.in.houses.framework.spring.orm.session.interf.Session;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FindUtil {

    public static <T> T findWithEmbeddedId(Class<T> entityClass, Object id, Field primaryKeyField, String tableName, Session session, EntityManager entityManager) throws SQLException {
        List<String> embeddedIdColumnNames = TableUtil.getColumnNames(primaryKeyField.getType());
        String sql = FormatSQLUtil.getWithEmbeddedId(tableName, embeddedIdColumnNames);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            List<Object> embeddedIdValues = FieldUtil.getValues(id);

            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService service = Executors.newFixedThreadPool(processors);

            for (int i = 1; i <= embeddedIdValues.size(); i++) {

                statement.setObject(i, embeddedIdValues.get(i - 1));
            }
            return executeFind(entityClass, statement, entityManager);
        }
    }

    public static <T> T findWithPrimaryKey(Class<T> entityClass, Object id, String tableName, Session session, EntityManager entityManager) throws SQLException {
        String primaryKeyName = FieldUtil.getPrimaryKeyName(entityClass);
        String sql = FormatSQLUtil.get(tableName, primaryKeyName);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            return executeFind(entityClass, statement, entityManager);
        }
    }

    private static <T> T executeFind(Class<T> entityClass, PreparedStatement statement, EntityManager entityManager) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                ResultSetUtil.populateEntityFields(entity, resultSet, entityClass, entityManager);
                return entity;
            } else {
                return null;
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Error populating entity: " + e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

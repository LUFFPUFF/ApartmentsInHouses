package apartment.in.houses.data_access.util.exucutor;

import apartment.in.houses.data_access.util.JDBCUtil.JDBCConnection;
import apartment.in.houses.data_access.util.exucutor.interf.Executor;
import apartment.in.houses.data_access.util.exucutor.util.ExecutorUtil;
import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.XMLparser.XMLParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ExecutorImpl<E, K> implements Executor<E, K> {

    private static final Map<String, String> xmlLoader =
            XMLParser.parse("/Users/nikita/Downloads/ApartmentsInHousesEE/src/main/resources/execute-config.xml");

    @Override
    public boolean executeInsert(E entity) throws SQLException {
        String tableName = ExecutorUtil.getTableName(entity.getClass());
        List<String> columnNames = ExecutorUtil.getColumnNames(entity.getClass());
        List<Object> values = ExecutorUtil.getValues(entity);

        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(xmlLoader.get("insert"),
                            tableName,
                            String.join(",", columnNames),
                            String.join(",", Collections.nCopies(columnNames.size(), "?")))
            );

            for (int i = 1; i <= values.size(); i++) {
                preparedStatement.setObject(i, values.get(i - 1));
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean executeDelete(Class<?> classEntity, K key) {
        String tableName = ExecutorUtil.getTableName(classEntity);
        String primaryKeyName = ExecutorUtil.getPrimaryKeyName(classEntity);

        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(String.format(
                    xmlLoader.get("delete"), tableName, primaryKeyName
            ));

            statement.setObject(1, key);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Логируем успешное удаление
                System.out.println("Запись была успешно удалена.");
            } else {
                // Логируем неуспешное удаление
                System.out.println("Запись не была удалена.");
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean executeUpdate(E entity) throws SQLException {
        String tableName = ExecutorUtil.getTableName(entity.getClass());
        List<String> columnNames = ExecutorUtil.getColumnNames(entity.getClass());
        String primaryKeyName = ExecutorUtil.getPrimaryKeyName(entity.getClass());
        List<Object> values = ExecutorUtil.getValues(entity);

        List<String> setClauses = new ArrayList<>();
        for (String columnName : columnNames) {
            if (!columnName.equals(primaryKeyName)) {
                setClauses.add(columnName + " = ?");
            }
        }
        String setClause = String.join(", ", setClauses);

        try(Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(String.format(xmlLoader.get("update"), tableName, setClause, primaryKeyName)))
        {
            int index = 1;
            for (int i = 0; i < columnNames.size(); i++) {
                if (!columnNames.get(i).equals(primaryKeyName)) {
                    statement.setObject(index++, values.get(i));
                }
            }

            statement.setObject(index, ExecutorUtil.getPrimaryKeyValue(entity, primaryKeyName));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<E> executeGetAll(Class<?> classEntity) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(xmlLoader.get("get_all"), ExecutorUtil.getTableName(classEntity)));
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                E entity  = (E) classEntity.getDeclaredConstructor().newInstance();
                ExecutorUtil.populateEntityFields(entity, resultSet, classEntity);
                entities.add(entity);
            }
            return entities;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public E executeGetId(Class<?> classEntity, K key) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, SQLException {
        String tableName = ExecutorUtil.getTableName(classEntity);
        String primaryKeyName = ExecutorUtil.getPrimaryKeyName(classEntity);

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(xmlLoader.get("get_id"), tableName, primaryKeyName))
                )
        {

            statement.setObject(1, key);
            try (ResultSet resultSet = statement.executeQuery()
                    )
            {
                if (resultSet.next()) {
                    E entity = (E) classEntity.getDeclaredConstructor().newInstance();
                    ExecutorUtil.populateEntityFields(entity, resultSet, classEntity);
                    return entity;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

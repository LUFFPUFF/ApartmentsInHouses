package apartment.in.houses.data_access.util.exucutor.util;

import apartment.in.houses.data_access.util.exucutor.annotation.Id;
import apartment.in.houses.data_access.util.exucutor.annotation.Table;
import apartment.in.houses.data_access.util.exucutor.annotation.Column;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecutorUtil {

    /**
     * Получение имени таблицы из аннотации @Table
     * @param classEntity
     * @return
     */
    public static String getTableName(Class<?> classEntity) {
        Table annotation = classEntity.getAnnotation(Table.class);
        if (annotation != null) {
            return annotation.name();
        } else {
            throw new IllegalArgumentException("Entity class must have @Table annotation: " + classEntity.getName());
        }
    }

    /**
     * Получение списка имен столбцов из аннотаций @Column
     * @param classEntity
     * @return
     */
    public static List<String> getColumnNames(Class<?> classEntity) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : classEntity.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                columnNames.add(columnAnnotation.name());
            }
        }
        return columnNames;
    }

    /**
     * Получение списка значений полей
     * @param entity
     * @return
     * @throws SQLException
     */
    public static <T> List<Object> getValues(T entity) throws SQLException {
        List<Object> values = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                values.add(field.get(entity));
            } catch (IllegalAccessException e) {
                throw new SQLException("Error getting field value: " + e.getMessage());
            }
        }
        return values;
    }

    /**
     * Получение значения PK
     * @param entity
     * @param primaryKeyName
     * @return
     * @throws SQLException
     */
    public static <T> Object getPrimaryKeyValue(T entity, String primaryKeyName) throws SQLException {
        try {
            Field primaryKeyField = entity.getClass().getDeclaredField(primaryKeyName);
            primaryKeyField.setAccessible(true);
            return primaryKeyField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new SQLException("Error getting primary key value: " + e.getMessage());
        }
    }

    /**
     * Получение имени PK
     * @param classEntity
     * @return
     */
    public static String getPrimaryKeyName(Class<?> classEntity) {
        for (Field field : classEntity.getDeclaredFields()) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (idAnnotation != null) {
                return field.getName();
            }
        }
        throw new IllegalArgumentException("Entity class must have a field annotated with @Id: " + classEntity.getName());
    }

    /**
     * Отвечает за заполнение полей объекта из ResultSet
     * @param entity
     * @param resultSet
     * @param entityClass
     * @param <T>
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> void populateEntityFields(T entity, ResultSet resultSet, Class<?> entityClass) throws SQLException, NoSuchFieldException, IllegalAccessException {
        List<String> columnNames = getColumnNames(entityClass);
        for (int i = 1; i <= columnNames.size(); i++) {
            String columnName = columnNames.get(i - 1);
            Field field = Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class) && f.getAnnotation(Column.class).name().equals(columnName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException("Field with column name " + columnName + " not found"));

            field.setAccessible(true);
            field.set(entity, resultSet.getObject(i, field.getType()));
        }
    }
}

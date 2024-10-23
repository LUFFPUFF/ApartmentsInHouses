package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FieldUtil {

        /**
         * Извлекает значения всех полей сущности.
         */
        public static <T> List<Object> getValues(T entity) throws SQLException {
            List<Object> values = new ArrayList<>();
            Class<?> entityClass = entity.getClass();

            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    Object value = field.get(entity);
                    values.add(value);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error extracting field value: " + e.getMessage(), e);
                }
            }

            return values;
        }


    /**
     * Установка значения поля в объекте
     */
    public static <T> void setFieldValue(T entity, Field field, Object value) throws SQLException {
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new SQLException("Error setting field value: " + e.getMessage());
        }
    }

    /**
     * Получение значения поля
     */
    public static <T> Object getFieldValue(T entity, Field fieldName) throws SQLException {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName.getName());
            field.setAccessible(true);
            Object value = field.get(entity);

            if (field.getType().isAnnotationPresent(Entity.class)) {
                if (value != null) {
                    Field primaryKeyField = getPrimaryKeyField(value.getClass());
                    primaryKeyField.setAccessible(true);
                    return primaryKeyField.get(value);
                } else {
                    return null;
                }
            } else {
                return value;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new SQLException("Error getting field value: " + fieldName, e);
        }
    }


    /**
     * Получение имени PK
     */
    public static String getPrimaryKeyName(Class<?> classEntity) {
        for (Field field : classEntity.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw new IllegalArgumentException("Entity class must have a field annotated with @Id or @EmbeddedId: " + classEntity.getName());
    }

    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
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
            throw new SQLException("Error getting primary key value: " + e.getMessage(), e);
        }
    }


    /**
     * Получение имени столбца внешнего ключа для поля, связанного через OneToMany
     * @param mappedBy имя поля, на которое ссылается связь
     * @param entityClass класс, в котором находится поле
     * @return имя столбца внешнего ключа
     */
    public static String getForeignKeyColumn(String mappedBy, Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getName().equals(mappedBy)) {
                ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class);
                if (manyToOneAnnotation != null) {
                    return field.getName();
                }
            }
        }
        throw new IllegalArgumentException("No field with mappedBy name " + mappedBy + " found in " + entityClass.getName());
    }

    public static String getColumnNameByFieldName(Class<?> entityClass, String fieldName) {
        try {
            Field field = entityClass.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                return columnAnnotation.name();
            } else {
                return field.getName();
            }
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field " + fieldName + " not found in class " + entityClass.getName(), e);
        }
    }

    /**
     * Получение поля, являющегося первичным ключом (PK) в классе сущности.
     * @param entityClass Класс сущности
     * @return Поле, помеченное аннотацией @Id
     */
    public static Field getPrimaryKeyField(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No primary key field annotated with @Id or @EmbeddedId found in " + entityClass.getName());
    }

    public static Field getEmbeddedIdField(Class<?> entityClass) throws SQLException {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(EmbeddedId.class)) {
                return field;
            }
        }
        throw new SQLException("No field annotated with @EmbeddedId found in " + entityClass.getName());
    }



    public static List<String> getEmbeddedIdColumnNames(Field embeddedIdField) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        Class<?> embeddedIdClass = embeddedIdField.getType();

        for (Field field : embeddedIdClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                columnNames.add(columnAnnotation.name());
            } else {
                columnNames.add(field.getName());
            }
        }

        if (columnNames.isEmpty()) {
            throw new SQLException("No columns found for EmbeddedId in " + embeddedIdClass.getName());
        }

        return columnNames;
    }



    public static <T> List<Object> getEmbeddedIdValues(T embeddedIdInstance) throws SQLException {
        List<Object> values = new ArrayList<>();
        if (embeddedIdInstance != null) {
            for (Field embeddedField : embeddedIdInstance.getClass().getDeclaredFields()) {
                embeddedField.setAccessible(true);
                try {
                    Object embeddedValue = embeddedField.get(embeddedIdInstance);
                    values.add(embeddedValue);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error accessing EmbeddedId fields: " + e.getMessage(), e);
                }
            }
        } else {
            throw new SQLException("EmbeddedId is null.");
        }
        return values;
    }


}

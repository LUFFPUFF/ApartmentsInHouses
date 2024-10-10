package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.Entity;
import apartment.in.houses.util.orm.annotation.Id;
import apartment.in.houses.util.orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FieldUtil {

    /**
     * Получение списка значений полей
     */
    public static <T> List<Object> getValues(T entity) throws SQLException {
        List<Object> values = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(entity);

                if (field.getType().isAnnotationPresent(Entity.class)) {
                    if (fieldValue != null) {
                        Field primaryKeyField = getPrimaryKeyField(fieldValue.getClass());
                        primaryKeyField.setAccessible(true);
                        Object foreignKeyValue = primaryKeyField.get(fieldValue);
                        values.add(foreignKeyValue);
                    } else {
                        values.add(null);
                    }
                } else {
                    values.add(fieldValue);
                }
            } catch (IllegalAccessException e) {
                throw new SQLException("Error getting field value: " + e.getMessage());
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
    public static <T> Object getFieldValue(T entity, String fieldName) throws SQLException {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(entity);

            if (field.isAnnotationPresent(ManyToOne.class)) {
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
            Id idAnnotation = field.getAnnotation(Id.class);
            if (idAnnotation != null) {
                return field.getName();
            }
        }
        throw new IllegalArgumentException("Entity class must have a field annotated with @Id: " + classEntity.getName());
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
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No primary key field annotated with @Id found in " + entityClass.getName());
    }
}

package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.ManyToMany;
import apartment.in.houses.util.orm.annotation.ManyToOne;
import apartment.in.houses.util.orm.annotation.OneToMany;
import apartment.in.houses.util.orm.manager.entitymanager.entitymanager.EntityManager;
import apartment.in.houses.util.orm.session.interf.Session;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipUtil {

    public static <T> void processRelationships(T entity, Class<?> entityClass, EntityManager entityManager, Session session) throws SQLException {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                processManyToOneRelationship(entity, field, entityManager);
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                processOneToManyRelationship(entity, field, session, entityManager);
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                processManyToManyRelationship(entity, field);
            }
        }
    }

    public static <T> String processSQLWithRelationships(String sql, Class<T> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                assert sql != null;
                sql = processManyToOneInQuery(sql, field, entityClass);
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                assert sql != null;
                sql = processOneToManyInQuery(sql, field, entityClass);
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                sql = processManyToManyInQuery(sql, field, entityClass);
            }
        }
        return sql;
    }

    private static <T> void processManyToOneRelationship(T entity, Field field, EntityManager entityManager) throws SQLException {
        ManyToOne annotation = field.getAnnotation(ManyToOne.class);
        Class<?> targetEntity = annotation.targetEntity();

        Object relatedEntity = FieldUtil.getFieldValue(entity, field.getName());

        if (relatedEntity != null) {
            Object foreignKeyValue;

            if (relatedEntity.getClass().isPrimitive() ||
                    Number.class.isAssignableFrom(relatedEntity.getClass()) ||
                    relatedEntity instanceof String) {
                foreignKeyValue = relatedEntity;
            } else {
                Field primaryKeyField = FieldUtil.getPrimaryKeyField(relatedEntity.getClass());
                primaryKeyField.setAccessible(true);

                try {
                    foreignKeyValue = primaryKeyField.get(relatedEntity);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error getting primary key value of the related entity: " + e.getMessage(), e);
                }
            }

            Object loadedEntity = entityManager.find(targetEntity, foreignKeyValue);

            if (loadedEntity != null) {
                if (!loadedEntity.equals(relatedEntity)) {
                    FieldUtil.setFieldValue(entity, field, loadedEntity);
                }
            } else {
                throw new SQLException("Related entity not found in the database for foreign key: " + foreignKeyValue);
            }
        } else {
            throw new SQLException("Related entity is null for field: " + field.getName());
        }
    }




    private static <T> void processOneToManyRelationship(T entity, Field field, Session session, EntityManager entityManager) throws SQLException {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Class<?> targetEntity = annotation.targetEntity();
        String mappedBy = annotation.mappedBy();

        String foreignKeyColumn = FieldUtil.getForeignKeyColumn(mappedBy, targetEntity);
        String sql = FormatSQLUtil.getOneToManyQuery(TableUtil.getTableName(targetEntity), foreignKeyColumn);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, FieldUtil.getPrimaryKeyValue(entity, "id"));
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Object> relatedEntities = new ArrayList<>();
                while (resultSet.next()) {
                    Object relatedEntity = targetEntity.getDeclaredConstructor().newInstance();
                    ResultSetUtil.populateEntityFields(relatedEntity, resultSet, targetEntity, entityManager);
                    relatedEntities.add(relatedEntity);
                }
                FieldUtil.setFieldValue(entity, field, relatedEntities);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static <T> void processManyToManyRelationship(T entity, Field field) {
        // TODO Реализация логики для обработки ManyToMany
    }

    private static <T> String processManyToOneInQuery(String sql, Field field, Class<T> entityClass) {
        ManyToOne annotation = field.getAnnotation(ManyToOne.class);
        Class<?> targetEntity = annotation.targetEntity();
        String foreignKeyColumn = TableUtil.getColumnName(field);
        String targetTable = TableUtil.getTableName(targetEntity);
        String targetPrimaryKey = FieldUtil.getPrimaryKeyName(targetEntity);

        String joinClause = FormatSQLUtil.joinClausManyToOne(targetTable, entityClass, foreignKeyColumn, targetPrimaryKey);

        int whereIndex = sql.toUpperCase().indexOf("WHERE");
        if (whereIndex != -1) {
            sql = sql.substring(0, whereIndex) + joinClause + " " + sql.substring(whereIndex);
        } else {
            sql += joinClause;
        }

        return sql;
    }


    private static <T> String processOneToManyInQuery(String sql, Field field, Class<T> entityClass) {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Class<?> targetEntity = annotation.targetEntity();
        String mappedBy = annotation.mappedBy();

        String targetTable = TableUtil.getTableName(targetEntity);
        String foreignKeyColumn = FieldUtil.getColumnNameByFieldName(targetEntity, mappedBy);

        String joinClause = FormatSQLUtil.joinClausOneToMany(targetTable, entityClass, foreignKeyColumn);

        int whereIndex = sql.toUpperCase().indexOf("WHERE");
        if (whereIndex != -1) {
            sql = sql.substring(0, whereIndex) + joinClause + " " + sql.substring(whereIndex);
        } else {
            sql += joinClause;
        }

        return sql;
    }

    private static <T> String processManyToManyInQuery(String sql, Field field, Class<T> entityClass) {
        // TODO реализация логики для мапинга связи ManyToMany
        return null;
    }

    /**
     * Получение аннотаций связей из полей класса
     */
    public static List<Field> getRelationshipFields(Class<?> entityClass) {
        List<Field> relationshipFields = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class) ||
                    field.isAnnotationPresent(OneToMany.class) ||
                    field.isAnnotationPresent(ManyToMany.class)) {
                relationshipFields.add(field);
            }
        }
        return relationshipFields;
    }

    /**
     * Получение имени столбца, связанного с отношением ManyToOne
     */
    public static String getManyToOneColumnName(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            ManyToOne annotation = field.getAnnotation(ManyToOne.class);
            if (annotation != null) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null) {
                    return columnAnnotation.name();
                }
            }
        }
        throw new IllegalArgumentException("No field with @ManyToOne and @Column annotation found in " + entityClass.getName());
    }
}

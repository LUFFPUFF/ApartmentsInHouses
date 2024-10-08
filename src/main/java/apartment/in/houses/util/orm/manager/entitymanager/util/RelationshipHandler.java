package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.ManyToMany;
import apartment.in.houses.util.orm.annotation.ManyToOne;
import apartment.in.houses.util.orm.annotation.OneToMany;
import apartment.in.houses.util.orm.manager.entitymanager.EntityManager;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;


public class RelationshipHandler {

    public static <T> void handleRelationships(T entity, EntityManager entityManager) throws SQLException {
        Class<?> entityClass = entity.getClass();

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                field.setAccessible(true);
                Object relatedEntity = null;

                try {
                    relatedEntity = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field: " + field.getName(), e);
                }

                if (relatedEntity != null) {
                    entityManager.persist(relatedEntity);

                    String primaryKeyName = EntityManagerUtil.getPrimaryKeyName(relatedEntity.getClass());
                    Object primaryKeyValue = EntityManagerUtil.getPrimaryKeyValue(entity, primaryKeyName);

                    try {
                        Field parentField = Arrays.stream(relatedEntity.getClass().getDeclaredFields())
                                .filter(f -> f.getType().equals(entity.getClass()))
                                .findFirst()
                                .orElseThrow(() -> new NoSuchFieldException("No parent field found in related entity"));

                        parentField.setAccessible(true);
                        parentField.set(relatedEntity, entity);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new SQLException("Error setting parent entity: " + e.getMessage());
                    }
                }
            }
        }

        // Обработка связей OneToMany
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                field.setAccessible(true);
                Collection<?> relatedEntities;

                try {
                    relatedEntities = (Collection<?>) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field: " + field.getName(), e);
                }

                if (relatedEntities != null) {
                    for (Object relatedEntity : relatedEntities) {
                        if (relatedEntity != null) {
                            entityManager.persist(relatedEntity);

                            try {
                                Field parentField = Arrays.stream(relatedEntity.getClass().getDeclaredFields())
                                        .filter(f -> f.getType().equals(entity.getClass()))
                                        .findFirst()
                                        .orElseThrow(() -> new NoSuchFieldException("No parent field found in related entity"));

                                parentField.setAccessible(true);
                                parentField.set(relatedEntity, entity);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                throw new SQLException("Error setting parent entity: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        }

        // Обработка связей ManyToMany
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToMany.class)) {
                field.setAccessible(true);
                Collection<?> relatedEntities;

                try {
                    relatedEntities = (Collection<?>) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field: " + field.getName(), e);
                }

                if (relatedEntities != null) {
                    for (Object relatedEntity : relatedEntities) {
                        if (relatedEntity != null) {
                            entityManager.persist(relatedEntity);

                            //TODO реализовать доп метод в EntityManager
                        }
                    }
                }
            }
        }
    }
}

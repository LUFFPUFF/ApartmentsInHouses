package apartment.in.houses.util.orm.manager.entitymanager.entitymanager;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.Permission;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Role;
import apartment.in.houses.util.XMLparser.ConfiguratorLoader;
import apartment.in.houses.util.orm.annotation.*;
import apartment.in.houses.util.orm.manager.entitymanager.util.*;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.query.Query;
import apartment.in.houses.util.orm.manager.querymanager.query.QueryImpl;
import apartment.in.houses.util.orm.session.interf.Session;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private final Session session;

    public EntityManagerImpl(Session session) {
        this.session = session;
    }

    @Override
    public <T> void persist(T entity) throws SQLException, IllegalAccessException {
        Class<?> entityClass = entity.getClass();

        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }

        String tableName = ConfiguratorLoader.getTableName(entityClass);
        List<String> columnNames = TableUtil.getColumnNames(entityClass);
        List<Object> values = FieldUtil.getValues(entity);

        Field primaryKeyField = FieldUtil.getPrimaryKeyField(entityClass);

        // Обработка составного ключа (EmbeddedId)
        if (primaryKeyField.isAnnotationPresent(EmbeddedId.class)) {
            Object embeddedId = FieldUtil.getPrimaryKeyValue(entity, primaryKeyField.getName());
            if (!EmbeddedUtil.isEmbeddable(embeddedId.getClass())) {
                throw new IllegalArgumentException("EmbeddedId is not embeddable.");
            }

            // Извлечение имен колонок и значений из EmbeddedId
            List<String> embeddedColumnNames = TableUtil.getEmbeddedColumnNames(embeddedId.getClass());
            List<Object> embeddedValues = EmbeddedUtil.getEmbeddedIdValues(embeddedId);

            // Удаляем составной ключ из общего списка значений, если он есть
            columnNames.remove(primaryKeyField.getName());
            values.remove(embeddedId);

            // Добавляем колонки и значения из EmbeddedId (то есть role_id и permission_id)
            columnNames.addAll(embeddedColumnNames);
            values.addAll(embeddedValues);
        }

        // Удаление значений ManyToOne и сохранение связанных объектов
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                field.setAccessible(true);
                Object relatedEntity = field.get(entity);

                if (relatedEntity != null) {
                    // Сохраняем связанные объекты перед основной сущностью
                    persist(relatedEntity);
                }

                // Проверка, что данное поле не дублирует ключ из EmbeddedId
                String columnName = field.getAnnotation(Column.class).name();
                if (columnNames.contains(columnName)) {
                    // Удаляем дубликаты, если они присутствуют
                    values.removeIf(value -> value.getClass().equals(field.getType()));
                    columnNames.remove(columnName);
                }
            }
        }

        // Генерация SQL запроса
        String sql = FormatSQLUtil.insert(tableName, columnNames);

        System.out.println("SQL Query: " + sql);
        System.out.println("Columns: " + columnNames);
        System.out.println("Values: " + values);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            for (int i = 1; i <= values.size(); i++) {
                System.out.println("Setting parameter " + i + ": " + values.get(i - 1));
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

        Field primaryKeyField = FieldUtil.getPrimaryKeyField(entityClass);
        String tableName = ConfiguratorLoader.getTableName(entityClass);

        if (primaryKeyField.isAnnotationPresent(EmbeddedId.class)) {
            RemoveUtil.handleRemoveWithEmbeddedId(entity, primaryKeyField, tableName, session);
        } else {
            RemoveUtil.handleRemoveWithPrimaryKey(entity, primaryKeyField, tableName, session);
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object id) throws SQLException {
        if (!ConfiguratorLoader.isEntityMapped(entityClass) || !entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not a mapped entity.");
        }

        Field primaryKeyField = FieldUtil.getPrimaryKeyField(entityClass);
        String tableName = ConfiguratorLoader.getTableName(entityClass);

        if (primaryKeyField.isAnnotationPresent(EmbeddedId.class)) {
            return FindUtil.findWithEmbeddedId(entityClass, id, primaryKeyField, tableName, session, this);
        } else {
            return FindUtil.findWithPrimaryKey(entityClass, id, tableName, session, this);
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

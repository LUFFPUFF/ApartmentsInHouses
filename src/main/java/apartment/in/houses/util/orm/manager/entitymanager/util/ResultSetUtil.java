package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.ManyToOne;
import apartment.in.houses.util.orm.manager.entitymanager.entitymanager.EntityManager;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ResultSetUtil {

    /**
     * Заполнение полей объекта из ResultSet
     */
    public static <T> void populateEntityFields(T entity, ResultSet resultSet, Class<?> entityClass, EntityManager entityManager) throws NoSuchFieldException, IllegalAccessException, SQLException {
        List<String> columnNames = TableUtil.getColumnNames(entityClass);
        for (int i = 1; i <= columnNames.size(); i++) {
            String columnName = columnNames.get(i - 1);
            Field field = Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class) && f.getAnnotation(Column.class).name().equals(columnName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException("Field with column name " + columnName + " not found"));

            field.setAccessible(true);

            if (field.isAnnotationPresent(ManyToOne.class)) {
                Object foreignKeyValue = resultSet.getObject(i);

                if (foreignKeyValue != null) {
                    ManyToOne annotation = field.getAnnotation(ManyToOne.class);
                    Class<?> targetEntityClass = annotation.targetEntity();

                    Object relatedEntity = entityManager.find(targetEntityClass, foreignKeyValue);

                    field.set(entity, relatedEntity);
                }
            } else {
                field.set(entity, resultSet.getObject(i, field.getType()));
            }
        }
    }
}

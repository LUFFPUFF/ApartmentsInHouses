package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableUtil {
    /**
     * Получение имени таблицы из аннотации @Table
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
     * Получение имени столбца из аннотации @Column
     */
    public static String getColumnName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null) {
            return columnAnnotation.name();
        }
        throw new IllegalArgumentException("Field must have @Column annotation: " + field.getName());
    }
}

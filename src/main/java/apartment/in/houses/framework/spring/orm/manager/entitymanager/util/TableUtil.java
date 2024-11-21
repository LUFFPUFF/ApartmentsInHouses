package apartment.in.houses.framework.spring.orm.manager.entitymanager.util;

import apartment.in.houses.framework.spring.orm.annotation.Column;
import apartment.in.houses.framework.spring.orm.annotation.Embeddable;
import apartment.in.houses.framework.spring.orm.annotation.Table;

import java.lang.reflect.Field;
import java.util.*;

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

    public static List<String> getEmbeddedColumnNames(Class<?> embeddedClass) {
        List<String> columnNames = new ArrayList<>();

        // Проверяем, что класс помечен аннотацией @Embeddable
        if (!embeddedClass.isAnnotationPresent(Embeddable.class)) {
            throw new IllegalArgumentException("Class " + embeddedClass.getName() + " is not annotated with @Embeddable.");
        }

        // Проходим по всем полям класса составного ключа
        Field[] fields = embeddedClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Делаем поле доступным для извлечения

            // Получаем аннотацию @Column, если она есть
            Column columnAnnotation = field.getAnnotation(Column.class);

            if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
                // Используем имя из аннотации @Column
                columnNames.add(columnAnnotation.name());
            } else {
                // Если аннотации @Column нет, используем имя поля
                columnNames.add(field.getName());
            }
        }

        return columnNames;
    }
}

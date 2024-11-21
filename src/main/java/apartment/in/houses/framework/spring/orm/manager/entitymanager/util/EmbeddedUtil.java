package apartment.in.houses.framework.spring.orm.manager.entitymanager.util;

import apartment.in.houses.framework.spring.orm.annotation.Embeddable;

import java.lang.reflect.Field;
import java.util.*;

public class EmbeddedUtil {
    public static boolean isEmbeddable(Class<?> embeddableClass) {
        return embeddableClass.isAnnotationPresent(Embeddable.class);
    }

    public static List<Object> getEmbeddedIdValues(Object embeddedId) throws IllegalAccessException {
        List<Object> values = new ArrayList<>();
        Field[] fields = embeddedId.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            values.add(field.get(embeddedId));
        }
        return values;
    }
}


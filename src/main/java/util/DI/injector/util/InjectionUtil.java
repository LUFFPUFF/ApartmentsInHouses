package util.DI.injector.util;

import util.DI.annotation.Autowired;
import util.DI.annotation.Qualifier;
import util.DI.injector.Injector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InjectionUtil {

    private InjectionUtil() {
        super();
    }

    /**
     * Perform injection recursively, for each service inside the Client class
     */
    public static void autowire(Injector injector, Class<?> classz, Object classInstance)
            throws InstantiationException, IllegalAccessException {
        List<Field> fields = findAllFields(classz);

        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                String qualifier = field.isAnnotationPresent(Qualifier.class)
                        ? field.getAnnotation(Qualifier.class).value()
                        : null;

                Object fieldInstance = injector.getBeanInstance(field.getType());
                field.set(classInstance, fieldInstance);  // Инъекция значения в поле
                autowire(injector, fieldInstance.getClass(), fieldInstance);  // Рекурсивная инъекция
            }
        }
    }

    // Метод для получения всех полей класса и его суперклассов
    private static List<Field> findAllFields(Class<?> classz) {
        return Arrays.stream(classz.getDeclaredFields())
                .collect(Collectors.toList());
    }
}

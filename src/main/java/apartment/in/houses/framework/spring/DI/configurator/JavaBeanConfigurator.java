package apartment.in.houses.framework.spring.DI.configurator;

import apartment.in.houses.framework.spring.DI.annotation.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class JavaBeanConfigurator implements BeanConfigurator {

    private final Map<Class<?>, Object> beanDefinitions = new HashMap<>();

    @Override
    public void registerBean(Class<?> beanClass) {
        if (beanClass.isAnnotationPresent(Component.class)) {
            try {
                Object bean = beanClass.getDeclaredConstructor().newInstance();
                beanDefinitions.put(beanClass, bean);
                System.out.println("Bean registered: " + beanClass.getName());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException("Failed to register bean: " + beanClass.getName(), e);
            }
        }
    }

    public <T> T getBean(Class<T> beanClass) {
        return (T) beanDefinitions.get(beanClass);
    }

    public Map<Class<?>, Object> getBeans() {
        return beanDefinitions;
    }
}

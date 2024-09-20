package util.DI.injector;

import org.reflections.Reflections;
import util.DI.annotation.Component;
import util.DI.injector.util.InjectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Injector {
    private final Map<Class<?>, Object> applicationScope;
    private static Injector injector;

    private Injector() {
        applicationScope = new HashMap<>();
    }

    public static void startApplication(Class<?> mainClass) {
        try {
            synchronized (Injector.class) {
                if (injector == null) {
                    injector = new Injector();
                    injector.initFramework(mainClass);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> T getService(Class<T> classz) {
        try {
            return injector.getBeanInstance(classz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initFramework(Class<?> mainClass)
            throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        Set<Class<?>> componentClasses = findAllComponents(mainClass.getPackage().getName());

        for (Class<?> componentClass : componentClasses) {
            Object classInstance = componentClass.newInstance();
            applicationScope.put(componentClass, classInstance);
            InjectionUtil.autowire(this, componentClass, classInstance);
            System.out.println("Component instance created: " + componentClass.getName());
        }
    }

    private Set<Class<?>> findAllComponents(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBeanInstance(Class<T> classz) throws InstantiationException, IllegalAccessException {
        if (applicationScope.containsKey(classz)) {
            return (T) applicationScope.get(classz);
        }

        synchronized (applicationScope) {
            T service = classz.newInstance();
            applicationScope.put(classz, service);
            InjectionUtil.autowire(this, classz, service);
            return service;
        }
    }
}

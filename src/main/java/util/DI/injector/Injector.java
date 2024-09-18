package util.DI.injector;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import util.DI.annotation.Component;
import util.DI.injector.util.InjectionUtil;

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
        Collection<Class<?>> componentClasses = findAllComponents(mainClass.getPackage().getName());

        for (Class<?> componentClass : componentClasses) {
            Object classInstance = componentClass.newInstance();
            applicationScope.put(componentClass, classInstance);
            InjectionUtil.autowire(this, componentClass, classInstance);
            System.out.println("Component instance created: " + componentClass.getName());
        }
    }

    private Collection<Class<?>> findAllComponents(String packageName) throws IOException {
        ComponentContainer componentContainer = ComponentContainer.getInstance();
        ClassHunter classHunter = componentContainer.getClassHunter();
        String packageRelPath = packageName.replace(".", "/");
        try (ClassHunter.SearchResult result = classHunter.findBy(
                SearchConfig.forResources(packageRelPath)
                        .by(ClassCriteria.create().allThoseThatMatch(cls -> cls.isAnnotationPresent(Component.class)))
        )) {
            return result.getClasses();
        }
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

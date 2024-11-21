package apartment.in.houses.framework.spring.DI.factory;

import apartment.in.houses.framework.spring.DI.annotation.Autowired;
import apartment.in.houses.framework.spring.DI.annotation.PostConstructor;
import apartment.in.houses.framework.spring.DI.postprocessor.BeanPostProcessorInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BeanFactoryUtils {

    public static Object createBean(Class<?> beanClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = beanClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    public static void injectDependencies(Object bean, BeanFactoryInterface beanFactory) throws IllegalAccessException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Class<?> typeField = field.getType();
                Object dependency = beanFactory.getBean(typeField);
                field.set(bean, dependency);
            }
        }
    }

    public static void invokePostConstructors(Object bean) throws InvocationTargetException, IllegalAccessException {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstructor.class)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }

    public static Object applyBeanPostProcessorsBeforeInitialization(Object bean, Class<?> beanClass, List<BeanPostProcessorInterface> processors) {
        for (BeanPostProcessorInterface processor : processors) {
            bean = processor.postProcessBeforeInitialization(bean, beanClass);
        }
        return bean;
    }

    public static Object applyBeanPostProcessorsAfterInitialization(Object bean, Class<?> beanClass, List<BeanPostProcessorInterface> processors) {
        for (BeanPostProcessorInterface processor : processors) {
            bean = processor.postProcessAfterInitialization(bean, beanClass);
        }
        return bean;
    }
}

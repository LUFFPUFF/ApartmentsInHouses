package apartment.in.houses.util.DI.factory;

import java.lang.reflect.InvocationTargetException;

public interface BeanFactoryInterface {
    <T> T getBean(Class<T> beanClass);
}

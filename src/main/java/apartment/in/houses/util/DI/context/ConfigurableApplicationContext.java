package apartment.in.houses.util.DI.context;

import java.lang.reflect.InvocationTargetException;

public interface ConfigurableApplicationContext {
    <T> T getBean(Class<T> beanClass);
    void refresh();
    void close();
}

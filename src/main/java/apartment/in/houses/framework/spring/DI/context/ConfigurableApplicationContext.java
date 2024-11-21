package apartment.in.houses.framework.spring.DI.context;

public interface ConfigurableApplicationContext {
    <T> T getBean(Class<T> beanClass);
    void refresh();
    void close();
}

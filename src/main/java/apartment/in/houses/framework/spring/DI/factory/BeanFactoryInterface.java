package apartment.in.houses.framework.spring.DI.factory;

public interface BeanFactoryInterface {
    <T> T getBean(Class<T> beanClass);
}

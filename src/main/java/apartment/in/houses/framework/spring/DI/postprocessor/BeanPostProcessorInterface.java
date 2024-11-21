package apartment.in.houses.framework.spring.DI.postprocessor;

public interface BeanPostProcessorInterface {

    Object postProcessBeforeInitialization(Object bean, Class<?> beanClass);
    Object postProcessAfterInitialization(Object bean, Class<?> beanClass);
}

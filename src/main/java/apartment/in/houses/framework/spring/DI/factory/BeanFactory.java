package apartment.in.houses.framework.spring.DI.factory;

import apartment.in.houses.framework.spring.DI.configurator.JavaBeanConfigurator;

public class BeanFactory implements BeanFactoryInterface {
    private final JavaBeanConfigurator beanConfigurator;

    public BeanFactory(JavaBeanConfigurator beanConfigurator) {
        this.beanConfigurator = beanConfigurator;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        T bean = beanConfigurator.getBean(beanClass);

        if (bean == null) {
            try {
                beanConfigurator.registerBean(beanClass);
                bean = beanConfigurator.getBean(beanClass);
                BeanFactoryUtils.injectDependencies(bean, this);
                BeanFactoryUtils.invokePostConstructors(bean);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create bean: " + beanClass.getName(), e);
            }
        }

        return bean;
    }
}

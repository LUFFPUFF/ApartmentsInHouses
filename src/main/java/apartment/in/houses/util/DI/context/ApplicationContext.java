package apartment.in.houses.util.DI.context;

import apartment.in.houses.util.DI.config.Configuration;
import apartment.in.houses.util.DI.config.JavaConfiguration;
import apartment.in.houses.util.DI.configurator.JavaBeanConfigurator;
import apartment.in.houses.util.DI.factory.BeanFactory;

import java.io.IOException;
import java.util.List;

public class ApplicationContext implements ConfigurableApplicationContext {

    private final BeanFactory beanFactory;
    private final JavaBeanConfigurator beanConfigurator;
    private final Configuration configuration;

    public ApplicationContext(String basePackage) {
        this.configuration = new JavaConfiguration(basePackage);
        this.beanConfigurator = new JavaBeanConfigurator();
        this.beanFactory = new BeanFactory(beanConfigurator);
    }

    /**
     * Получение бина
     * @param beanClass
     * @return
     * @param <T>
     */
    @Override
    public <T> T getBean(Class<T> beanClass) {
        return beanFactory.getBean(beanClass);
    }

    /**
     * Инициализация контекста приложения
     */
    @Override
    public void refresh() {
        try {
            List<Class<?>> classes = configuration.scan();
            for (Class<?> clazz : classes) {
                beanConfigurator.registerBean(clazz);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}

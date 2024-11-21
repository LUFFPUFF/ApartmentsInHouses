package apartment.in.houses.framework.spring.DI.postprocessor;

public class PostProcessor implements BeanPostProcessorInterface {

    @Override
    public Object postProcessBeforeInitialization(Object bean, Class<?> beanClass) {
        System.out.println("Выполняем постобработку перед инициализацией: " + beanClass.getSimpleName());
        return bean; // Возвращаем оригинальный объект
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, Class<?> beanClass) {
        System.out.println("Выполняем постобработку после инициализации: " + beanClass.getSimpleName());
        return bean; // Возвращаем оригинальный объект
    }

}

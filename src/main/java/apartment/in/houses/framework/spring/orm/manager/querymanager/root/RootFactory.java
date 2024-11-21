package apartment.in.houses.framework.spring.orm.manager.querymanager.root;

public class RootFactory {
    public static <T> Root<T> createRoot(Class<T> entityClas) {
        return new Root<>(entityClas);
    }
}

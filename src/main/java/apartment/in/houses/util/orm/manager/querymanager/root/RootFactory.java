package apartment.in.houses.util.orm.manager.querymanager.root;

public class RootFactory {
    public static <T> Root<T> createRoot(Class<T> entityClas) {
        return new Root<>(entityClas);
    }
}

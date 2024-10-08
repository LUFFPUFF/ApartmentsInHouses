package apartment.in.houses.data_access.util.exucutor.interf;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Executor<E, K> {

    boolean executeInsert(E entity) throws SQLException;
    boolean executeDelete(Class<?> classEntity, K key);
    boolean executeUpdate(E entity) throws SQLException;
    List<E> executeGetAll(Class<?> classEntity) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException, SQLException;
    E executeGetId(Class<?> classEntity, K key) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, SQLException;
}

package apartment.in.houses.data_access.registrationdatabase.dao;

import java.util.List;

public interface UserDAO<E, K> {

    List<E> getAllUsers();
    E getUser(int id);
    boolean insert(E entity);
    boolean update(E entity);
    boolean delete(K key);
}

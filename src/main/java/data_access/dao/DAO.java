package data_access.dao;

import java.util.List;

public interface DAO<E, K> {
    List<E> getAll();
    E getEntityById(K id);
    void insert(E entity);
    void update(E entity);
    void delete(K id);
}

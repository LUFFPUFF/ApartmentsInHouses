package data_access.dao;

import java.util.List;

public interface DAO<E, K> {
    void insert(E entity);
    void update(E entity);
    void delete(K id);
}

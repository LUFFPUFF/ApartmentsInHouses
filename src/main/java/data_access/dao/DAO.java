package data_access.dao;

import java.util.List;

public interface DAO<T> {
    void insert(T t);
    void update(T t);
    void delete(T t);
}

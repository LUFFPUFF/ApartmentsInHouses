package apartment.in.houses.util.orm.manager.entitymanager;

import apartment.in.houses.util.orm.manager.querymanager.query.Query;

import java.sql.SQLException;

public interface EntityManager {
    <T> void persist(T entity) throws SQLException;
    <T> void remove(T entity) throws SQLException;
    <T> T find(Class<T> entityClass, Object id) throws SQLException;
    <T> Query<T> createQuery(String sql, Class<T> entityClass);
}

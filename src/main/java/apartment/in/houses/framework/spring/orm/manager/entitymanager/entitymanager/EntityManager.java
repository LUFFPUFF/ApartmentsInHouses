package apartment.in.houses.framework.spring.orm.manager.entitymanager.entitymanager;

import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.framework.spring.orm.manager.querymanager.query.Query;

import java.sql.SQLException;

public interface EntityManager {
    <T> void persist(T entity) throws SQLException, IllegalAccessException;
    <T> void remove(T entity) throws SQLException;
    <T> T find(Class<T> entityClass, Object id) throws SQLException;
    <T> Query<T> createQuery(String sql, Class<T> entityClass);
    <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery);
}

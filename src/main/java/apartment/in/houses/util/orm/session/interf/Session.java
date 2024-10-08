package apartment.in.houses.util.orm.session.interf;

import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.query.Query;
import apartment.in.houses.util.orm.transaction.interf.Transaction;

import java.sql.Connection;

public interface Session {

    void close();
    <T> void save(T entity);
    <T> T get(Class<T> entityClass, Object id);
    <T> void delete(T entity);
    <T> Query<T> createQuery(String sql, Class<T> entityClass);
    <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery);
    Connection getConnection();
    Transaction beginTransaction();
    Transaction getTransaction();
}

package apartment.in.houses.util.orm.session;

import apartment.in.houses.util.orm.manager.entitymanager.entitymanager.EntityManager;
import apartment.in.houses.util.orm.manager.entitymanager.entitymanager.EntityManagerImpl;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.query.Query;
import apartment.in.houses.util.orm.session.connection.exception.DatabaseException;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import apartment.in.houses.util.orm.transaction.interf.Transaction;
import apartment.in.houses.util.orm.transaction.TransactionImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class SessionImpl implements Session {
    private final Connection connection;
    private final SessionFactory sessionFactory;
    private Transaction currentTransaction;
    private final EntityManager entityManager;

    public SessionImpl(Connection connection, SessionFactory sessionFactory) {
        this.connection = connection;
        this.sessionFactory = sessionFactory;
        this.entityManager = new EntityManagerImpl(this);
    }

    @Override
    public void close() {
        try {
            if (currentTransaction != null && currentTransaction.isActive()) {
                currentTransaction.rollback();
            }
        } finally {
            sessionFactory.releaseConnection(connection);
        }
    }

    @Override
    public <T> void save(T entity) {
        try {
            entityManager.persist(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T get(Class<T> entityClass, Object id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void delete(T entity) {
        try {
            entityManager.remove(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> Query<T> createQuery(String sql, Class<T> entityClass) {
        return entityManager.createQuery(sql, entityClass);
    }

    @Override
    public <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Transaction beginTransaction() {
        if (currentTransaction != null && currentTransaction.isActive()) {
            throw new DatabaseException("Transaction is already active");
        }
        try {
            currentTransaction = new TransactionImpl(connection);
            currentTransaction.begin();
            return currentTransaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction getTransaction() {
        return currentTransaction;
    }
}

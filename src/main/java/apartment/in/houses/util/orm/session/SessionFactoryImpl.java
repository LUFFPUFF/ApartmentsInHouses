package apartment.in.houses.util.orm.session;

import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.util.orm.session.connection.ConnectionManagerImpl;
import apartment.in.houses.util.orm.session.connection.interf.ConnectionManager;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;

import java.sql.Connection;

public class SessionFactoryImpl implements SessionFactory {
    private final ConnectionManager connectionManager;

    public SessionFactoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Session openSession() {
        return new SessionImpl(connectionManager.getConnection(), this);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return new CriteriaBuilderImpl();
    }

    @Override
    public void releaseConnection(Connection connection) {
        connectionManager.closeConnection(connection);
    }
}

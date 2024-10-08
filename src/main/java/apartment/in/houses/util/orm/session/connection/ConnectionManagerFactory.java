package apartment.in.houses.util.orm.session.connection;

import apartment.in.houses.util.orm.session.SessionFactoryImpl;
import apartment.in.houses.util.orm.session.interf.SessionFactory;

public class ConnectionManagerFactory {

    private static final class SessionFactoryHolder {
        private static final SessionFactory sessionFactory = new SessionFactoryImpl(new ConnectionManagerImpl());
    }

    public static SessionFactory createSessionFactory() {
        return SessionFactoryHolder.sessionFactory;
    }
}

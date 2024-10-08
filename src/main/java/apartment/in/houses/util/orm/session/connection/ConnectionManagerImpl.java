package apartment.in.houses.util.orm.session.connection;

import apartment.in.houses.util.XMLparser.XMLParser;
import apartment.in.houses.util.orm.session.SessionFactoryImpl;
import apartment.in.houses.util.orm.session.connection.exception.DatabaseException;
import apartment.in.houses.util.orm.session.connection.interf.ConnectionManager;
import apartment.in.houses.util.orm.session.interf.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionManagerImpl implements ConnectionManager {

    private static final Map<String, String> config =
            XMLParser.parse("src/main/resources/jdbc-config.xml");
    private final BlockingQueue<Connection> connectionPool;

    public ConnectionManagerImpl() {
        this.connectionPool = new LinkedBlockingDeque<>(Integer.parseInt(config.get("poolsize")));
        initializeConnectionPool(Integer.parseInt(config.get("initialPoolSize")));
    }

    private void initializeConnectionPool(int initialPoolSize) {
        try {
            for (int i = 0; i < initialPoolSize; i++) {
                Connection connection = DriverManager.getConnection(
                        config.get("url"),
                        config.get("username"),
                        config.get("password")
                );
                connectionPool.offer(connection);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize connection pool", e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new DatabaseException("Failed to acquire a connection from the pool", e);
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connectionPool.put(connection);
            }
        } catch (SQLException | InterruptedException e) {
            throw new DatabaseException("Failed to release connection back to the pool", e);
        }
    }

    public static SessionFactory connect() {
        return new SessionFactoryImpl(new ConnectionManagerImpl());
    }
}

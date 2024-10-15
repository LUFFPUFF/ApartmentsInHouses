package apartment.in.houses.util.orm.session.connection;

import apartment.in.houses.util.orm.session.SessionFactoryImpl;
import apartment.in.houses.util.orm.session.connection.exception.DatabaseException;
import apartment.in.houses.util.orm.session.connection.interf.ConnectionManager;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionManagerImpl implements ConnectionManager {
    private static final Map<String, String> config = loadConfig();
    private final BlockingQueue<Connection> connectionPool;

    public ConnectionManagerImpl() {
        this.connectionPool = new LinkedBlockingDeque<>(10);
        initializeConnectionPool(5);
    }

    private void initializeConnectionPool(int initialPoolSize) {
        try {

            Class.forName(config.get("driver"));

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    public static SessionFactory getSessionFactory() {
        return new SessionFactoryImpl(new ConnectionManagerImpl());
    }

    private static Map<String, String> loadConfig() {
        Map<String, String> configMap = new HashMap<>();
        try {
            InputStream inputStream = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("jdbc-config.xml");
            if (inputStream == null) {
                throw new FileNotFoundException("jdbc-config.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            configMap.put("driver", document.getElementsByTagName("driver").item(0).getTextContent());
            configMap.put("url", document.getElementsByTagName("url").item(0).getTextContent());
            configMap.put("username", document.getElementsByTagName("username").item(0).getTextContent());
            configMap.put("password", document.getElementsByTagName("password").item(0).getTextContent());
            configMap.put("poolsize", document.getElementsByTagName("poolsize").item(0).getTextContent());
            configMap.put("initialPoolSize", document.getElementsByTagName("initialPoolSize").item(0).getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return configMap;
    }

}

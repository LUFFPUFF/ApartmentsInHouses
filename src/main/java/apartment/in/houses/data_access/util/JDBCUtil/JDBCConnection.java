package apartment.in.houses.data_access.util.JDBCUtil;

import apartment.in.houses.util.XMLparser.XMLParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JDBCConnection {
    private static final Map<String, String> xmlLoad =
            XMLParser.parse("/Users/nikita/Downloads/ApartmentsInHousesEE/src/main/resources/jdbc-config.xml");
    private static final int POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pool = new ArrayBlockingQueue<>(POOL_SIZE);

            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(
                        xmlLoad.get("url"),
                        xmlLoad.get("username"),
                        xmlLoad.get("password")
                );
                pool.offer(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    pool.offer(connection);
                } else {

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void shutdownPool() {
        for (Connection connection : pool) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

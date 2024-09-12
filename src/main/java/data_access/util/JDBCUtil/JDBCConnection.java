package data_access.util.JDBCUtil;

import util.logger.CustomLogger;
import util.logger.LogLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JDBCConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ApartmentsInHouses";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nikita090504";

    private static final int POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static final CustomLogger logger = new CustomLogger("log/jdbc.log", LogLevel.INFO);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pool = new ArrayBlockingQueue<>(POOL_SIZE);

            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                pool.offer(connection);
            }
            logger.info("Connection pool initialized with " + POOL_SIZE + " connections");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver class not found");
        } catch (SQLException e) {
            logger.error("SQL Exception during connection pool initialization");
        }
        logger.close();
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.take();
            logger.info("Connection acquired from pool");
        } catch (InterruptedException e) {
            logger.error("Error while taking connection from pool");
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    pool.offer(connection);
                    logger.info("Connection returned to pool");
                } else {
                    logger.warning("Connection is closed, not returning to pool");
                }
            } catch (SQLException e) {
                logger.error("Error while checking if connection is closed");
            }
        }
        logger.close();
    }


    public static void shutdownPool() {
        for (Connection connection : pool) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing connection during pool shutdown");
            }
        }
        logger.info("Connection pool shut down");
        logger.close();
    }

}

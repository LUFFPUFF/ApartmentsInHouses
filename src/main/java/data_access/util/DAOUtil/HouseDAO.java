package data_access.util.DAOUtil;

import data_access.dao.DAO;
import data_access.entity.House;
import data_access.util.JDBCUtil.JDBCConnection;
import util.logger.CustomLogger;
import util.logger.LogLevel;

import java.sql.*;

public class HouseDAO implements DAO<House> {
    private static final CustomLogger logger = new CustomLogger("log/house.log", LogLevel.INFO);

    @Override
    public void insert(House house) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO houses (id, address, name, start_construction_date, end_construction_date, commissioning_date) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, house.getId());
            preparedStatement.setString(2, house.getAddress());
            preparedStatement.setString(3, house.getName());
            preparedStatement.setDate(4, house.getStart_construction_date() != null ? new java.sql.Date(house.getStart_construction_date().getTime()) : null);
            preparedStatement.setDate(5, house.getEnd_construction_date() != null ? new java.sql.Date(house.getEnd_construction_date().getTime()) : null);
            preparedStatement.setDate(6, house.getCommissioning_date() != null ? new java.sql.Date(house.getCommissioning_date().getTime()) : null);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully added a new house with id: " + house.getId());
            } else {
                logger.warning("No rows affected, house with id: " + house.getId() + " was not added.");
            }
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    logger.error("Transaction is being rolled back due to an error");
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
                }
            }
            logger.error("Error while inserting house: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Закрываем ресурсы
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    JDBCConnection.releaseConnection(connection);
                }
            } catch (SQLException e) {
                logger.error("Error while closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void update(House house) {

    }

    @Override
    public void delete(House house) {

    }

    public void testSQL() {
        try {
            Connection connection = JDBCConnection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Houses");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

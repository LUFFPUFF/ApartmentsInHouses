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
        String INSERT_SQL = "INSERT INTO houses (id, address, name, start_construction_date, end_construction_date, commissioning_date) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setInt(1, house.getId());
            preparedStatement.setString(2, house.getAddress());
            preparedStatement.setString(3, house.getName());
            preparedStatement.setDate(4, house.getStart_construction_date());
            preparedStatement.setDate(5, house.getEnd_construction_date());
            preparedStatement.setDate(6, house.getCommissioning_date());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully added a new house with id: " + house.getId());
            } else {
                logger.warning("No rows affected, house with id: " + house.getId() + " was not added.");
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                logger.error("Transaction is being rolled back due to an error");
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            logger.error("Error while inserting house: " + e.getMessage());

        } finally {
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE houses SET address = ?, name = ?, start_construction_date = ?, end_construction_date = ?, commissioning_date = ? WHERE id = ?";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, house.getAddress());
            preparedStatement.setString(2, house.getName());
            preparedStatement.setDate(3, house.getStart_construction_date());
            preparedStatement.setDate(4, house.getEnd_construction_date());
            preparedStatement.setDate(5, house.getCommissioning_date());
            preparedStatement.setInt(6, house.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully updated house with id: " + house.getId());
            } else {
                logger.warning("No rows affected, house with id: " + house.getId() + " was not updated.");
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                logger.error("Transaction is being rolled back due to an error");
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            logger.error("Error while updating house: " + e.getMessage());

        } finally {
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
    public void delete(House house) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String DELETE_SQL = "Delete * From Houses where id = ?";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, house.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully deleted house with id: " + house.getId());
            } else {
                logger.warning("No rows affected, house with id: " + house.getId() + " was not deleted.");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                logger.error("Transaction is being rolled back due to an error");
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            logger.error("Error while deleting house: " + e.getMessage());

        } finally {
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

}

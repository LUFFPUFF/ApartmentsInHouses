package data_access.util.DAOUtil;

import data_access.dao.DAO;
import data_access.entity.Apartment;
import data_access.entity.House;
import data_access.util.JDBCUtil.JDBCConnection;
import util.DI.annotation.Component;
import util.logger.CustomLogger;
import util.logger.LogLevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentController implements DAO<Apartment, Integer> {

    private static final CustomLogger logger = new CustomLogger("log/apart.log", LogLevel.INFO);

    @Override
    public List<Apartment> getAll() {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        List<Apartment> apartments = new ArrayList<>();
        String SQL = "select * from apartments";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer houseId = resultSet.getInt("house_id");
                Double totalArea = resultSet.getDouble("total_area");
                Double livingArea = resultSet.getDouble("living_area");
                Integer rooms = resultSet.getInt("rooms");
                Integer floor = resultSet.getInt("floor");
                Integer entrance = resultSet.getInt("entrance");
                Double price = resultSet.getDouble("price");
                String saleCondition = resultSet.getString("sale_condition");

                Apartment apartment = new Apartment(id, houseId, totalArea, livingArea, rooms, floor, entrance, price, saleCondition);

                apartments.add(apartment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return apartments;
    }

    @Override
    public Apartment getEntityById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Apartment apartment = null;
        String SQL = "select * from apartments where id = ?";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer houseId = resultSet.getInt("house_id");
                Double totalArea = resultSet.getDouble("total_area");
                Double livingArea = resultSet.getDouble("living_area");
                Integer rooms = resultSet.getInt("rooms");
                Integer floor = resultSet.getInt("floor");
                Integer entrance = resultSet.getInt("entrance");
                Double price = resultSet.getDouble("price");
                String saleCondition = resultSet.getString("sale_condition");

                apartment = new Apartment(id, houseId, totalArea, livingArea, rooms, floor, entrance, price, saleCondition);

            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return apartment;
    }

    @Override
    public void insert(Apartment apartment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO apartments (id, house_id, total_area, living_area, rooms, floor, entrance, price, sale_condition) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = JDBCConnection.getConnection();

            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, apartment.getId());
            preparedStatement.setInt(2, apartment.getHouse_id());
            preparedStatement.setDouble(3, apartment.getTotalArea());
            preparedStatement.setDouble(4, apartment.getLivingArea());
            preparedStatement.setInt(5, apartment.getRooms());
            preparedStatement.setInt(6, apartment.getFloor());
            preparedStatement.setInt(7, apartment.getEntrance());
            preparedStatement.setDouble(8, apartment.getPrice());
            preparedStatement.setString(9, apartment.getSaleCondition());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully added a new house with id: ");
            }

        } catch (SQLException e) {
            logger.error("Error while inserting house: ");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                JDBCConnection.releaseConnection(connection);
            } catch (SQLException e) {
                logger.error("Error while closing resources: ");
            }
        }

        logger.close();
    }

    @Override
    public void update(Apartment apartment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String UPDATE_SQL = "update apartments set house_id = ?, total_area = ?, living_area = ?, rooms = ?, floor = ?, entrance = ?, price = ?, sale_condition = ? where id = ?";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setInt(1, apartment.getHouse_id());
            preparedStatement.setDouble(2, apartment.getTotalArea());
            preparedStatement.setDouble(3, apartment.getLivingArea());
            preparedStatement.setInt(4, apartment.getRooms());
            preparedStatement.setInt(5, apartment.getFloor());
            preparedStatement.setInt(6, apartment.getEntrance());
            preparedStatement.setDouble(7, apartment.getPrice());
            preparedStatement.setString(8, apartment.getSaleCondition());
            preparedStatement.setInt(9, apartment.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully updated house with id: " + apartment.getId());
            } else {
                logger.warning("No rows affected, house with id: " + apartment.getId() + " was not updated.");
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                logger.error("Transaction is being rolled back due to an error");
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            logger.error("Error while updating apartment: " + e.getMessage());

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
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String DELETE_SQL = "delete * from apartment where id = ?";

        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully deleted apartment with id: " + id);
            } else {
                logger.warning("No rows affected, apartment with id: " + id + " was not deleted.");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                logger.error("Transaction is being rolled back due to an error");
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            logger.error("Error while deleting apartment: " + e.getMessage());

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

package data_access.util.DAOUtil;

import data_access.dao.DAO;
import data_access.entity.Apartment;
import data_access.util.JDBCUtil.JDBCConnection;
import util.logger.CustomLogger;
import util.logger.LogLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApartmentDAO implements DAO<Apartment> {

    private static final CustomLogger logger = new CustomLogger("log/apart.log", LogLevel.INFO);

    @Override
    public void insert(Apartment apartment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO houses (id, totalArea, livingArea, rooms, floor, entrance, price, saleCondition) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = JDBCConnection.getConnection();

            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, apartment.getId());
            preparedStatement.setDouble(2, apartment.getTotalArea());
            preparedStatement.setDouble(3, apartment.getLivingArea());
            preparedStatement.setInt(4, apartment.getRooms());
            preparedStatement.setInt(5, apartment.getFloor());
            preparedStatement.setInt(6, apartment.getEntrance());
            preparedStatement.setDouble(7, apartment.getPrice());
            preparedStatement.setString(8, apartment.getSaleCondition());

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
    }

    @Override
    public void update(Apartment apartment) {

    }

    @Override
    public void delete(Apartment apartment) {

    }
}

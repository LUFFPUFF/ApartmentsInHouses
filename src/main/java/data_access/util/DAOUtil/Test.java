package data_access.util.DAOUtil;

import data_access.entity.Apartment;
import data_access.entity.House;
import data_access.util.JDBCUtil.JDBCConnection;

import java.sql.Connection;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        HouseDAO houseDAO = new HouseDAO();


        House house = new House(4, "Зои Космодемьянской д 7", "Дом", new java.sql.Date(1980, 11, 12), new java.sql.Date(2010, 11, 12), new java.sql.Date(2015, 11, 12));



        Apartment apartment = new Apartment(1, house.getId(), 45.5, 33.3, 1, 4, 2, 3.7, Apartment.SaleCondition.FREE);


        houseDAO.insert(house);
    }
}

package apartment.in.houses.data_access.apartmentsdatabase.dao;

import apartment.in.houses.data_access.apartmentsdatabase.entities.House;

import java.util.List;

public interface HouseDAO {

    List<House> getAllHouses();
    House getHouse(int id);
    boolean insert(House apartment);
    boolean update(House apartment);
    boolean delete(int id);
}

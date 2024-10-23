package apartment.in.houses.data_access.apartmentsdatabase.dao;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.House;

import java.util.List;

public interface HouseDAO {

    List<House> getAllHouses();
    House getHouse(int id);
    boolean insert(House house);
    boolean update(House house);
    boolean delete(House house);
}

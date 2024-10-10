package apartment.in.houses.data_access.apartmentsdatabase.dao;

import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;

import java.util.List;

public interface ApartmentDAO {
    List<Apartment> getAllApartments();
    Apartment getApartment(int id);
    boolean insert(Apartment apartment);
    boolean update(Apartment apartment);
    boolean delete(Apartment apartment);
}

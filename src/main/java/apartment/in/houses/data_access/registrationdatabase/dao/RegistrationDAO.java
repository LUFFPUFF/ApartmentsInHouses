package apartment.in.houses.data_access.registrationdatabase.dao;

import apartment.in.houses.data_access.registrationdatabase.entities.Administrator;
import apartment.in.houses.data_access.registrationdatabase.entities.Registration;
import apartment.in.houses.data_access.registrationdatabase.entities.User;

import java.util.List;

public interface RegistrationDAO {
    List<Registration> getAllRegistration();
    Registration getRegistration(int id);

    boolean insert(Registration registration);
    boolean update(Registration registration);
    boolean delete(int id);
}

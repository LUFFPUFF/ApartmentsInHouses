package apartment.in.houses.data_access.apartmentsdatabase.dao;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.Client;

import java.util.List;

public interface ClientDAO {

    List<Client> getAllClients();
    Client getClient(int id);
    boolean insert(Client client);
    boolean update(Client client);
    boolean delete(Client client);
}

package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.ClientDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Client;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;
import apartment.in.houses.util.orm.session.connection.ConnectionManagerImpl;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import apartment.in.houses.util.orm.transaction.interf.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ClientRepository implements ClientDAO {

    private static final SessionFactory sessionFactory =
            ConnectionManagerImpl.getSessionFactory();
    @Override
    public List<Client> getAllClients() {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = new CriteriaBuilderImpl();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);

            query.select(root);

            transaction.commit();

            return session.createQuery(query).getResultList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Client getClient(int id) {
        Session session = sessionFactory.openSession();
        Client client;
        try {
            Transaction transaction = session.beginTransaction();
            client = session.get(Client.class, id);
            transaction.commit();
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean insert(Client client) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.save(client);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Client client) {
        return false;
    }

    @Override
    public boolean delete(Client client) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.delete(client);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

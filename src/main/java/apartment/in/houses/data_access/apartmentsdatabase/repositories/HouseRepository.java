package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.HouseDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entities.House;
import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;
import apartment.in.houses.util.orm.session.connection.ConnectionManagerFactory;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import apartment.in.houses.util.orm.transaction.interf.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Component
public class HouseRepository implements HouseDAO {

    private static final SessionFactory sessionFactory =
            ConnectionManagerFactory.createSessionFactory();
    @Override
    public List<House> getAllHouses() {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = new CriteriaBuilderImpl();
            CriteriaQuery<House> query = builder.createQuery(House.class);
            Root<House> root = query.from(House.class);

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
    public House getHouse(int id) {
        House house;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            house = session.get(House.class, id);
            transaction.commit();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return house;
    }

    @Override
    public boolean insert(House house) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.save(house);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(House house) {
        return false;
    }

    @Override
    public boolean delete(House house) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.delete(house);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

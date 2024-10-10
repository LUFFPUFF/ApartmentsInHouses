package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.service.exceprion.InvalidException;
import apartment.in.houses.service.exceprion.NotFoundException;

import java.util.List;

public interface Service<E, K> {

    List<E> getAll();
    E getId(K key) throws NotFoundException;
    void add(E entity) throws InvalidException;
    void update(E entity) throws InvalidException;
    void delete(E entity) throws InvalidException;
}

package BL.service;

import BL.exceprion.InvalidException;
import BL.exceprion.NotFoundException;
import data_access.entity.House;

import java.util.List;

public interface Service<E, K> {

    List<E> getAll();
    E getId(K key) throws NotFoundException;

    void add(E entity) throws InvalidException;
    void update(E entity) throws InvalidException;
    void delete(K key) throws InvalidException;
}

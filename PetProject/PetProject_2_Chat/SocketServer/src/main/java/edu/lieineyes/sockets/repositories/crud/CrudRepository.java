package edu.lieineyes.sockets.repositories.crud;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    void save(T entity) throws IllegalAccessException;
    void update(T entity);
    void delete(Long id);
}

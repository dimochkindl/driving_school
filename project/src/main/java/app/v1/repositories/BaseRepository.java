package app.v1.repositories;

import app.v1.entities.Car;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAll();
    T getById(Long id);
    void update(T t);
    void delete(Long id) throws SQLException;
    void save(T t);
}

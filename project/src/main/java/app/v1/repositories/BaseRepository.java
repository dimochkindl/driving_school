package app.v1.repositories;

import java.util.List;

public interface BaseRepository<T> {
    List<T> getAll();
    T getById(Long id);
    void update(T t);
    void delete(Long id);
    void save(T t);
}

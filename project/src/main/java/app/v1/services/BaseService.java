package app.v1.services;

import app.v1.repositories.BaseRepository;

import java.util.Collection;

public interface BaseService<T> {
    void add(T model);

    void update(T model);

    Collection<T> getAll();

    void remove(Long id);

    T get(Long id);

}

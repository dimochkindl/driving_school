package app.v1.services;

import app.v1.repositories.BaseRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseRepository<T> repository;

    @Autowired
    public BaseServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(T model) {
        if (model != null) {
            repository.save(model);
        }
    }

    @Override
    @Transactional
    public void update(T model) {
        repository.update(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection getAll() {
        return repository.getAll();
    }

    @Override
    public T get(Long id) {
        return repository.getById(id);
    }

    @SneakyThrows
    @Override
    public void remove(Long id) {
        if(repository.getById(id) != null){
            repository.delete(id);
        }

    }
}

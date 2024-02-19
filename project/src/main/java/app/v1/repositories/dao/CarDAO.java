package app.v1.repositories.dao;

import app.v1.entities.Car;
import app.v1.entities.Practice;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface CarDAO extends BaseRepository<Car> {
    List<Practice> usedForPractices(Long id);
}

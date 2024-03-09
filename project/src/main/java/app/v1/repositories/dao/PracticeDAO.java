package app.v1.repositories.dao;

import app.v1.entities.Practice;
import app.v1.repositories.BaseRepository;

import java.time.LocalDate;
import java.util.List;

public interface PracticeDAO extends BaseRepository<Practice> {
    List<Object> getPracticesByDate(LocalDate date);
    List<Object> getPracticeByPlace(String place);

    int getNumberOfPracticesByCarID(Long id);
}

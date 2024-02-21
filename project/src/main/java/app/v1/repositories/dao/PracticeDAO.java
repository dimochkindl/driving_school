package app.v1.repositories.dao;

import app.v1.entities.Practice;
import app.v1.repositories.BaseRepository;

import java.sql.Date;
import java.util.List;

public interface PracticeDAO extends BaseRepository<Practice> {
    List<Practice> getPracticesByDate(Date date);
    List<Practice> getPracticeByPlace(String place);

    int getNumberOfPracticesByCarID(Long id);
}

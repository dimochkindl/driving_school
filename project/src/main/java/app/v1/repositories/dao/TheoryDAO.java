package app.v1.repositories.dao;

import app.v1.entities.Theory;
import app.v1.repositories.BaseRepository;

import java.sql.Date;
import java.util.List;

public interface TheoryDAO extends BaseRepository<Theory> {
    List<Theory> getTheoryByDate(Date date);
    Theory getTheoryByTheme(String theme);

    float getAVGGradeForStudent(Long id);

    List<Long> getAllGrades(Long id);
}

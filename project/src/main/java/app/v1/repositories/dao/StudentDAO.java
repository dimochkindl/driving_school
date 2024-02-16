package app.v1.repositories.dao;

import app.v1.dto.StudentDTO;
import app.v1.entities.Student;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface StudentDAO extends BaseRepository<Student> {
    StudentDTO getBySurname(String surname);
    List<Integer> getTheoryGrades(Long id);

    List<Integer> getExamResults(Long id);

    int getVisitedPractices(Long id);

}

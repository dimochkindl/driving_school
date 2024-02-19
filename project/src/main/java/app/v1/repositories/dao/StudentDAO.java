package app.v1.repositories.dao;

import app.v1.entities.*;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface StudentDAO extends BaseRepository<Student> {
    List<Student> getBySurname(String surname);

    List<Integer> getTheoryGrades(Long id);

    List<Integer> getExamResults(Long id);

    int getVisitedPractices(Long id);
    List<Exam> getExams(Long id);
    List<Practice> getPractices(Long id);

    Car getCarForPractice(Long id);
    List<Employee> getStudentsTeachers(Long id);

}

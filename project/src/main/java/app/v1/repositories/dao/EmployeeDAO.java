package app.v1.repositories.dao;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.Practice;
import app.v1.entities.Theory;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface EmployeeDAO extends BaseRepository<Employee> {

    List<Theory> getTheoryLessons(Long id);

    List<Employee> getBySurname(String surname);

    Post getPost(Long id);

    void retireFromTheory(Long id);

    void retireFromPractice(Long id);

    List<Practice> getPracticeLessons(Long id);

    void rateTheory(Long id, Long studentId, Long grade);

    int rateExam(Long id, Long studentId, Long grade);

}

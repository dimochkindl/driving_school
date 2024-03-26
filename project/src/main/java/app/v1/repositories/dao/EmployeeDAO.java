package app.v1.repositories.dao;

import app.v1.dto.filters.EmployeeFilter;
import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.id.ExamResultId;
import app.v1.entities.id.StudentTheoryRelationId;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface EmployeeDAO extends BaseRepository<Employee> {

    List<Object> getTheoryLessons(Long id);

    List<Object> getBySurnameAndLastName(EmployeeFilter filter);

    List<Object> getExams(Long id);

    List<Employee> getBySurname(String surname);

    Post getPost(Long id);

    void retireFromTheory(Long id);

    void retireFromPractice(Long id);

    List<Object> getPracticeLessons(Long id);

    void rateTheory(StudentTheoryRelationId id, Long grade);

    int rateExam(ExamResultId id, Long grade);

}

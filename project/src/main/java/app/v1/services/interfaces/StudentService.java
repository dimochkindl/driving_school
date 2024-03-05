package app.v1.services.interfaces;

import app.v1.entities.*;
import app.v1.services.BaseService;

import java.util.List;

public interface StudentService extends BaseService<Student> {
    List<Student> getBySurname(String surname);

    List<Integer> getTheoryGrades(Long id);

    List<Integer> getExamResults(Long id);

    int getVisitedPractices(Long id);
    List<Exam> getExams(Long id);
    List<Practice> getPractices(Long id);

    Car getCarForPractice(Long id);
    List<Employee> getStudentsTeachers(Long id);
}

package app.v1.repositories.dao;

import app.v1.entities.Employee;
import app.v1.entities.Exam;
import app.v1.repositories.BaseRepository;

import java.sql.Date;
import java.util.List;

public interface ExamDAO extends BaseRepository<Exam> {
    List<Exam> getExamsByDate(Date date);

    Employee getEmployeeByExam(Long id);

}

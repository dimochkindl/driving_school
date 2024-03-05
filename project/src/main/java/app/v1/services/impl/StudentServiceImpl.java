package app.v1.services.impl;

import app.v1.entities.*;
import app.v1.repositories.impl.StudentDAOIml;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan(basePackages = "app.v1.repositories")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    private final StudentDAOIml repository;

    @Autowired
    public StudentServiceImpl(StudentDAOIml repository) {
        super(repository);
        this.repository = repository;
    }


    @Override
    public List<Student> getBySurname(String surname) {
        return null;
    }

    @Override
    public List<Integer> getTheoryGrades(Long id) {
        return null;
    }

    @Override
    public List<Integer> getExamResults(Long id) {
        return null;
    }

    @Override
    public int getVisitedPractices(Long id) {
        return 0;
    }

    @Override
    public List<Exam> getExams(Long id) {
        return null;
    }

    @Override
    public List<Practice> getPractices(Long id) {
        return null;
    }

    @Override
    public Car getCarForPractice(Long id) {
        return null;
    }

    @Override
    public List<Employee> getStudentsTeachers(Long id) {
        return null;
    }
}

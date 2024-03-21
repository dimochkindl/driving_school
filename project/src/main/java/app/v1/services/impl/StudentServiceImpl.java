package app.v1.services.impl;

import app.v1.entities.*;
import app.v1.repositories.impl.StudentDAOIml;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = "app.v1.repositories")
@Slf4j
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    private final StudentDAOIml repository;

    @Autowired
    public StudentServiceImpl(StudentDAOIml repository) {
        super(repository);
        this.repository = repository;
    }


    @Override
    public List<Student> getBySurname(String surname) {
        if(surname != null){
            var list = repository.getBySurname(surname);
            return list;
        }else {
            log.warn("retrieving no one student by surname");
        }
        return null;
    }

    @Override
    public List<Integer> getTheoryGrades(Long id) {
        return repository.getTheoryGrades(id.intValue());
    }

    @Override
    public List<Integer> getExamResults(Long id) {
        List<Integer> results = new ArrayList<>();
        if(id != 0){
            var list = repository.getExamResults(id);
            for(var value : list){
                if (value instanceof Integer template) {
                    results.add(template);
                } else {
                    log.warn("value is not the instance of Integer");
                }
            }
        }
        return results;
    }

    @Override
    public int getVisitedPractices(Long id) {
        return repository.getVisitedPractices(id);
    }

    @Override
    public List<Exam> getExams(Long id) {
        return repository.getExams(id);
    }

    @Override
    public List<Practice> getPractices(Long id) {
        return repository.getPractices(id);
    }

    @Override
    public Car getCarForPractice(Long id) {
        return repository.getCarForPractice(id);
    }

    @Override
    public List<Employee> getStudentsTeachers(Long id) {
        return repository.getStudentsTeachers(id);
    }
}

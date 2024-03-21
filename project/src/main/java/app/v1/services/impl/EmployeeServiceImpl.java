package app.v1.services.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.Practice;
import app.v1.entities.Theory;
import app.v1.entities.id.ExamResultId;
import app.v1.entities.id.StudentTheoryRelationId;
import app.v1.repositories.impl.EmployeeDAOImpl;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = "app.v1.repositories")
@Slf4j
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {

    private final EmployeeDAOImpl repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAOImpl repository) {
        super(repository);
        this.repository = repository;
    }


    @Override
    public List<Theory> getTheoryLessons(Long id) {
        List<Theory> theories = new ArrayList<>();
        if(id != 0){
            var list = repository.getTheoryLessons(id);
            for(var value : list){
                if (value instanceof Theory theory) {
                    theories.add(theory);
                } else {
                    log.warn("value is not the instance of Theory");
                }
            }
        }
        return theories;
    }

    @Override
    public List<Employee> getBySurname(String surname) {
        if(surname != null){
            var list = repository.getBySurname(surname);
            return list;
        }
        log.warn("returning the empty list");
        return null;
    }

    @Override
    public Post getPost(Long id) {
        if(id != 0) return repository.getPost(id);
        return null;
    }

    @Override
    public void retireFromTheory(Long id) {
        repository.retireFromTheory(id);
    }

    @Override
    public void retireFromPractice(Long id) {
        repository.retireFromPractice(id);
    }

    @Override
    public List<Practice> getPracticeLessons(Long id) {
        List<Practice> practices = new ArrayList<>();
        if(id != 0){
            var list = repository.getPracticeLessons(id);
            for(var value : list){
                if (value instanceof Practice practice) {
                    practices.add(practice);
                } else {
                    log.warn("value is not the instance of Theory");
                }
            }
        }
        return practices;
    }

    @Override
    public void rateTheory(StudentTheoryRelationId id, Long grade) {
        repository.rateTheory(id, grade);
    }

    @Override
    public int rateExam(ExamResultId id, Long grade) {
        repository.rateExam(id, grade);
        return grade.intValue();
    }
}

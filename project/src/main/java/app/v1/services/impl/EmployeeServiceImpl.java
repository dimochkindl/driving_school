package app.v1.services.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.Practice;
import app.v1.entities.Theory;
import app.v1.repositories.impl.EmployeeDAOImpl;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan(basePackages = "app.v1.repositories")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {

    private final EmployeeDAOImpl repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAOImpl repository) {
        super(repository);
        this.repository = repository;
    }


    @Override
    public List<Theory> getTheoryLessons(Long id) {
        return null;
    }

    @Override
    public List<Employee> getBySurname(String surname) {
        return null;
    }

    @Override
    public Post getPost(Long id) {
        return null;
    }

    @Override
    public void retireFromTheory(Long id) {

    }

    @Override
    public void retireFromPractice(Long id) {

    }

    @Override
    public List<Practice> getPracticeLessons(Long id) {
        return null;
    }

    @Override
    public void rateTheory(Long id, Long studentId, Long grade) {

    }

    @Override
    public int rateExam(Long id, Long studentId, Long grade) {
        return 0;
    }
}

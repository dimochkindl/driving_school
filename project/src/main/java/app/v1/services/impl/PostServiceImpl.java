package app.v1.services.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.repositories.impl.PostDAOImpl;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = "app.v1.repositories")
@Slf4j
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {

    private final PostDAOImpl repo;

    @Autowired
    public PostServiceImpl(PostDAOImpl repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public List<Employee> getEmployeesByPostId(Long id) {
        var result = repo.getEmployeesByPostId(id);
        List<Employee> employees = new ArrayList<>();
        log.info("list of employees: {}", result);
        for (var value : result) {
            if (value instanceof Employee employee) {
                employees.add(employee);
            } else {
                log.warn("list is null or value is not instance of Employee");
            }
        }
        return employees;
    }

    @Override
    public List<Employee> getBySpecialization(String spec) {
        var result = repo.getBySpecialization(spec);
        List<Employee> employees = new ArrayList<>();
        log.info("list of employees: {}", result);
        for (var value : result) {
            if (value instanceof Employee employee) {
                employees.add(employee);
            } else {
                log.warn("list is null or value is not instance of Employee");
            }
        }
        return employees;
    }

    @Override
    public List<Employee> getByPost(String post) {
        var result = repo.getByPost(post);
        List<Employee> employees = new ArrayList<>();
        log.info("list of employees: {}", result);
        for (var value : result) {
            if (value instanceof Employee employee) {
                employees.add(employee);
            } else {
                log.warn("list is null or value is not instance of Employee");
            }
        }
        return employees;
    }
}

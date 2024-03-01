package app.v1.services.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.repositories.impl.PostDAOImpl;
import app.v1.services.BaseServiceImpl;
import app.v1.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {

    private final PostDAOImpl repo;

    @Autowired
    public PostServiceImpl(PostDAOImpl repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public List<Employee> getEmployeesByPostId(Long id) {
        return null;
    }

    @Override
    public List<Employee> getBySpecialization(String spec) {
        return null;
    }

    @Override
    public List<Employee> getByPost(String post) {
        return null;
    }
}

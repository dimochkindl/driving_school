package app.v1.services.interfaces;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.services.BaseService;

import java.util.List;

public interface PostService extends BaseService<Post> {
    List<Employee> getEmployeesByPostId(Long id);

    List<Employee> getBySpecialization(String spec);

    List<Employee> getByPost(String post);
}

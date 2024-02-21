package app.v1.repositories.dao;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface PostDAO extends BaseRepository<Post> {
    List<Employee> getEmployeesByPostId(Long id);

    List<Employee> getBySpecialization(String spec);

    List<Employee> getByPost(String post);
}

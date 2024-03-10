package app.v1.repositories.dao;

import app.v1.entities.Post;
import app.v1.repositories.BaseRepository;

import java.util.List;

public interface PostDAO extends BaseRepository<Post> {
    List<Object> getEmployeesByPostId(Long id);

    List<Object> getBySpecialization(String spec);

    List<Object> getByPost(String post);
}

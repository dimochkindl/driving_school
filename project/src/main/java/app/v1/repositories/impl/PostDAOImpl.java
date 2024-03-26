package app.v1.repositories.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.PostDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class PostDAOImpl implements PostDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public PostDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getAll() {
        Connection connection = DbConnector.getConnection();
        List<Post> posts = new ArrayList<>();

        String query = "select * from post";

        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String spec = rs.getString("specialization");
                    String name = rs.getString("name");

                    posts.add(Post.builder()
                            .id(id)
                            .specialization(spec)
                            .name(name)
                            .build());
                }

                return posts;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Post getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from post where id = ?";

        try(PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)){

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String spec = rs.getString("specialization");
                String name = rs.getString("name");
                return Post.builder()
                        .id(id)
                        .specialization(spec)
                        .name(name)
                        .build();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DbConnector.getConnection();
        String query = "delete from post where id = ?";

        try(PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)){

            statement.setLong(1, id);
            int rowsChanged = statement.executeUpdate();

            System.out.println("Rows affected after deleting post by id: "+rowsChanged);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        Connection connection = DbConnector.getConnection();
        String query = "insert into post  (specialization, name) values(?, ?)";
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {

            statement.setString(1, post.getSpecialization());
            statement.setString(2, post.getSpecialization());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after saving the post: " + saved + " post: "+ post.getPostAsString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Post post) {
        Connection connection = DbConnector.getConnection();
        String query = "update post set specialization = ?, name = ? where id = ?";
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {
            statement.setString(1, post.getSpecialization());
            statement.setString(2, post.getSpecialization());
            statement.setLong(3, post.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Object> getEmployeesByPostId(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = builder.createQuery();
        var employees = criteria.from(Employee.class);
        var post = employees.join("post");
        criteria.select(employees).where(builder.equal(post.get("id"), id));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Object> getBySpecialization(String spec) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery();
        var employees = criteria.from(Employee.class);
        var post = employees.join("post");
        criteria.select(employees).where(builder.equal(post.get("specialization"), spec));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Object> getByPost(String post) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = builder.createQuery();
        var employees = criteria.from(Employee.class);
        var posts = employees.join("post");
        criteria.select(employees).where(builder.equal(posts.get("name"), post));
        return session.createQuery(criteria).list();
    }
}

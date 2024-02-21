package app.v1.repositories.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.PostDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAOImpl implements PostDAO {

    @Override
    public List<Post> getAll() {
        Connection connection = DbConnector.getConnection();
        List<Post> posts = new ArrayList<>();

        String query = "select * from post";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String spec = rs.getString("specialization");
                String name = rs.getString("name");

                posts.add(new Post(id, spec, name));
            }

            return posts;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Post getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from post where id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String spec = rs.getString("specialization");
                String name = rs.getString("name");
                return new Post(id, spec, name);
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

        try(PreparedStatement statement = connection.prepareStatement(query)){

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
        String query = "insert into post  (id, specialization, name) values(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, post.getId());
            statement.setString(2, post.getSpecialization());
            statement.setString(3, post.getSpecialization());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after saving the post: " + saved + " post: "+ post.getPostAsString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Post post) {
        Connection connection = DbConnector.getConnection();
        String query = "update post set specialization = ?, name = ? where id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getSpecialization());
            statement.setString(2, post.getSpecialization());
            statement.setLong(3, post.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    //hibernate
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

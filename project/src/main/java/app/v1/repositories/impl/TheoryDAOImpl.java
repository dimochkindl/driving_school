package app.v1.repositories.impl;

import app.v1.entities.Theory;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.TheoryDAO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class TheoryDAOImpl implements TheoryDAO {
    @Override
    public List<Theory> getAll() {
        Connection connection = DbConnector.getConnection();
        List<Theory> theories = new ArrayList<>();

        /*String query = "select theory.*, student_theory_relation.* " +
                "from theory " +
                "join student_theory_relation on student_theory_relation.theory_id = theory.id";
*/
        String query = "select * from theory";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String theme = rs.getString("theme");
                float price = rs.getFloat("price");
                theories.add(Theory.builder()
                        .id(id)
                        .theme(theme)
                        .price(price)
                        .build());
            }

            return theories;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Theory getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from theory where id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String theme = rs.getString("theme");
                float price = rs.getFloat("price");
                return Theory.builder().id(id).theme(theme).price(price).build();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Theory theory) {
        Connection connection = DbConnector.getConnection();
        String query = "update theory set theme = ?, price = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(3, theory.getId());
            statement.setString(1, theory.getTheme());
            statement.setFloat(2, theory.getPrice());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after updating the theory: " + saved);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DbConnector.getConnection();
        String query = "delete from theory where id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            int rowsChanged = statement.executeUpdate();

            System.out.println("Rows affected after deleting theory by id: "+rowsChanged);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Theory theory) {
        Connection connection = DbConnector.getConnection();
        String query = "insert into theory  (theme, price) values( ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, theory.getTheme());
            statement.setFloat(2, theory.getPrice());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after saving the theory: " + saved);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Theory> getTheoryByDate(Date date) {
        Connection connection = DbConnector.getConnection();
        List<Theory> theories = new ArrayList<>();
        String query = "select * from theory t " +
                "join student_theory_relation st on st.theory_id = t.id " +
                "where date = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setDate(1, date);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String theme = rs.getString("theme");
                float price = rs.getFloat("price");
                 theories.add( Theory.builder()
                         .id(id)
                         .theme(theme)
                         .price(price)
                         .build());
            }

            return theories;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Theory getTheoryByTheme(String theme) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from theory where theme = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, theme);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Long id = rs.getLong("id");
                float price = rs.getFloat("price");
                return Theory.builder()
                        .id(id)
                        .theme(theme)
                        .price(price)
                        .build();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public float getAVGGradeForStudent(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select avg(grade) from theory t " +
                "join student_theory_relation st on st.theory_id = t.id " +
                "where st.student_id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getFloat(1);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Long> getAllGrades(Long id) {
        Connection connection = DbConnector.getConnection();
        List<Long> grades = new ArrayList<>();
        String query = "select grade from theory t " +
                "join student_theory_relation st on st.theory_id = t.id " +
                "where st.student_id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                grades.add(rs.getLong(1));
            }

            return grades;

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}

package app.v1.repositories.impl;

import app.v1.entities.Employee;
import app.v1.entities.Exam;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.ExamDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ExamDAOImpl implements ExamDAO {
    @Override
    public List<Exam> getAll() {
        Connection connection = DbConnector.getConnection();
        List<Exam> exams = new ArrayList<>();

        String query = "select * from exam";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String exam = rs.getString("exam");
                Date date = rs.getDate("date");
                Long grade = rs.getLong("grade");

                exams.add(new Exam(id, exam, date.toLocalDate(), grade));
            }

            return exams;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Exam getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from exam where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String exam = rs.getString("exam");
                Date date = rs.getDate("date");
                Long grade = rs.getLong("grade");

                return new Exam(id, exam, date.toLocalDate(), grade);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Exam exam) {
        Connection connection = DbConnector.getConnection();
        String query = "update exam set exam = ?, date = ?, grade = ? where id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, exam.getExam());
            statement.setDate(2, Date.valueOf(exam.getDate()));
            statement.setLong(3, exam.getGrade());
            statement.setLong(4, exam.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DbConnector.getConnection();
        String query = "delete from exam where id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);
            int rowsChanged = statement.executeUpdate();

            System.out.println("Rows affected after deleting exam by id: "+rowsChanged);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Exam exam) {
        Connection connection = DbConnector.getConnection();
        String query = "insert into exam  (id, exam, date, grade) values(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(2, exam.getExam());
            statement.setDate(3, Date.valueOf(exam.getDate()));
            statement.setLong(4, exam.getGrade());
            statement.setLong(1, exam.getId());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after saving the new exam: " + saved + " exam: "+ exam.getExam());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public List<Exam> getExamsByDate(Date date) {
        Connection connection = DbConnector.getConnection();
        List<Exam> exams = new ArrayList<>();

        String query = "select * from exam where date = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, date);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String exam = rs.getString("exam");
                Long grade = rs.getLong("grade");

                exams.add(new Exam(id, exam, date.toLocalDate(), grade));
            }

            return exams;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    //hibernate
    @Override
    public Employee getEmployeeByExam(Long id) {
        return null;
    }
}

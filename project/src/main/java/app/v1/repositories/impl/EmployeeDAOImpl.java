package app.v1.repositories.impl;

import app.v1.entities.*;
import app.v1.repositories.dao.EmployeeDAO;
import lombok.Cleanup;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.v1.repositories.DbConnector.getConnection;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAll() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<Employee> employees = new ArrayList<>();

        String query = "select * from employee";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String phoneNumber = resultSet.getString("phone_number");
        float experience = resultSet.getFloat("experience");

        return new Employee(id, name, surname, phoneNumber, experience);
    }

    @Override
    public Employee getById(Long id) {
        Connection connection = getConnection();
        String query = "select * from employee where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createEmployee(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Employee employee) {
        Connection connection = getConnection();
        String query = "update employee set name = ? , surname = ?, phone_number = ?, experience = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPhone());
            statement.setFloat(4, employee.getExperience());
            statement.setLong(5, employee.getId());


            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            executeUpdate(connection, "delete from employee where id = ?", id);
            executeUpdate(connection, "delete from exam_results where teacher_id = ?", id);
            executeUpdate(connection, "delete from student_practice_relation where teacher_id = ?", id);
            executeUpdate(connection, "delete from student_theory_relation where teacher_id = ?", id);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(Connection connection, String query, Long id) throws SQLException {
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public void save(Employee employee) {
        Connection connection = getConnection();
        String query = "insert into employee  (name, surname, phone_number, experience, post_id) values(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPhone());
            statement.setFloat(4, employee.getExperience());
            statement.setLong(5, employee.getPost().getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employee> getBySurname(String surname) {
        Connection connection = getConnection();
        String query = "select * from employee where surname = ?";
        List<Employee> employees = null;
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setString(1, surname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getPost(Long id) {
        Connection connection = getConnection();
        String query = "select * from post p join employee e on p.id = e.post_id where e.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) { // Проверяем, есть ли следующая запись
                Long post_id = rs.getLong("id");
                String specialization = rs.getString("specialization");
                String name = rs.getString("name");
                return new Post(post_id, specialization, name);
            } else {
                // Можно выбрать другое действие, например, бросить исключение или вернуть null, в зависимости от вашего случая использования.
                System.out.println("Запись с id=" + id + " не найдена.");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Не забывайте закрывать ресурсы, например, Connection, после использования
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void retireFromTheory(Long id) {
        Connection connection = getConnection();
        String query = "delete from student_theory_relation where teacher_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsChanged = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void retireFromPractice(Long id) {
        Connection connection = getConnection();
        String query = "delete from student_practice_relation where teacher_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsChanged = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    //for hibernate
    @Override
    public List<Practice> getPracticeLessons(Long id) {
        return null;
    }

    @Override
    public List<Theory> getTheoryLessons(Long id) {
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

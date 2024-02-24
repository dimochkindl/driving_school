package app.v1.repositories.impl;

import app.v1.entities.*;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.StudentDAO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@Repository
public class StudentDAOIml extends DbConnector implements StudentDAO {
    @Override
    public List<Student> getAll() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<Student> students = new ArrayList<>();

        String query = "select * from student";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Student student = createStudent(resultSet);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getById(Long id) {
        Connection connection = getConnection();
        String query = "select * from student where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createStudent(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Student> getBySurname(String surname) {
        Connection connection = getConnection();
        String query = "select * from student where surname = ?";
        List<Student> students = null;
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setString(1, surname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student stud = createStudent(resultSet);
                students.add(stud);
            }

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student student) {
        Connection connection = getConnection();
        String query = "update student set name = ? , surname = ?, phone_number = ?, category = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getPhone());
            statement.setString(4, student.getCategory());
            statement.setLong(5, student.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            executeUpdate(connection, "delete from student where id = ?", id);
            executeUpdate(connection, "delete from exam_results where student_id = ?", id);
            executeUpdate(connection, "delete from student_practice_relation where student_id = ?", id);
            executeUpdate(connection, "delete from student_theory_relation where student_id = ?", id);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(Connection connection, String query, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }


    @Override
    public void save(Student student) {
        Connection connection = getConnection();
        String query = "insert into student  (id, name, surname, phone_number, category) values(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setString(4, student.getPhone());
            statement.setString(5, student.getCategory());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Exam> getExams(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<Exam> exams = new ArrayList<>();

        String query = "select from exam e join exam_results er on e.id = er.exam_id where er.student_id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long examId = resultSet.getLong("id");
                String examName = resultSet.getString("exam");
                Date examDate = resultSet.getDate("date");
                Long examGrade = resultSet.getLong("grade");

                Exam exam = new Exam(examId, examName, examDate.toLocalDate(), examGrade);
                exams.add(exam);
            }

            return exams;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    public List<Practice> getPractices(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<Practice> practices = new ArrayList<>();
        String query = "select * from practice p join student_practice_relation sp on p.id = sp.practice_id where sp.student_id = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long practiceId = rs.getLong("id");
                Date date = rs.getDate("date");
                float price = rs.getFloat("price");
                String place = rs.getString("place");
                Long car_id = rs.getLong("car_id");

                Car car = getCarForPractice(car_id);
                Practice practice = new Practice(practiceId, date.toLocalDate(), place, price, car);
                practices.add(practice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return practices;
    }

    public Car getCarForPractice(Long id) {
        Connection connection = getConnection();
        String query = "select * from car where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            Long car_id = rs.getLong("id");
            String number = rs.getString("car_number");
            String model = rs.getString("model");
            Long year = rs.getLong("year");

            return new Car(car_id, number, model, year);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Post getPost(Long id) {
        Connection connection = getConnection();
        List<Practice> practices = new ArrayList<>();
        String query = "select * from post where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            Long post_id = rs.getLong("id");
            String specialization = rs.getString("specialization");
            String name = rs.getString("name");

            return new Post(post_id, specialization, name);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Employee> getStudentsTeachers(Long id) {
        Connection connection = getConnection();
        List<Employee> employees = new ArrayList<>();

        String query = "select * from employee e " +
                "join student_practice_relation sp on e.id=sp.teacher_id and sp.student_id = ? " +
                "join student_theory_relation st on e.id = st.teacher_id and st.student_id = ? " +
                "join exam_results er on e.id = st.teacher_id and er.student_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.setLong(2, id);
            statement.setLong(3, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long employee_id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phone_number");
                float experience = rs.getFloat("experience");
                Long post_id = rs.getLong("post_id");
                Employee employee = new Employee(employee_id, name, surname, phoneNumber, experience, getPost(post_id));
                employees.add(employee);
            }

            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Student createStudent(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String phoneNumber = resultSet.getString("phone_number");
        String category = resultSet.getString("category");

        return new Student(id, name, surname, phoneNumber, category);
    }

    public Long getLastId() {
        Connection connection = getConnection();
        String query = "SELECT id FROM student ORDER BY id DESC LIMIT 1;\n";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                long lastId = rs.getLong("id");
                return lastId;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Methods for hibernate
    @Override
    public List<Integer> getTheoryGrades(Long id) {
        return null;
    }

    @Override
    public List<Integer> getExamResults(Long id) {
        return null;
    }

    @Override
    public int getVisitedPractices(Long id) {
        return 0;
    }
}
package app.v1.repositories.impl;

import app.v1.entities.Student;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOImlTest {

    private final String password = "lilasgard228";
    private final String username = "postgres";

    private final String url = "jdbc:postgresql://localhost:5432/driving_school";

    private StudentDAOIml repo = new StudentDAOIml();

    @Test
    void getAll() {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            List<Student> students = repo.getAll();
            assertEquals(1, students.size());
            for (var student : students) {
                System.out.println(student);
            }

        } catch (SQLException e) {
            fail("Exception during test: " + e.getMessage());
        }
    }

    @SneakyThrows
    @Test
    void save() {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Student student = new Student();

            student.setName("Mishanya");
            student.setSurname("Hos");
            student.setPhone("+4354234");
            student.setCategory("A");

            Long id = repo.getLastId();
            student.setId(++id);
            repo.save(student);

            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM student WHERE name = ?");
            statement.setString(1, "Alena");
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            int count = resultSet.getInt(1);
            assertEquals(1, count);
        }
    }

    @Test
    void getBySurname() {
        String surnameToFind = "Hos";

        List<Student> foundStudent = repo.getBySurname(surnameToFind);
        assertNotNull(foundStudent);

        for (var student : foundStudent) {
            assertEquals(surnameToFind, student.getSurname());
        }

    }

    @Test
    void delete() {
        Long id = repo.getLastId();
        Student studentBeforeDeletion = repo.getById(id);
        assertNotNull(studentBeforeDeletion);
        System.out.println("Before deletion: " + studentBeforeDeletion);

        repo.delete(id);

        Student studentAfterDeletion = repo.getById(id);
        assertNull(studentAfterDeletion);
        System.out.println("After deletion: " + studentAfterDeletion);
    }

}
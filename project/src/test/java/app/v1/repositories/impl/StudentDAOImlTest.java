package app.v1.repositories.impl;

import app.v1.entities.Student;
import app.v1.repositories.DbConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentDAOImlTest {

    private StudentDAOIml repo;
    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        repo = new StudentDAOIml();

        // Создаем моки
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Определяем поведение моков
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Моки для DbConnector
        mockStatic(DbConnector.class);
        when(DbConnector.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void getAll() throws SQLException {
        // Определяем поведение мока ResultSet
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        // Ваш тестовый код
        List<Student> students = repo.getAll();

        // Проверки
        assertEquals(1, students.size());
        for (var student : students) {
            System.out.println(student);
        }
    }

    @Test
    void save() throws SQLException {
        // Ваш тестовый код

        // Определяем поведение мока PreparedStatement и ResultSet
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(1L);

        // Ваш тестовый код
        Student student = new Student();
        student.setName("Mishanya");
        student.setSurname("Hos");
        student.setPhone("+4354234");
        student.setCategory("A");

        Long id = repo.getLastId();
        student.setId(++id);
        repo.save(student);

        // Проверки
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getBySurname() throws SQLException {
        // Определяем поведение мока ResultSet
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        // Ваш тестовый код
        String surnameToFind = "Hos";
        List<Student> foundStudent = repo.getBySurname(surnameToFind);

        // Проверки
        assertNotNull(foundStudent);
        for (var student : foundStudent) {
            assertEquals(surnameToFind, student.getSurname());
        }
    }

    @Test
    void delete() throws SQLException {
        // Ваш тестовый код

        // Определяем поведение мока ResultSet
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        // Ваш тестовый код
        Long id = repo.getLastId();
        Student studentBeforeDeletion = repo.getById(id);
        assertNotNull(studentBeforeDeletion);
        System.out.println("Before deletion: " + studentBeforeDeletion);

        repo.delete(id);

        // Проверки
        verify(mockPreparedStatement, times(1)).executeUpdate();

        Student studentAfterDeletion = repo.getById(id);
        assertNull(studentAfterDeletion);
        System.out.println("After deletion: " + studentAfterDeletion);
    }
}

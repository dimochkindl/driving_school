package app.v1.repositories.impl;

import app.v1.entities.Student;
import app.v1.repositories.DbConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudentDAOImlTest {

    private StudentDAOIml repo;

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        try (MockedStatic<DbConnector> mockedStatic = mockStatic(DbConnector.class)) {
            mockedStatic.when(DbConnector::getConnection).thenReturn(mockConnection);
        }

        repo = new StudentDAOIml();
    }

    @Test
    void getAll() throws SQLException {
        prepareMockResultSet(true, false);

        List<Student> students = repo.getAll();

        assertEquals(1, students.size());
        students.forEach(student -> System.out.println(student));
    }

    @Test
    void save() throws SQLException {
        prepareMockPreparedStatementAndResultSet(true);

        Student student = prepareStudent();
        Long id = repo.getLastId();
        student.setId(++id);
        repo.save(student);

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getBySurname() throws SQLException {
        prepareMockResultSet(true, false);

        String surnameToFind = "Hos";
        List<Student> foundStudent = repo.getBySurname(surnameToFind);

        assertNotNull(foundStudent);
        foundStudent.forEach(student -> assertEquals(surnameToFind, student.getSurname()));
    }

    @Test
    void delete() throws SQLException {
        prepareMockResultSet(true, false);

        Long id = repo.getLastId();
        Student studentBeforeDeletion = repo.getById(id);
        assertNotNull(studentBeforeDeletion);
        System.out.println("Before deletion: " + studentBeforeDeletion);

        repo.delete(id);

        verify(mockPreparedStatement, times(1)).executeUpdate();

        Student studentAfterDeletion = repo.getById(id);
        assertNull(studentAfterDeletion);
        System.out.println("After deletion: " + studentAfterDeletion);
    }

    private void prepareMockResultSet(boolean... nextReturnValues) throws SQLException {
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        for (boolean nextReturnValue : nextReturnValues) {
            when(mockResultSet.next()).thenReturn(nextReturnValue);
        }
    }

    private void prepareMockPreparedStatementAndResultSet(boolean nextReturnValue) throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(nextReturnValue);
        when(mockResultSet.getLong(1)).thenReturn(1L);
    }

    private Student prepareStudent() {
        Student student = new Student();
        student.setName("Mishanya");
        student.setSurname("Hos");
        student.setPhone("+4354234");
        student.setCategory("A");
        return student;
    }
}


package app.v1.repositories.impl;

import app.v1.entities.Employee;
import app.v1.entities.Exam;
import app.v1.entities.ExamResults;
import app.v1.entities.Student;
import app.v1.entities.id.ExamResultId;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ComponentScan(basePackages = {"app.v1.repositories"})
class ExamDAOImplTest {

    private final ExamDAOImpl dao;

    private final SessionFactory sessionFactory;

    @Autowired
    ExamDAOImplTest(ExamDAOImpl dao, SessionFactory sessionFactory) {
        this.dao = dao;
        this.sessionFactory = sessionFactory;
    }

    @Test
    void getEmployeeByExam() {
        var exam = Exam.builder().grade(4L).exam("driving").build();
        var exam2 = Exam.builder().exam("1st test").grade(8L).build();

        var employee = Employee.builder().name("Misha").surname("Litvin").phone("11111111").build();
        var employee2 = Employee.builder().name("Nastya").surname("Koroleva").phone("2222222").build();

        var student = Student.builder().name("Alex").surname("Ivanov").phone("3333333").build();

        var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(exam);
        session.persist(exam2);

        session.persist(employee);
        session.persist(employee2);

        session.persist(student);
        session.flush();
        var id = new ExamResultId(student.getId(), employee.getId(), exam.getId());
        var id2 = new ExamResultId(student.getId(), employee2.getId(), exam2.getId());
        var examResult = ExamResults.builder().id(id).exam(exam).student(student).teacher(employee).build();
        var result2 = ExamResults.builder().id(id2).exam(exam2).student(student).teacher(employee2).build();
        session.persist(examResult);
        session.persist(result2);

        session.getTransaction().commit();
        session.close();

        Employee result = (Employee) dao.getEmployeeByExam(employee.getId());
        log.info("\n\nresult: {}\n\n", result);

        assertEquals("Misha", employee.getName());
    }
}
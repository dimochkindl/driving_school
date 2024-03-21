package app.v1.repositories.impl;

import app.v1.entities.*;
import app.v1.entities.id.ExamResultId;
import app.v1.entities.id.StudentPracticeRelationId;
import app.v1.entities.id.StudentTheoryRelationId;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@ComponentScan({"app.v1.repositories", "app.v1.entities"})
class StudentDAOImlTest {

    private final StudentDAOIml dao;

    private final SessionFactory sessionFactory;

    @Autowired
    StudentDAOImlTest(StudentDAOIml dao, SessionFactory sessionFactory) {
        this.dao = dao;
        this.sessionFactory = sessionFactory;
    }

    @Test
    void getTheoryGrades() {
        var theory = Theory.builder().price(10).theme("1st theme").build();
        var theory1 = Theory.builder().price(15).theme("2st theme").build();
        var theory2 = Theory.builder().price(20).theme("3st theme").build();
        var list = new ArrayList<>();
        list.add(theory);
        list.add(theory1);
        list.add(theory2);

        var employee = Employee.builder().name("Misha").surname("Litvin").phone("11111111").build();
        var employee1 = Employee.builder().name("Nastya").surname("Koroleva").phone("2222222").build();

        var student = Student.builder().name("Alex").surname("Ivanov").phone("3333333").build();

        var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(student);

        session.persist(employee1);
        session.persist(employee);
        log.info("\nSaved employees, student, theories\n");

        for (var th : list) {
            session.persist(th);
        }

        session.flush();

        StudentTheoryRelationId id = new StudentTheoryRelationId(student.getId(), employee.getId(), theory.getId());
        StudentTheoryRelationId id1 = new StudentTheoryRelationId(student.getId(), employee.getId(), theory1.getId());
        StudentTheoryRelationId id2 = new StudentTheoryRelationId(student.getId(), employee1.getId(), theory2.getId());
        var relation = StudentTheoryRelation.builder().id(id).student(student).teacher(employee).theory(theory).grade((short) 7).build();
        var relation1 = StudentTheoryRelation.builder().id(id1).student(student).teacher(employee).theory(theory1).grade((short) 10).build();
        var relation2 = StudentTheoryRelation.builder().id(id2).student(student).teacher(employee1).theory(theory2).grade((short) 4).build();

        session.persist(relation);
        session.persist(relation1);
        session.persist(relation2);
        session.flush();

        session.getTransaction().commit();
        session.close();
        var grades = dao.getTheoryGrades(1);
        log.info("\nstudent: {}\ngrades\n: {}", student, grades);


        assertEquals(3, grades.size());
    }

    @Test
    void getExamResults() {
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

        var list = dao.getExamResults(student.getId());
        log.info("\n\nresult: {}\n\n", list);

        assertEquals(2, list.size());
    }

    @Test
    void getVisitedPractices() {
        var employee = Employee.builder().name("Misha").surname("Litvin").phone("11111111").build();
        var practice = Practice.builder().place("Yanki Kupaly square").price(40).build();
        var practice2 = Practice.builder().place("Yakuba Kolasa square").price(40).build();
        var student = Student.builder().name("Alex").surname("Ivanov").phone("3333333").build();

        var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(employee);
        session.persist(student);
        session.persist(practice2);
        session.persist(practice);

        session.flush();

        var id =  new StudentPracticeRelationId(student.getId(), employee.getId(), practice2.getId());
        var id2 = new StudentPracticeRelationId(student.getId(), employee.getId(), practice.getId());
        var relation1 = StudentPracticeRelation.builder().id(id).practice(practice2).student(student).teacher(employee).build();
        var relation2 = StudentPracticeRelation.builder().id(id2).practice(practice).student(student).teacher(employee).build();

        session.persist(relation1);
        session.persist(relation2);
        session.getTransaction().commit();

        var count = dao.getVisitedPractices(student.getId());
        log.info("\nVisited practices for {}: {}\n", student, count);

        assertEquals(2, count);
    }
}
package app.v1.repositories.impl;

import app.v1.dto.filters.EmployeeFilter;
import app.v1.entities.Employee;
import app.v1.entities.Practice;
import app.v1.entities.StudentPracticeRelation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.AbstractList;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ComponentScan(basePackages = "app.v1.repositories")
class EmployeeDAOImplTest {

    private final SessionFactory sessionFactory;
    private EmployeeDAOImpl dao;

    @Autowired
    EmployeeDAOImplTest(SessionFactory sessionFactory, EmployeeDAOImpl dao) {
        this.sessionFactory = sessionFactory;
        this.dao = dao;
    }

    @Test
    void getBySurnameAndLastName() {
        Session session = sessionFactory.openSession();
        var employee = Employee.builder().name("Dina").surname("Saeva").phone("+7953289091").build();
        var employee2 = Employee.builder().name("Misha").surname("Litvin").phone("+79511189091").build();
        session.beginTransaction();
        session.persist(employee);
        session.persist(employee2);
        session.flush();
        session.getTransaction().commit();
        log.info("Add employees: {}, {}", employee, employee2);

        EmployeeFilter filter = EmployeeFilter.builder().firstname(employee.getName()).lastname(employee.getSurname()).build();
        var employees = dao.getBySurnameAndLastName(filter);
        log.info("First employee by name and surname: {}", employees);

        EmployeeFilter filter2 = EmployeeFilter.builder().firstname(employee2.getName()).build();
        var employees2 = dao.getBySurnameAndLastName(filter2);
        log.info("Second employee by name: {}", employees2);


        EmployeeFilter filter3 = EmployeeFilter.builder().lastname(employee2.getSurname()).build();
        var employees3 = dao.getBySurnameAndLastName(filter3);
        log.info("Second employee by name: {}", employees3);

        assertEquals(1, employees.size());
        assertEquals(1, employees2.size());
        assertEquals(1, employees3.size());
    }

    @Test
    void getPracticeLessons() {
        Session session = sessionFactory.openSession();
        var employee = Employee.builder().name("Dina").surname("Saeva").phone("+7953289091").build();
        var practice = Practice.builder().price(30).place("Big ben").build();
        var practice2 = Practice.builder().price(45).place("Sovetskaya").build();
        var relation = StudentPracticeRelation.builder().teacher(employee).practice(practice).build();
        /*relation.setEmployee(employee);
        relation.setPractice(practice);*/

        var relation2 = StudentPracticeRelation.builder().practice(practice2).teacher(employee).build();
        /*relation2.setEmployee(employee);
        relation2.setPractice(practice2);*/

        session.beginTransaction();
        session.persist(employee);
        session.persist(practice);
        session.persist(practice2);
        session.flush();

        session.persist(relation);
        session.persist(relation2);
        session.getTransaction().commit();

        log.info("\n relation1 : ,{} - {}\n", relation.getTeacher(), relation.getPractice());
        log.info("\n relation2 : ,{} - {}\n", relation2.getTeacher(), relation2.getPractice());

        var relations = dao.getPracticeLessons(1L);
        log.info("\nrelations: {}\n", relations);

        assertEquals(2, relations.size());
    }
}
package app.v1.repositories.impl;

import app.v1.entities.Practice;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@ComponentScan("app.v1.repositories")
class PracticeDAOImplTest {

    private final PracticeDAOImpl dao;
    private final SessionFactory sessionFactory;

    @Autowired
    public PracticeDAOImplTest(PracticeDAOImpl dao, SessionFactory sessionFactory) {
        this.dao = dao;
        this.sessionFactory = sessionFactory;
    }

    @Test
    void getPracticesByDate() {

        LocalDate testDate = LocalDate.of(2022, 1, 21);
        String place1 = "Place 1";
        float price1 = 10.99f;

        String place2 = "Place 2";
        float price2 = 15.99f;

        Practice practice1 = Practice.builder().date(testDate).place(place1).price(price1).build();
        Practice practice2 = Practice.builder().date(testDate).place(place2).price(price2).build();

        @Cleanup var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();

        session.persist(practice1);
        session.persist(practice2);
        transaction.commit();

        var practices = dao.getPracticesByDate(testDate);
        log.info("\nList of found practices: {}\n", practices);
        assertEquals(2, practices.size());
    }

    @Test
    void getPracticeByPlace() {
        LocalDate testDate = LocalDate.of(2022, 1, 21);
        String place = "place";
        float price1 = 10.99f;
        float price2 = 15.99f;

        Practice practice1 = Practice.builder().date(testDate).place(place).price(price1).build();
        Practice practice2 = Practice.builder().date(testDate).place(place).price(price2).build();

        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(practice1);
        session.persist(practice2);

        session.getTransaction().commit();

        var practices = dao.getPracticeByPlace(place);
        log.info("List of practices: {}", practices);

        assertEquals(2, practices.size());

    }
}
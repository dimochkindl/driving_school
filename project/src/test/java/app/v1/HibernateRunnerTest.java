package app.v1;

import app.v1.entities.Car;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@Slf4j
@ComponentScan( basePackages = "app.v1.entities")
public class HibernateRunnerTest {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateRunnerTest(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    public void checkH2(){
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var car = Car.builder()
                .number("1212Pl")
                .model("Subaru")
                .year(2023L)
                .build();

        session.persist(car);
        session.getTransaction().commit();
        log.info("Car saved with ID: {}", car.getId());

    }
}


package app.v1.repositories.impl;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class PostDAOImplTest {

    private final SessionFactory sessionFactory;
    private final PostDAOImpl dao;

    @Autowired
    PostDAOImplTest(SessionFactory sessionFactory, PostDAOImpl dao) {
        this.sessionFactory = sessionFactory;
        this.dao = dao;
    }

    @Test
    void getEmployeesByPostId() {
        String postName = "instructor";
        var post = Post.builder().name(postName).build();

        var employee = Employee.builder()
                .name("Victor")
                .phone("12332191")
                .surname("Wemb")
                .post(post)
                .build();

        var employee2 = Employee.builder()
                .name("Danya")
                .phone("11332190")
                .surname("Milokhin")
                .post(post)
                .build();

        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(post);
        session.flush();

        session.persist(employee);
        session.persist(employee2);
        session.getTransaction().commit();

        var list = dao.getEmployeesByPostId(1L);
        log.info("\nEmployee :{}\n", list);
        assertEquals(2, list.size());
    }

    @Test
    void getByPost() {
        String postName = "instructor";
        var post = Post.builder().name(postName).build();

        var employee = Employee.builder()
                .name("Victor")
                .phone("12332191")
                .surname("Wemb")
                .post(post)
                .build();

        var employee2 = Employee.builder()
                .name("Danya")
                .phone("11332190")
                .surname("Milokhin")
                .post(post)
                .build();

        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(post);
        session.flush();

        session.persist(employee);
        session.persist(employee2);
        session.getTransaction().commit();

        var list = dao.getByPost(postName);
        log.info("\nlist of employees :{}\n", list);
        assertEquals(2, list.size());
    }

    @Test
    void getBySpecialization() {
        String postName = "instructor";
        String spec = "teacher";
        var post = Post.builder().name(postName).specialization(spec).build();

        var employee = Employee.builder()
                .name("Victor")
                .phone("12332191")
                .surname("Wemb")
                .post(post)
                .build();

        var employee2 = Employee.builder()
                .name("Danya")
                .phone("11332190")
                .surname("Milokhin")
                .post(post)
                .build();

        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(post);
        session.flush();

        session.persist(employee);
        session.persist(employee2);
        session.getTransaction().commit();

        var employees = dao.getBySpecialization(spec);
        log.info("\nlist : {}\n", employees);

        assertEquals(2, employees.size());
    }
}
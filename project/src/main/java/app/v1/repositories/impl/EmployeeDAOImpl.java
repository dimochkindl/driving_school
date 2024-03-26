package app.v1.repositories.impl;

import app.v1.dto.filters.EmployeeFilter;
import app.v1.entities.*;
import app.v1.entities.id.ExamResultId;
import app.v1.entities.id.StudentTheoryRelationId;
import app.v1.repositories.dao.EmployeeDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.v1.repositories.DbConnector.getConnection;

@Repository
@Slf4j
public class EmployeeDAOImpl implements EmployeeDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Employee> getAll() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<Employee> employees = new ArrayList<>();

        String query = "select * from employee";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employees.add(employee);
            }
            log.info("employees : {}", employees);
            return employees;
        } catch (SQLException e) {
            log.error("error while retrieving employees: ", e);
            throw new RuntimeException(e);
        }
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String phoneNumber = resultSet.getString("phone_number");
        float experience = resultSet.getFloat("experience");
        return new Employee(id, name, surname, phoneNumber, experience);
    }

    @Override
    public Employee getById(Long id) {
        Connection connection = getConnection();
        String query = "select * from employee where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                log.info("employee by id: {}", resultSet);
                return createEmployee(resultSet);
            }

        } catch (SQLException e) {
            log.warn("could not retrieve employee: ", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Employee employee) {
        Connection connection = getConnection();
        String query = "update employee set name = ? , surname = ?, phone_number = ?, experience = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPhone());
            statement.setFloat(4, employee.getExperience());
            statement.setLong(5, employee.getId());

            log.info("updated employee");
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            executeUpdate(connection, "delete from employee where id = ?", id);
            executeUpdate(connection, "delete from exam_results where teacher_id = ?", id);
            executeUpdate(connection, "delete from student_practice_relation where teacher_id = ?", id);
            executeUpdate(connection, "delete from student_theory_relation where teacher_id = ?", id);

            connection.commit();
            log.info("deleted employee");
        } catch (SQLException e) {
            log.warn("could not delete employee: ", e);
        }
    }

    private void executeUpdate(Connection connection, String query, Long id) throws SQLException {
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public void save(Employee employee) {
        Connection connection = getConnection();
        String query = "insert into employee  (name, surname, phone_number, experience, post_id) values(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPhone());
            statement.setFloat(4, employee.getExperience());
            statement.setLong(5, employee.getPost().getId());

            statement.executeUpdate();
            log.info("saved employee");
        } catch (SQLException ex) {
            log.warn("could not save employee: ", ex);
        }
    }

    @Override
    public List<Employee> getBySurname(String surname) {
        Connection connection = getConnection();
        String query = "select * from employee where surname = ?";
        List<Employee> employees = null;
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setString(1, surname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            log.warn("could not retrieve employee by surname: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getPost(Long id) {
        Connection connection = getConnection();
        String query = "select * from post p join employee e on p.id = e.post_id where e.id = ?";
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Long post_id = rs.getLong("id");
                    String specialization = rs.getString("specialization");
                    String name = rs.getString("name");
                    return Post.builder().id(post_id).specialization(specialization).name(name).build();
                } else {
                    log.info("Post with id=" + id + " not found");
                    return null;
                }
            } catch (SQLException ex) {
                log.warn("could not retrieve post: ", ex);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public void retireFromTheory(Long id) {
        Connection connection = getConnection();
        String query = "delete from student_theory_relation where teacher_id = ?";
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int rowsChanged = statement.executeUpdate();
                log.info("rows changed while delete relation: {}", rowsChanged);
            } catch (SQLException ex) {
                log.warn("could not retire from theory: ", ex);
            }
        }
    }

    @Override
    public void retireFromPractice(Long id) {
        Connection connection = getConnection();
        String query = "delete from student_practice_relation where teacher_id = ?";
        try {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int rowsChanged = statement.executeUpdate();
                log.info("rows changed while delete relation: {}", rowsChanged);
            }
        } catch (SQLException ex) {
            log.warn("could not retire from practice: ", ex);
        }
    }


    @Override
    public List<Object> getBySurnameAndLastName(EmployeeFilter filter) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var employees = criteria.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getFirstname() != null) {
            predicates.add(cb.equal(employees.get("name"), filter.getFirstname()));
        }
        if (filter.getLastname() != null) {
            predicates.add(cb.equal(employees.get("surname"), filter.getLastname()));
        }
        criteria.select(employees).where(cb.and(predicates.toArray(new Predicate[0])));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Object> getPracticeLessons(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var practices = criteria.from(Practice.class);
        var relations = practices.join("practiceRelations");
        criteria.select(practices).where(cb.equal(relations.get("id").get("teacherId"), id));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Object> getTheoryLessons(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var theories = criteria.from(Theory.class);
        var relations = theories.join("theoryRelations");
        criteria.select(theories).where(cb.equal(relations.get("id").get("teacherId"), id));
        return session.createQuery(criteria).list();
    }

    @Override
    public void rateTheory(StudentTheoryRelationId id, Long grade) {
        Session session = sessionFactory.openSession();
        var relation = StudentTheoryRelation.builder().id(id).grade(grade.shortValue()).date(LocalDate.now()).build();
        session.beginTransaction();
        session.persist(relation);
        session.getTransaction().commit();
    }

    @Override
    public int rateExam(ExamResultId id, Long grade) {
        Session session = sessionFactory.openSession();
        var exam = session.get(Exam.class, id.getExamId());
        exam.setGrade(grade);
        session.getTransaction().commit();
        return grade.intValue();
    }

    @Override
    public List<Object> getExams(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var exams = criteria.from(Exam.class);
        var relations = exams.join("examResultsList");
        criteria.select(exams).where(cb.equal(relations.get("id").get("teacherId"), id));
        return session.createQuery(criteria).list();
    }
}

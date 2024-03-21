package app.v1.repositories.impl;

import app.v1.entities.*;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.StudentDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class StudentDAOIml extends DbConnector implements StudentDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public StudentDAOIml(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Student> getAll() {
        Connection connection = getConnection();
        PreparedStatement statement;
        List<Student> students = new ArrayList<>();

        String query = "select * from student";
        try {
            statement = Objects.requireNonNull(connection).prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Student student = createStudent(resultSet);
                students.add(student);
            }
            log.info("students: {}", students);
            return students;
        } catch (SQLException e) {
            log.warn("couldn't retrieve students: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getById(Long id) {
        Connection connection = getConnection();
        String query = "select * from student where id = ?";
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createStudent(resultSet);
            }

        } catch (SQLException e) {
            log.warn("couldn't retrieve student: ", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Student> getBySurname(String surname) {
        Connection connection = getConnection();
        String query = "select * from student where surname = ?";
        List<Student> students = null;
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {

            statement.setString(1, surname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student stud = createStudent(resultSet);
                students.add(stud);
            }

            log.info("students: {}", students);
            return students;
        } catch (SQLException e) {
            log.warn("couldn't retrieve students by surname: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student student) {
        Connection connection = getConnection();
        String query = "update student set name = ? , surname = ?, phone_number = ?, category = ? where id = ?";
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getPhone());
            statement.setString(4, student.getCategory());
            statement.setLong(5, student.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            log.warn("couldn't update student: ", ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = getConnection()) {
            Objects.requireNonNull(connection).setAutoCommit(false);

            executeUpdate(connection, "delete from student where id = ?", id);
            executeUpdate(connection, "delete from exam_results where student_id = ?", id);
            executeUpdate(connection, "delete from student_practice_relation where student_id = ?", id);
            executeUpdate(connection, "delete from student_theory_relation where student_id = ?", id);

            connection.commit();
        } catch (SQLException e) {
            log.warn("couldn't delete student: ", e);
        }
    }

    private void executeUpdate(Connection connection, String query, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }


    @Override
    public void save(Student student) {
        Connection connection = getConnection();
        String query = "insert into student  (name, surname, phone_number, category) values(?, ?, ?, ?)";
        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {


            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getPhone());
            statement.setString(4, student.getCategory());

            statement.executeUpdate();
        } catch (SQLException ex) {
            log.warn("couldn't save student: ", ex);
        }
    }

    public List<Exam> getExams(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement;
        List<Exam> exams = new ArrayList<>();

        String query = "select from exam e join exam_results er on e.id = er.exam_id where er.student_id = ?";
        try {
            statement = Objects.requireNonNull(connection).prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long examId = resultSet.getLong("id");
                String examName = resultSet.getString("exam");
                Date examDate = resultSet.getDate("date");
                Long examGrade = resultSet.getLong("grade");

                exams.add(Exam.builder()
                        .id(examId)
                        .exam(examName)
                        .date(examDate.toLocalDate())
                        .grade(examGrade)
                        .build());
            }

            log.info("exams for student: {}", exams);

            return exams;
        } catch (SQLException e) {
            log.warn("couldn't retrieve exams: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    public List<Practice> getPractices(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement;
        List<Practice> practices = new ArrayList<>();
        String query = "select * from practice p join student_practice_relation sp on p.id = sp.practice_id where sp.student_id = ?";

        try {
            statement = Objects.requireNonNull(connection).prepareStatement(query);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long practiceId = rs.getLong("id");
                Date date = rs.getDate("date");
                float price = rs.getFloat("price");
                String place = rs.getString("place");
                Long car_id = rs.getLong("car_id");

                Car car = getCarForPractice(car_id);

                practices.add(Practice.builder()
                        .id(practiceId)
                        .date(date.toLocalDate())
                        .place(place)
                        .price(price)
                        .car(car)
                        .build());
            }
        } catch (SQLException ex) {
            log.warn("couldn't getPractices: ", ex);
        }
        log.info("practices : {}", practices);
        return practices;
    }

    public Car getCarForPractice(Long id) {
        Connection connection = getConnection();
        String query = "select * from car where id = ?";

        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            Long car_id = rs.getLong("id");
            String number = rs.getString("car_number");
            String model = rs.getString("model");
            Long year = rs.getLong("year");

            return Car.builder()
                    .id(car_id)
                    .model(model)
                    .number(number)
                    .year(year)
                    .build();

        } catch (SQLException ex) {
            log.warn("couldn't retrieve cars: ", ex);
        }
        return null;
    }

    private Post getPost(Long id) {
        Connection connection = getConnection();
        String query = "select * from post where id = ?";

        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            Long post_id = rs.getLong("id");
            String specialization = rs.getString("specialization");
            String name = rs.getString("name");

            return Post.builder()
                    .id(post_id)
                    .specialization(specialization)
                    .name(name)
                    .build();

        } catch (SQLException ex) {
            log.warn("couldn't retrieve post: ", ex);
        }
        return null;
    }

    public List<Employee> getStudentsTeachers(Long id) {
        Connection connection = getConnection();
        List<Employee> employees = new ArrayList<>();

        String query = "select * from employee e " +
                "join student_practice_relation sp on e.id=sp.teacher_id and sp.student_id = ? " +
                "join student_theory_relation st on e.id = st.teacher_id and st.student_id = ? " +
                "join exam_results er on e.id = st.teacher_id and er.student_id = ?";


        try (PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(query)) {

            statement.setLong(1, id);
            statement.setLong(2, id);
            statement.setLong(3, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long employee_id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phone_number");
                float experience = rs.getFloat("experience");
                Long post_id = rs.getLong("post_id");
                Employee employee = new Employee(employee_id, name, surname, phoneNumber, experience, getPost(post_id));
                employees.add(employee);
            }

            return employees;
        } catch (SQLException ex) {
            log.warn("couldn't retrieve teachers: ", ex);
        }
        return null;
    }

    private Student createStudent(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String phoneNumber = resultSet.getString("phone_number");
        String category = resultSet.getString("category");

        return new Student(id, name, surname, phoneNumber, category);
    }

    @Override
    public List<Integer> getTheoryGrades(int id) {
        Session session = sessionFactory.openSession();
        var list = session.createQuery("SELECT r from StudentTheoryRelation r WHERE r.student.id = :studentId", StudentTheoryRelation.class)
                .setParameter("studentId", id).list();

        List<Integer> grades = list.stream()
                .mapToInt(StudentTheoryRelation::getGrade)
                .boxed()
                .collect(Collectors.toList());

        log.info("\n\nlist:{}\n\n", list);
        return grades;

    }

    @Override
    public List<Object> getExamResults(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var grades = criteria.from(Exam.class);
        var results = grades.join("examResultsList");
        criteria.select(grades).where(cb.equal(results.get("id").get("studentId"), id));
        return session.createQuery(criteria).list();
    }

    @Override
    public int getVisitedPractices(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery();
        var practices = criteria.from(Practice.class);
        var relation = practices.join("practiceRelations");
        criteria.select(cb.count(practices)).where(cb.equal(relation.get("id").get("studentId"), id));
        Long count = (Long) session.createQuery(criteria).getSingleResult();
        session.close();
        return count.intValue();
    }
}
package app.v1.repositories.impl;

import app.v1.entities.Car;
import app.v1.entities.Practice;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.CarDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
public class CarDAOImpl implements CarDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CarDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Car> getAll() {
        Connection connection = DbConnector.getConnection();
        PreparedStatement statement;
        List<Car> cars = new ArrayList<>();

        String query = "select * from car";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String number = resultSet.getString("car_number");
                String model = resultSet.getString("model");
                Long year = resultSet.getLong("year");

                var car = Car.builder()
                        .id(id)
                        .model(model)
                        .number(number)
                        .year(year)
                        .build();

                cars.add(car);
            }
            log.info("Cars result set: {}", cars);
            return cars;
        } catch (SQLException e) {
            log.warn("couldn't retrieve cars: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from car where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String number = resultSet.getString("car_number");
                String model = resultSet.getString("model");
                Long year = resultSet.getLong("year");
                var car = Car.builder()
                        .id(id)
                        .model(model)
                        .number(number)
                        .year(year)
                        .build();
                return car;
            }

        } catch (SQLException e) {
            log.warn("couldn't retrieve car by id: ", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Car car) {
        Connection connection = DbConnector.getConnection();
        String query = "update car set car_number = ?, model = ?, year = ? where id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, car.getNumber());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getYear());
            statement.setLong(4, car.getId());


            statement.executeUpdate();
        } catch (SQLException ex) {
            log.warn("couldn't update car: ", ex);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DbConnector.getConnection();
        connection.setAutoCommit(false);
        String query = "delete from car where id = ?";
        String query_2 = "update practice set car_id = null where car_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(query_2);
        statement.executeUpdate();
        connection.commit();

    }

    @Override
    public void save(Car car) {
        Connection connection = DbConnector.getConnection();
        String query = "insert into car  (car_number, model, year) values(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, car.getNumber());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getYear());

            statement.executeUpdate();
        } catch (SQLException ex) {
            log.warn("couldn't save cars: ", ex);
        }
    }

    @Override
    public List<Practice> usedForPractices(Long id) {
        TypedQuery<Practice> query = entityManager.createQuery("select p from practice where car_id = :car_id", Practice.class);
        query.setParameter("car_id", id);
        return query.getResultList();
    }
}

package app.v1.repositories.impl;

import app.v1.entities.Car;
import app.v1.entities.Practice;
import app.v1.repositories.dao.CarDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.v1.repositories.DbConnector.getConnection;

public class CarDAOImpl implements CarDAO {
    @Override
    public List<Car> getAll() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
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

                cars.add(new Car(id, number, model, year));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getById(Long id) {
        Connection connection = getConnection();
        String query = "select * from car where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long car_id = resultSet.getLong("id");
                String number = resultSet.getString("car_number");
                String model = resultSet.getString("model");
                Long year = resultSet.getLong("year");
                return new Car(car_id, number, model, year);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Car car) {
        Connection connection = getConnection();
        String query = "update car set id = ?, car_number = ?, model = ?, year = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, car.getId());
            statement.setString(2, car.getNumber());
            statement.setString(3, car.getModel());
            statement.setLong(4, car.getYear());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = getConnection();
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
        Connection connection = getConnection();
        String query = "insert into car  (id, car_number, model, year) values(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, car.getId());
            statement.setString(2, car.getNumber());
            statement.setString(3, car.getModel());
            statement.setLong(4, car.getYear());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Practice> usedForPractices(Long id) {
        return null;
    }
}

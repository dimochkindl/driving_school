package app.v1.repositories.impl;

import app.v1.entities.Car;
import app.v1.entities.Practice;
import app.v1.repositories.DbConnector;
import app.v1.repositories.dao.PracticeDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
public class PracticeDAOImpl implements PracticeDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public PracticeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Practice> getAll() {
        Connection connection = DbConnector.getConnection();
        List<Practice> practices = new ArrayList<>();

        String query = "select practice.*, car.* " +
                "from practice " +
                "join car on practice.car_id = car.id";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Date date = rs.getDate("date");
                String place = rs.getString("place");
                float price = rs.getFloat("price");
                Long carId = rs.getLong("car_id");
                String number = rs.getString("car_number");
                String model = rs.getString("model");
                Long year = rs.getLong("year");

                var car = Car.builder()
                        .id(carId)
                        .number(number)
                        .model(model)
                        .year(year)
                        .build();

                var practice = Practice.builder()
                        .id(id)
                        .date(date.toLocalDate())
                        .place(place)
                        .price(price)
                        .build();
                practice.setCar(car);
                practices.add(practice);
            }

            return practices;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Practice getById(Long id) {
        Connection connection = DbConnector.getConnection();
        String query = "select * from practice where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Date date = rs.getDate("date");
                String place = rs.getString("place");
                float price = rs.getFloat("price");
                return new Practice(id, date.toLocalDate(), place, price);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Practice practice) {
        Connection connection = DbConnector.getConnection();
        String query = "update practice set  date = ?, price = ?, place = ?, car_id = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(5, practice.getId());
            statement.setDate(1, Date.valueOf(practice.getDate()));
            statement.setFloat(2, practice.getPrice());
            statement.setString(3, practice.getPlace());
            statement.setLong(4, practice.getCar().getId());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after updating the practice: " + saved);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DbConnector.getConnection();
        String query = "delete from practice where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            int rowsChanged = statement.executeUpdate();

            System.out.println("Rows affected after deleting practice by id: " + rowsChanged);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Practice practice) {
        Connection connection = DbConnector.getConnection();
        String query = "insert into practice  (date, price, place, car_id) values(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(practice.getDate()));
            statement.setFloat(2, practice.getPrice());
            statement.setString(3, practice.getPlace());
            statement.setLong(4, practice.getCar().getId());

            int saved = statement.executeUpdate();
            System.out.println("rows affected after saving the practice: " + saved);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Object> getPracticesByDate(LocalDate date) {
        log.info("Date: {}", date);
        @Cleanup var session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = cb.createQuery();
        var practices = criteria.from(Practice.class);
        criteria.select(practices).where(cb.equal(practices.get("date"), date));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Object> getPracticeByPlace(String place) {
        @Cleanup var session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = cb.createQuery();
        var practices = criteria.from(Practice.class);
        criteria.select(practices).where(cb.equal(practices.get("place"), place));
        return session.createQuery(criteria).list();
    }

    @Override
    public int getNumberOfPracticesByCarID(Long id) {
        return 0;
    }
}

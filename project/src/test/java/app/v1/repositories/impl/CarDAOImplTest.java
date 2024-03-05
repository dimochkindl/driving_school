package app.v1.repositories.impl;

import app.v1.entities.Car;
import app.v1.entities.Practice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarDAOImplTest {

    @Mock
    private EntityManager entityManager;

    private Logger log = LoggerFactory.getLogger(CarDAOImplTest.class);

    @InjectMocks
    private CarDAOImpl carDAO;

    @Test
    void usedForPractices_ShouldReturnListOfPractices() {
        // Arrange
        Long carId = 1L;
        Car car = new Car(carId, "ABC123", "Toyota", 2010L);
        LocalDate currentDate = LocalDate.now();
        Practice practice1 = new Practice(1L, currentDate.minusDays(1), "Victory square", 100.0f, car);
        Practice practice2 = new Practice(2L, currentDate.minusDays(2), "Zyuba", 150.0f, car);
        log.info("Add new Car {} with practices {}, {}", car, practice1, practice2);
        List<Practice> expectedPractices = Arrays.asList(practice1, practice2);

        TypedQuery<Practice> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Practice.class))).thenReturn(query);
        when(query.setParameter(eq("car_id"), eq(carId))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedPractices);

        // Act
        List<Practice> actualPractices = carDAO.usedForPractices(carId);

        // Assert
        assertEquals(expectedPractices, actualPractices);
        verify(entityManager).createQuery(anyString(), eq(Practice.class));
        verify(query).setParameter(eq("car_id"), eq(carId));
        verify(query).getResultList();

    }
}

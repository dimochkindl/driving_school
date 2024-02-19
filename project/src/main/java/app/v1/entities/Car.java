package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "car")
@Data
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_number", length = 10)
    private String number;

    @Column(name = "model", length = 40)
    private String model;

    @Column(name = "year", nullable = false)
    private Long year;


    public Car(Long id, String number, String model, Long year) {
        this.id = id;
        this.number = number;
        this.model = model;
        this.year = year;
    }
}

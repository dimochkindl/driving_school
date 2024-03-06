package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "car")
@Data
@ToString(exclude = "practiceList")
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

    @OneToMany(mappedBy = "car")
    private List<Practice> practiceList;

}

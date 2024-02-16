package app.v1.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "practice")
@Data
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "place", length = 50)
    private String place;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}

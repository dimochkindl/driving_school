package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "practice")
@Data
@AllArgsConstructor
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "place", length = 50)
    private String place;

    @Column(name = "price")
    private float price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    public Practice(Long id, LocalDate date, String place, float price) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.price = price;
    }
}

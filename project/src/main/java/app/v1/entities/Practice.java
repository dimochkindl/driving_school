package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "place", length = 50)
    private String place;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Practice(Long id, Date date, String place, float price) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.price = price;
    }
}

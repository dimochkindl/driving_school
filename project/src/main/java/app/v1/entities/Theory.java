package app.v1.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "theory")
@Data
public class Theory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theme", length = 50, nullable = false)
    private String theme;

    @Column(name = "price", nullable = false)
    private float price;

}

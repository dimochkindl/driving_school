package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "practice")
@Data
@ToString(exclude = "car")
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "practice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentPracticeRelation> practiceRelations = new ArrayList<>();

    public Practice(Long id, LocalDate date, String place, float price) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.price = price;
    }
}

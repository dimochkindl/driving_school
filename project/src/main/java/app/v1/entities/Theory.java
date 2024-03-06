package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theory")
@Data
@AllArgsConstructor
public class Theory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theme", length = 50, nullable = false)
    private String theme;

    @Column(name = "price", nullable = false)
    private float price;

    @OneToMany(mappedBy = "theory")
    private List<StudentTheoryRelation> theoryRelations = new ArrayList<>();

}

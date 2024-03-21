package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Theory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theme", length = 50, nullable = false)
    private String theme;

    @Column(name = "price", nullable = false)
    private float price;

    @Builder.Default
    @OneToMany(mappedBy = "theory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentTheoryRelation> theoryRelations = new ArrayList<>();

}

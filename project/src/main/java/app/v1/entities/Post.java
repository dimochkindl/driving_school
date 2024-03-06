package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "post")
@Data
@ToString(exclude = "employees")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialization", length = 30)
    private String specialization;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "post")
    private List<Employee> employees;

    public String getPostAsString() {
        return name;
    }

}

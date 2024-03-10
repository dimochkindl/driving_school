package app.v1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@ToString(exclude = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phone;

    @Column(name = "experience")
    private float experience;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<ExamResults> examResults = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<StudentTheoryRelation> theoryRelations = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<StudentPracticeRelation> practiceRelations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Employee(Long id, String name, String surname, String phone, float experience, Post post) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.experience = experience;
        this.post = post;
    }

    public Employee(Long id, String name, String surname, String phone, float experience) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.experience = experience;
    }

}

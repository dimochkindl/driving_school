package app.v1.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
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

    @ManyToMany
    @JoinTable(
            name = "exam_results",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Exam> exams;

    @ManyToMany
    @JoinTable(
            name = "student_practice_relation",
            joinColumns = @JoinColumn(name = "practice_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Exam> practices;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}

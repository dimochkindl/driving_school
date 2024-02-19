package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname",length = 30,nullable = false)
    private String surname;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phone;

    @Column(name = "category", length = 3)
    private String category;

    @ManyToMany
    @JoinTable(
            name = "exam_results",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Exam> exams;

    @ManyToMany
    @JoinTable(
            name = "student_practice_relation",
            joinColumns = @JoinColumn(name = "practice_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Practice> practices;

    @ManyToMany
    @JoinTable(
            name = "exam_results",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Employee> teachers;


}

package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Builder.Default
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phone = "+375-33-812-495-0";

    @Column(name = "category", length = 3)
    private String category;

    @Builder.Default
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ExamResults> studentResults = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<StudentTheoryRelation> theoryRelations;

    @OneToMany(mappedBy = "student")
    private List<StudentPracticeRelation> practiceRelations;

    public Student(Long id, String name, String surname, String phone, String category) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.category = category;
    }
}

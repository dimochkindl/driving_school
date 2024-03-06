package app.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam")
@Data
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exam", length = 40)
    private String exam;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "grade")
    private Long grade;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamResults> examResultsList = new ArrayList<>();
}

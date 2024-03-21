package app.v1.entities;

import app.v1.entities.id.StudentTheoryRelationId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "student_theory_relation")
@Data
@Builder
@ToString(exclude = {"student", "teacher", "theory"})
@AllArgsConstructor
@NoArgsConstructor
public class StudentTheoryRelation {
    @EmbeddedId
    private StudentTheoryRelationId id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "grade")
    private short grade;

    @MapsId("student")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("teacher")
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Employee teacher;

    @MapsId("theory")
    @ManyToOne
    @JoinColumn(name = "theory_id")
    private Theory theory;

    public void setStudent(Student student) {
        this.student = student;
        student.getTheoryRelations().add(this);
    }

    public void setEmployee(Employee employee) {
        teacher = employee;
        employee.getTheoryRelations().add(this);
    }

    public void setTheory(Theory theory) {
        this.theory = theory;
        theory.getTheoryRelations().add(this);
    }

    public StudentTheoryRelationId getId() {
        return id;
    }
}

package app.v1.entities;

import app.v1.entities.id.StudentTheoryRelationId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "student_theory_relation")
@Data
public class StudentTheoryRelation {
    @EmbeddedId
    private StudentTheoryRelationId id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "grade")
    private short grade;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("teacherId")
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Employee teacher;

    @MapsId("theoryId")
    @ManyToOne
    @JoinColumn(name = "theory_id")
    private Theory theory;

    public void setStudent(Student student){
        this.student = student;
        student.getTheoryRelations().add(this);
    }

    public void setEmployee(Employee employee){
        teacher = employee;
        employee.getTheoryRelations().add(this);
    }

    public void setTheory(Theory theory){
        this.theory = theory;
        theory.getTheoryRelations().add(this);
    }

}

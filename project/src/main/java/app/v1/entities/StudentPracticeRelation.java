package app.v1.entities;

import app.v1.entities.id.StudentPracticeRelationId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "student_practice_relation")
@Data
@Builder
public class StudentPracticeRelation {
    @EmbeddedId
    private StudentPracticeRelationId id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("teacherId")
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Employee teacher;

    @MapsId("practiceId")
    @ManyToOne
    @JoinColumn(name = "practice_id")
    private Practice practice;

    public void setStudent(Student student){
        this.student = student;
        student.getPracticeRelations().add(this);
    }

    public void setEmployee(Employee employee){
        teacher = employee;
        employee.getPracticeRelations().add(this);
    }

    public void setPractice(Practice practice){
        this.practice = practice;
        practice.getPracticeRelations().add(this);
    }
}

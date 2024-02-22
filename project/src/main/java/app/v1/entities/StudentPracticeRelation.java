package app.v1.entities;

import app.v1.entities.id.StudentPracticeRelationId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_practice_relation")
@Data
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
}

package app.v1.entities;

import app.v1.entities.id.StudentTheoryRelationId;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "student_theory_relation")
@Data
public class StudentTheoryRelation {
    @EmbeddedId
    private StudentTheoryRelationId id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "grade")
    private short grade;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Long studentId;

    @MapsId("teacherId")
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Long teacherId;

    @MapsId("theoryId")
    @ManyToOne
    @JoinColumn(name = "theory_id")
    private Long theoryId;

}

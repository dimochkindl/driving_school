package app.v1.entities;

import app.v1.entities.id.ExamResultId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamResults {

    @EmbeddedId
    private ExamResultId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Long student;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Long teacher;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Long exam;

}

package app.v1.entities;

import app.v1.entities.id.ExamResultId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Student student;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Employee teacher;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public void setStudent(Student student) {
        this.student = student;
        student.getStudentResults().add(this);
    }

    public void setEmployee(Employee employee) {
        teacher = employee;
        employee.getExamResults().add(this);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        exam.getExamResultsList().add(this);
    }
}

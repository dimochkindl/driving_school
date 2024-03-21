package app.v1.entities.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResultId implements Serializable {
    @Serial
    private static final long serialVersionUID = 7980064578169571586L;
    private Long studentId;
    private Long teacherId;
    private Long examId;
}

package app.v1.entities.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPracticeRelationId implements Serializable {
    private Long studentId;
    private Long teacherId;
    private Long practiceId;
}
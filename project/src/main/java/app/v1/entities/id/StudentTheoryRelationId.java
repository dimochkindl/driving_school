package app.v1.entities.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentTheoryRelationId implements Serializable {
    @Serial
    private static final long serialVersionUID = 782733403616952935L;
    private Long studentId;
    private Long teacherId;
    private Long theoryId;
}

package app.v1.dto.filters;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class EmployeeFilter {
    String firstname;
    String lastname;
}

package app.v1.dto.filters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeFilter {
    String firstname;
    String lastname;
}

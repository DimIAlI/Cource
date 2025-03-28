package org.example.model.dto.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.model.dto.CustomerDto;
import org.example.model.dto.WorkspaceDto;
import org.example.model.dto.filters.BaseFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ReservationFilter extends BaseFilter<Long> {
    private CustomerDto customer;
    private WorkspaceDto space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();

        if (getId() != null) {
            conditions.add("res.id = ?");
            parameters.add(getId());
        }
        if (customer != null) {
            conditions.add("customer_id = ?");
            parameters.add(customer.getId());
        }
        if (space != null) {
            conditions.add("space_id = ?");
            parameters.add(space.getId());
        }
        if (startTime != null) {
            conditions.add("start_time = ?");
            parameters.add(startTime);
        }
        if (endTime != null) {
            conditions.add("end_time = ?");
            parameters.add(endTime);
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

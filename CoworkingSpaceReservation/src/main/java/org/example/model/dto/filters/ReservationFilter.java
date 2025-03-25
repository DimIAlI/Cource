package org.example.model.dto.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.WorkspaceEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ReservationFilter implements Filter {
    private Long id;
    private CustomerEntity customer;
    private WorkspaceEntity space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();

        if (id != null) {
            conditions.add("id = ?");
            parameters.add(id);
        }
        if (customer != null) {
            conditions.add("customer_id = ?");
            parameters.add(customer.getId());
        }
        if (startTime != null) {
            conditions.add("start_time = ?");
            parameters.add(startTime);
        }
        if (startTime != null) {
            conditions.add("end_time = ?");
            parameters.add(startTime);
        }
        if (space != null) {
            conditions.add("space_id = ?");
            parameters.add(space.getId());
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

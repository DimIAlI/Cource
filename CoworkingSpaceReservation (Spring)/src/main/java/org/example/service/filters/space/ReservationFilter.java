package org.example.service.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.service.filters.BaseFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ReservationFilter extends BaseFilter {
    private Long customerId;
    private Long spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();
        int paramIndex = 1;

        if (getId() != null) {
            conditions.add("id = ?" + paramIndex);
            parameters.add(getId());

            paramIndex++;
        }
        if (customerId != null) {
            conditions.add("customer.id = ?" + paramIndex);
            parameters.add(customerId);

            paramIndex++;
        }
        if (spaceId != null) {
            conditions.add("space.id = ?" + paramIndex);
            parameters.add(spaceId);

            paramIndex++;
        }
        if (startTime != null) {
            conditions.add("e.startTime = ?" + paramIndex);
            parameters.add(startTime);

            paramIndex++;
        }
        if (endTime != null) {
            conditions.add("e.endTime = ?" + paramIndex);
            parameters.add(endTime);
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

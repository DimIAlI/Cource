package org.example.model.dto.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.filters.BaseFilter;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.entity.account.CustomerEntity;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ReservationFilter extends BaseFilter {
    private CustomerDto customer;
    private WorkspaceDto space;
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
        if (customer != null) {
            conditions.add("customer_id = ?" + paramIndex);
            parameters.add(buildCustomerEntity());

            paramIndex++;
        }
        if (space != null) {
            conditions.add("workspace_id = ?" + paramIndex);
            parameters.add(buildWorkspaceEntity());

            paramIndex++;
        }
        if (startTime != null) {
            conditions.add("start_time = ?" + paramIndex);
            parameters.add(startTime);

            paramIndex++;
        }
        if (endTime != null) {
            conditions.add("end_time = ?" + paramIndex);
            parameters.add(endTime);

            paramIndex++;
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }

    private WorkspaceEntity buildWorkspaceEntity() {
        return WorkspaceEntity.builder()
                .id(space.getId())
                .type(buildSpaceTypeEntity())
                .price(space.getPrice())
                .available(space.getAvailable())
                .build();
    }

    private SpaceTypeEntity buildSpaceTypeEntity() {
        return SpaceTypeEntity.builder()
                .id(space.getType().getId())
                .name(space.getType().getName())
                .displayName(space.getType().getDisplayName())
                .build();
    }

    private CustomerEntity buildCustomerEntity() {
        return CustomerEntity.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .build();
    }
}

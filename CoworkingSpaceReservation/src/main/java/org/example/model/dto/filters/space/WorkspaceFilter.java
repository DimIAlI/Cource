package org.example.model.dto.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.model.dto.filters.BaseFilter;
import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.entity.space.SpaceTypeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class WorkspaceFilter extends BaseFilter {
    private SpaceTypeDto type;
    private Double price;
    private Boolean available;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();
        int paramIndex = 1;

        if (getId() != null) {
            conditions.add("id = ?" + paramIndex);
            parameters.add(getId());

            paramIndex++;
        }
        if (type != null) {
            conditions.add("type_id = ?" + paramIndex);
            parameters.add(buildSpaceTypeEntity());

            paramIndex++;
        }
        if (price != null) {
            conditions.add("price = ?" + paramIndex);
            parameters.add(price);

            paramIndex++;
        }
        if (available != null) {
            conditions.add("available = ?" + paramIndex);
            parameters.add(available);

            paramIndex++;
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }

    private SpaceTypeEntity buildSpaceTypeEntity() {
        return SpaceTypeEntity.builder()
                .id(type.getId())
                .name(type.getName())
                .displayName(type.getDisplayName())
                .build();
    }
}

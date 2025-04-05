package org.example.service.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.dto.service.space.SpaceTypeDto;
import org.example.service.filters.BaseFilter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class WorkspaceFilter extends BaseFilter {
    private SpaceTypeDto type;
    private Double price;

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
            conditions.add("type.id = ?" + paramIndex);
            parameters.add(type.getId());

            paramIndex++;
        }
        if (price != null) {
            conditions.add("price = ?" + paramIndex);
            parameters.add(price);
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

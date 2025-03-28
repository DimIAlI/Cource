package org.example.model.dto.filters.space;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.model.dto.filters.BaseFilter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@SuperBuilder
public class WorkspaceFilter extends BaseFilter<Long> {
    private Long typeId;
    private Double price;
    private Boolean available;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();

        if (getId() != null) {
            conditions.add("id = ?");
            parameters.add(getId());
        }
        if (price != null) {
            conditions.add("price = ?");
            parameters.add(price);
        }
        if (available != null) {
            conditions.add("available = ?");
            parameters.add(available);
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

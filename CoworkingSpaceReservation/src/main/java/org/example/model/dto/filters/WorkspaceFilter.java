package org.example.model.dto.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class WorkspaceFilter implements Filter{
    private Long id;
    private Long typeId;
    private double price;
    private boolean available;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();

        /*
        If new queries with additional WHERE parameters are required
        need to add a new if block with the same logic
        */

        if (id != null) {
            conditions.add("id = ?");
            parameters.add(id);
        }

        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

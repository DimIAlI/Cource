package org.example.model.dto.filters.account;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.model.dto.filters.BaseFilter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public abstract class UserFilter extends BaseFilter {
    private String login;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();
        int paramIndex = 1;

        if (getId() != null) {
            conditions.add("id = ?" + paramIndex);
            parameters.add(getId());

            paramIndex++;
        }
        if (login != null) {
            conditions.add("login = ?"+ paramIndex);
            parameters.add(login);

            paramIndex++;
        }
        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

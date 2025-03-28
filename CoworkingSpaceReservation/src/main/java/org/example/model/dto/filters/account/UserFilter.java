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
public abstract class UserFilter extends BaseFilter<Long> {
    private String login;

    @Override
    public String buildWhereCondition(List<Object> parameters) {

        List<String> conditions = new ArrayList<>();

        if (getId() != null) {
            conditions.add("id = ?");
            parameters.add(getId());
        }
        if (login != null) {
            conditions.add("login = ?");
            parameters.add(login);
        }
        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
}

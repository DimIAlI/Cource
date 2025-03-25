package org.example.model.dto.filters;

import java.util.List;

public interface Filter {

    String buildWhereCondition( List<Object> parameters);
}

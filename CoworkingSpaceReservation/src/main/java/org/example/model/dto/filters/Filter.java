package org.example.model.dto.filters;

import org.example.model.entity.Identifiable;

import java.io.Serializable;
import java.util.List;

public interface Filter<T extends Serializable> extends Identifiable<T> {

    String buildWhereCondition(List<Object> parameters);
}

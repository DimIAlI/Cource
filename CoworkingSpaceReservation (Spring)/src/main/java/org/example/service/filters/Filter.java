package org.example.service.filters;

import org.example.entity.Identifiable;

import java.io.Serializable;
import java.util.List;

public interface Filter<T extends Serializable> extends Identifiable<T> {

    String buildWhereCondition(List<Object> parameters);
}

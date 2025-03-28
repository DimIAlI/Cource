package org.example.model.dto.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@Setter
public abstract class BaseFilter<T extends Serializable> implements Filter<T> {
    private T id;
}

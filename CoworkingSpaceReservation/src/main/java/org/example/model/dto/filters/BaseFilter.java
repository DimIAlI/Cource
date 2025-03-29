package org.example.model.dto.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class BaseFilter implements Filter<Long> {
    private Long id;
}

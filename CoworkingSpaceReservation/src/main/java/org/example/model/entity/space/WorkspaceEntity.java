package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@SuperBuilder
public class WorkspaceEntity extends BaseEntity<Long> {
    @EqualsAndHashCode.Include
    private Long typeId;
    @EqualsAndHashCode.Include
    private Double price;
    private Boolean available;

}


package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;


@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@SuperBuilder
public class SpaceTypeEntity extends BaseEntity<Long> {
    private String name;
    private String displayName;
}

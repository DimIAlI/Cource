package org.example.model.entity.accunt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AdminEntity extends BaseEntity<Long> {
}

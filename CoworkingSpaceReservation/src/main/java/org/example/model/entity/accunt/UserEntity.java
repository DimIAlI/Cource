package org.example.model.entity.accunt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
public abstract class UserEntity<T extends Serializable> extends BaseEntity<T> {
    private String login;
}

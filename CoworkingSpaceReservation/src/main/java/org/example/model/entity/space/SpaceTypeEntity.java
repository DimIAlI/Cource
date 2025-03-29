package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "space_types")
public class SpaceTypeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, name = "display_name")
    private String displayName;
}

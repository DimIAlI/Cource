package org.example.entity.space;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.entity.BaseEntity;

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

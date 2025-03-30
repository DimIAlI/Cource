package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "workspaces")
public class WorkspaceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "type_id")
    private SpaceTypeEntity type;
    @EqualsAndHashCode.Include
    @Column(nullable = false, columnDefinition = "NUMERIC(15, 2)")
    private Double price;
    @Column(nullable = false)
    private Boolean available;
}
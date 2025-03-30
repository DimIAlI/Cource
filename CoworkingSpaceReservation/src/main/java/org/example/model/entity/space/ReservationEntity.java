package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;
import org.example.model.entity.account.CustomerEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class ReservationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkspaceEntity space;
    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;
    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;
}

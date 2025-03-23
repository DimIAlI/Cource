package org.example.model.storage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.entity.AdminEntity;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.WorkspaceEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ApplicationState {

    private final Map<String, CustomerEntity> registeredCustomers = new HashMap<>();
    private final Map<String, AdminEntity> registeredAdmins = new HashMap<>();
    private final Map<Long, ReservationEntity> reservationsById = new HashMap<>();

    private final Map<String, List<ReservationEntity>> reservationsByCustomer = new HashMap<>();
    private final Map<Long, WorkspaceEntity> workspaces = new HashMap<>();
    @Setter
    private Long lastWorkspaceId = 0L;
    @Setter
    private Long lastReservationId = 0L;

    @JsonDeserialize(keyAs = Long.class)
    public Map<Long, ReservationEntity> getReservationsById() {
        return reservationsById;
    }

    @JsonDeserialize(keyAs = Long.class)
    public Map<Long, WorkspaceEntity> getWorkspaces() {
        return workspaces;
    }
}

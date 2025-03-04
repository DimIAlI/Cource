package org.example.model.storage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.Reservation;
import org.example.model.Workspace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ApplicationState {

    private final Map<String, Customer> registeredCustomers = new HashMap<>();
    private final Map<String, Admin> registeredAdmins = new HashMap<>();
    private final Map<Long, Reservation> reservationsById = new HashMap<>();

    @JsonSerialize(keyUsing = CustomerKeySerializer.class)
    @JsonDeserialize(keyUsing = CustomerKeyDeserializer.class)
    private final Map<Customer, List<Reservation>> reservationsByCustomer = new HashMap<>();
    private final Map<Long, Workspace> workspaces = new HashMap<>();
    @JsonDeserialize(keyAs = Long.class)
    public Map<Long, Reservation> getReservationsById() {
        return reservationsById;
    }
    @JsonDeserialize(keyAs = Long.class)
    public Map<Long, Workspace> getWorkspaces() {
        return workspaces;
    }
}

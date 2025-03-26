package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.model.dto.CustomerDto;

public class ViewReservationsCommand extends CustomerCommand {
    public ViewReservationsCommand(CustomerDto customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        generalController.showViewReservationItem();
        generalController.showViewCustomerReservations(getCustomer());
        return false;
    }
}

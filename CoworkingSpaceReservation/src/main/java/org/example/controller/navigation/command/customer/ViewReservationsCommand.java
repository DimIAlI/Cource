package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.model.entity.CustomerEntity;

public class ViewReservationsCommand extends CustomerCommand {
    public ViewReservationsCommand(CustomerEntity customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        generalController.showViewReservationItem();
        generalController.showViewCustomerReservations(getCustomer());
        return false;
    }
}

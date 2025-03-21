package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.model.Customer;

public class ViewReservationsCommand extends CustomerCommand {
    public ViewReservationsCommand(Customer customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        generalController.showViewReservationItem();
        generalController.showViewCustomerReservations(getCustomer());
        return false;
    }
}

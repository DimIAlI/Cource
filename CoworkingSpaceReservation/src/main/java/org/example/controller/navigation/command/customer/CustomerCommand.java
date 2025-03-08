package org.example.controller.navigation.command.customer;

import lombok.Getter;
import org.example.controller.navigation.command.MenuCommand;
import org.example.model.Customer;
@Getter
public abstract class CustomerCommand implements MenuCommand {
    private final Customer customer;

    public CustomerCommand(Customer customer) {
        this.customer = customer;
    }

}

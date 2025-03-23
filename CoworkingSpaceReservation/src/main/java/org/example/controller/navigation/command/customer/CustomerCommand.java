package org.example.controller.navigation.command.customer;

import lombok.Getter;
import org.example.controller.navigation.command.MenuCommand;
import org.example.model.entity.CustomerEntity;
@Getter
public abstract class CustomerCommand implements MenuCommand {
    private final CustomerEntity customer;

    public CustomerCommand(CustomerEntity customer) {
        this.customer = customer;
    }
}

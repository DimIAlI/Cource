package org.example.controller.navigation.command.customer;

import lombok.Getter;
import org.example.controller.navigation.command.MenuCommand;
import org.example.model.dto.account.CustomerDto;
@Getter
public abstract class CustomerCommand implements MenuCommand {
    private final CustomerDto customer;

    public CustomerCommand(CustomerDto customer) {
        this.customer = customer;
    }
}

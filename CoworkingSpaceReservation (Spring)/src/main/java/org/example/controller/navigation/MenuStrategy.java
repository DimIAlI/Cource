package org.example.controller.navigation;


import org.example.controller.GeneralController;

public interface MenuStrategy {
    boolean navigate(String choice, GeneralController generalController);
}

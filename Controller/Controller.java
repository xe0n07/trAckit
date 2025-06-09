package Controller;

import View.Login;

public class Controller {

    public Controller() {
        // You can initialize models or services here if needed
    }

    // Starts the application with the login screen
    public void startApplication() {
        new Login().setVisible(true);
    }

    // You can add more methods here to handle navigation, events, etc.
}


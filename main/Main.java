package main;

import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        // Set a nice look and feel (optional)
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore and use default
        }

        // Start the application via the controller
        javax.swing.SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            controller.startApplication();
        });
    }
}
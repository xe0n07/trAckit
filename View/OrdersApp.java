package View;

import javax.swing.*;

public class OrdersApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Orders");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600); // Adjust size as needed
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new orders());
            frame.setVisible(true);
        });
    }
} 
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class dashboard extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private HashMap<String, JButton> menuButtons = new HashMap<>();
    private Color menuColor = Color.decode("#44B394");
    private Color selectedColor = new Color(60, 180, 140);
    private Color hoverColor = new Color(80, 200, 170);

    public dashboard() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Left menu
        JPanel navPanel = new JPanel();
        navPanel.setBackground(menuColor);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(180, 0));

        String[] menuItems = {"Dashboard", "In stock", "Products", "Sales", "Orders"};
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add your real panels here
        cardPanel.add(new JPanel(), "Dashboard"); // Empty for now
        cardPanel.add(new JPanel(), "In stock");  // Empty for now
        cardPanel.add(new addProductsFromOrders(), "Products"); // Use addProductsFromOrders for Products tab
        cardPanel.add(new JPanel(), "Sales");     // Empty for now
        cardPanel.add(new orders(), "Orders");    // Use your real orders panel

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 40));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(menuColor);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!btn.getBackground().equals(selectedColor))
                        btn.setBackground(hoverColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!btn.getBackground().equals(selectedColor))
                        btn.setBackground(menuColor);
                }
            });

            // Selection effect and panel switching
            btn.addActionListener(e -> {
                for (JButton b : menuButtons.values()) {
                    b.setBackground(menuColor);
                }
                btn.setBackground(selectedColor);
                cardLayout.show(cardPanel, item); // This must match the cardPanel.add(..., "Orders") etc.
            });

            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btn);
            menuButtons.put(item, btn);
        }

        // Set default selected menu
        menuButtons.get("Dashboard").setBackground(selectedColor);

        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // Dummy panel for demonstration; replace with your actual products panel if available
    static class productsPanel extends JPanel {
        public productsPanel() {
            setBackground(Color.WHITE);
            add(new JLabel("Products Panel"));
        }
    }
}
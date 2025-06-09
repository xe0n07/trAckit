package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class products extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, manufacturerField, costField;
    private static ArrayList<String> productList = new ArrayList<>();

    public products() {
        setTitle("Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());

        // Left menu panel (same as orders)
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(0xD1ABA0));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(180, 0));

        String[] menuItems = {"Dashboard", "In stock", "Products", "Sales", "Orders"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 40));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(new Color(0xD1ABA0));
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btn);

            if (item.equals("Orders")) {
                btn.addActionListener(e -> {
                    new orders().setVisible(true);
                    dispose();
                });
            }
        }

        // Table for products
        String[] columnNames = {"Product Name", "Manufacturer", "Cost", "Supplier Name"};
        Object[][] data = {
            {"Charger", "Xiomi Tech.", "450", "ABC Supplier"}
        };
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return true;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0xD1ABA0));
        table.getTableHeader().setForeground(Color.BLACK);

        JScrollPane tableScroll = new JScrollPane(table);

        // Form to add new product
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.BLACK);
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        nameField = new JTextField(12);
        manufacturerField = new JTextField(12);
        costField = new JTextField(8);
        JTextField supplierField = new JTextField(12);

        // JLabel nameLabel = new JLabel("Product Name:");
        // nameLabel.setForeground(Color.WHITE);
        // JLabel manufacturerLabel = new JLabel("Manufacturer:");
        // manufacturerLabel.setForeground(Color.WHITE);
        // JLabel costLabel = new JLabel("Cost:");
        // costLabel.setForeground(Color.WHITE);

        JButton addButton = new JButton("Add Product");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 8));
        addButton.setBackground(new Color(0xD1ABA0));
        // addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addProduct());

        // formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(manufacturerField);
        formPanel.add(costField);
        formPanel.add(supplierField);
        formPanel.add(addButton);

        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();
        String cost = costField.getText().trim();
        String supplier = supplierField.getText().trim();

        if (name.isEmpty() || manufacturer.isEmpty() || cost.isEmpty() || supplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Double.parseDouble(cost);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cost must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Prevent duplicate product names
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (name.equalsIgnoreCase((String) tableModel.getValueAt(i, 0))) {
                JOptionPane.showMessageDialog(this, "Product already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        tableModel.addRow(new Object[]{name, manufacturer, cost, supplier});
        productList.add(name);
        orders.updateProductList(productList);
        nameField.setText("");
        manufacturerField.setText("");
        costField.setText("");
        supplierField.setText("");
        JOptionPane.showMessageDialog(this, "Product added and available in Orders.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new products().setVisible(true));
    }
}
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class addProductsFromOrders extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public addProductsFromOrders() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table with 4 columns
        String[] columnNames = {"Product Name", "Manufacturer", "Cost", "Supplier Name"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return true;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(Color.decode("#44B394"));
        table.getTableHeader().setForeground(Color.BLACK);

        JScrollPane tableScroll = new JScrollPane(table);

        // Bottom panel with Add Row and Save buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Row");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(Color.decode("#44B394"));
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> tableModel.addRow(new Object[]{"", "", "", ""}));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#44B394"));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveToDatabase());

        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);

        add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void saveToDatabase() {
        // TODO: Implement your database save logic here
        JOptionPane.showMessageDialog(this, "Products saved to database.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
};

// If you want a separate tab for this
// cardPanel.add(new addProductsFromOrders(), "Products");
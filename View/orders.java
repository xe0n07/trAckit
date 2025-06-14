package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class orders extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> productComboBox;
    private JComboBox<String> statusComboBox;

    // Example product list; replace with dynamic fetching if needed
    private String[] productList = {"Charger", "Adapter", "Cable"};

    public orders() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        String[] columnNames = {"Order ID", "Product", "Category", "Sales Channel", "Instruction", "Items", "Status", "Store Name", "Stock Adjustment", "Qty"};
        Object[][] data = {
            {1, "Charger", "cat 3", "Store name", "stock adjustment", 5, "Completed", "Store name", "stock adjustment", 5}
        };
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // Product (1) and Status (6) columns are editable as dropdowns
                return true;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(Color.decode("#44B394"));
        table.getTableHeader().setForeground(Color.BLACK);

        // Product drop-down
        productComboBox = new JComboBox<>(productList);
        TableColumn productColumn = table.getColumnModel().getColumn(1);
        productColumn.setCellEditor(new DefaultCellEditor(productComboBox));

        // Status drop-down
        statusComboBox = new JComboBox<>(new String[]{"Completed", "Pending", "Cancelled"});
        TableColumn statusColumn = table.getColumnModel().getColumn(6);
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));

        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(Color.decode("#44B394"));
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addNewOrderRow());

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#44B394"));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveOrdersToDatabase());

        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addNewOrderRow() {
        int nextOrderId = 1;
        int rowCount = tableModel.getRowCount();
        if (rowCount > 0) {
            Object lastIdObj = tableModel.getValueAt(rowCount - 1, 0);
            try {
                nextOrderId = Integer.parseInt(lastIdObj.toString()) + 1;
            } catch (Exception ignored) {}
        }
        tableModel.addRow(new Object[]{nextOrderId, productList[0], "", "", "", "", "Pending", "", "", ""});
    }

    private void saveOrdersToDatabase() {
        JOptionPane.showMessageDialog(this, "Orders saved to database.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}   
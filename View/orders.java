package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class orders extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private static ArrayList<String> productList = new ArrayList<>();

    public orders() {
        setTitle("Orders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Sample products (will be updated by products.java)
        if (productList.isEmpty()) {
            productList.add("Charger");
            productList.add("Battery");
            productList.add("Generator");
            productList.add("Inventer");
            productList.add("Power");
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());

        // Left menu panel
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
        }

        // Table
        String[] columnNames = {"Order ID", "Product", "Category", "Sales Channel", "Instruction", "Items", "Status", "Store Name", "Stock Adjustment", "Qty"};
        Object[][] data = {
            {1, "Charger", "cat 3", "Store name", "stock adjustment", 5, "Completed", "Store name", "stock adjustment", 5},
            {2, "Battery", "cat 3", "Store name", "stock adjustment", 5, "Pending", "Store name", "stock adjustment", 5},
            {3, "Generator", "cat 3", "Store name", "stock adjustment", 5, "Completed", "Store name", "stock adjustment", 5},
            {4, "Inventer", "cat 3", "Store name", "stock adjustment", 5, "Pending", "Store name", "stock adjustment", 5},
            {5, "Power", "cat 3", "Store name", "stock adjustment", 5, "Completed", "Store name", "stock adjustment", 5}
        };
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // Order ID is not editable
                return col != 0;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 5 || columnIndex == 9) return Integer.class;
                return String.class;
            }
        };
        table = new JTable(tableModel);

        // Set Product column as JComboBox
        TableColumn productCol = table.getColumnModel().getColumn(1);
        JComboBox<String> productCombo = new JComboBox<>(productList.toArray(new String[0]));
        productCol.setCellEditor(new DefaultCellEditor(productCombo));

        // Set Status column as JComboBox
        TableColumn statusCol = table.getColumnModel().getColumn(6);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Completed", "Pending", "Cancelled"});
        statusCol.setCellEditor(new DefaultCellEditor(statusCombo));

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0xD1ABA0));
        table.getTableHeader().setForeground(Color.BLACK);

        JScrollPane tableScroll = new JScrollPane(table);

        // New Order button under the table
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        JButton newOrderButton = new JButton("New Order");
        newOrderButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        newOrderButton.setBackground(new Color(0xD1ABA0));
        newOrderButton.setForeground(Color.BLACK);
        newOrderButton.setFocusPainted(false);
        newOrderButton.addActionListener(e -> addNewOrderRow());
        bottomPanel.add(newOrderButton);

        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addNewOrderRow() {
        int nextOrderId = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{nextOrderId, productList.isEmpty() ? "" : productList.get(0), "", "", "", 0, "Pending", "", "", 0});
    }

    // Called by products.java to update product list in orders
    public static void updateProductList(ArrayList<String> products) {
        productList.clear();
        productList.addAll(products);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new orders().setVisible(true));
    }
}
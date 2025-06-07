package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class products extends JFrame {

    private DefaultListModel<String> listModel;
    private JList<String> productJList;
    private JTextField productField;
    private static ArrayList<String> productList = new ArrayList<>();

    public products() {
        setTitle("Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add New Product");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        productJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(productJList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        productField = new JTextField(20);
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct());
        inputPanel.add(productField);
        inputPanel.add(addButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Load initial products if any
        for (String prod : productList) {
            listModel.addElement(prod);
        }
    }

    private void addProduct() {
        String product = productField.getText().trim();
        if (!product.isEmpty() && !listModel.contains(product)) {
            listModel.addElement(product);
            productList.add(product);
            orders.updateProductList(productList);
            productField.setText("");
            JOptionPane.showMessageDialog(this, "Product added and available in Orders.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Enter a unique product name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new products().setVisible(true));
    }
}
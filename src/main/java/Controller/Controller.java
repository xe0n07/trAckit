package Controller;

import DAO.UserDAO;
import DAO.ProductDAO;
import DAO.OrderDAO;
import Model.Product;
import Model.Order;

import java.util.List;

import javax.swing.JOptionPane;

public class Controller {

    // Use a static UserDAO instance for all backend operations
    private static final UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    // Login logic
    public static boolean loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean result = userDAO.loginUser(username.trim(), password);
        if (!result) {
            JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    // Registration logic
    public static boolean registerUser(String username, String company, String password, String confirmPassword, boolean agreedTerms) {
        if (username == null || username.trim().isEmpty() ||
            company == null || company.trim().isEmpty() ||
            password == null || password.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!agreedTerms) {
            JOptionPane.showMessageDialog(null, "You must agree to the terms and conditions.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean success = userDAO.registerUser(username.trim(), company.trim(), password);
        if (success) {
            JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Password reset logic
    public static boolean resetPassword(String username, String newPassword) {
        if (username == null || username.trim().isEmpty() ||
            newPassword == null || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and new password are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean result = userDAO.resetPassword(username.trim(), newPassword);
        if (result) {
            JOptionPane.showMessageDialog(null, "Password reset successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to reset password.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
    public void deleteAllProducts() {
        productDAO.deleteAllProducts();
    }
    public List<Order> getAllOrders() { return orderDAO.getAllOrders(); }
    public void addOrder(Order o) { orderDAO.addOrder(o); }
    public void updateOrder(Order o) { orderDAO.updateOrder(o); }
    public void deleteOrder(int id) { orderDAO.deleteOrder(id); }
    public void deleteAllOrders() { orderDAO.deleteAllOrders(); }

    // Add more backend logic for dashboard, products, orders, etc. as needed
    // Example stub for dashboard logic
    public static void showDashboard() {
        // Implement dashboard backend logic here if needed
    }

    // Example stub for notification logic
    public static void showNotifications() {
        // Implement notification backend logic here if needed
    }
}
package DAO;

import Model.User;
import Database.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    // Register a new user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, full_name, company_name, email, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // In production, store hashed passwords
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getCompanyName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getRole());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Authenticate user
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("company_name"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Check if username exists
    public boolean usernameExists(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean resetPassword(String username, String newPassword) {
        String checkSql = "SELECT username FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    // Username does not exist
                    return false;
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, newPassword); // In production, hash the password!
                updateStmt.setString(2, username);
                int affectedRows = updateStmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Check if email exists
   

    // --- PRODUCT OPERATIONS ---

    // Add a new product
       public boolean addProduct(String name, String manufacturer, double cost, String supplier) {
        String sql = "INSERT INTO inventory_management_system (name, manufacturer, cost, supplier) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, manufacturer);
            pstmt.setDouble(3, cost);
            pstmt.setString(4, supplier);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 

    // Update an existing product by name
    public boolean updateProduct(String name, String manufacturer, double cost, String supplier) {
        String sql = "UPDATE inventory_management_system SET manufacturer=?, cost=?, supplier=? WHERE name=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, manufacturer);
            pstmt.setDouble(2, cost);
            pstmt.setString(3, supplier);
            pstmt.setString(4, name);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all products
    public ResultSet getAllProducts() {
        String sql = "SELECT * FROM inventory_management_system";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- ORDER OPERATIONS ---

    // Add or update an order (by order_id)
    public boolean addOrUpdateOrder(int orderId, String product, String category, String salesChannel, String instruction,
                                    int items, String status, String storeName, String stockAdjustment, int qty) {
        String sql = "INSERT INTO inventory_management_system (order_id, product, category, sales_channel, instruction, items, status, store_name, stock_adjustment, qty) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE product=VALUES(product), category=VALUES(category), sales_channel=VALUES(sales_channel), " +
                     "instruction=VALUES(instruction), items=VALUES(items), status=VALUES(status), store_name=VALUES(store_name), " +
                     "stock_adjustment=VALUES(stock_adjustment), qty=VALUES(qty)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setString(2, product);
            pstmt.setString(3, category);
            pstmt.setString(4, salesChannel);
            pstmt.setString(5, instruction);
            pstmt.setInt(6, items);
            pstmt.setString(7, status);
            pstmt.setString(8, storeName);
            pstmt.setString(9, stockAdjustment);
            pstmt.setInt(10, qty);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all orders
    public ResultSet getAllOrders() {
        String sql = "SELECT * FROM inventory_management_system WHERE order_id IS NOT NULL";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

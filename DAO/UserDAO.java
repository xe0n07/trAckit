package DAO;

import Database.DatabaseConnection;
import java.sql.*;
import javax.swing.JOptionPane;

public class UserDAO {
    public boolean registerUser(String username, String company, String password) {
        String sql = "INSERT INTO users (username, company, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, company);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Show the real error in a dialog and print to output
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
package DAO;

import Model.Order;
import Database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class OrderDAO {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("id"),
                    rs.getString("product_name"),
                    rs.getString("category"),
                    rs.getString("sales_channel"),
                    rs.getInt("quantity"),
                    rs.getString("order_date"),
                    rs.getString("status")
                );
                o.setPurchasePrice(rs.getDouble("purchase_price"));
                o.setSellingPrice(rs.getDouble("selling_price"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                orders.add(o);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return orders;
    }

    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (product_name, category, sales_channel, quantity, order_date, status, purchase_price, selling_price, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getProductName());
            pstmt.setString(2, order.getCategory());
            pstmt.setString(3, order.getSalesChannel());
            pstmt.setInt(4, order.getQuantity());
            pstmt.setString(5, order.getOrderDate());
            pstmt.setString(6, order.getStatus());
            pstmt.setDouble(7, order.getPurchasePrice());
            pstmt.setDouble(8, order.getSellingPrice());
            pstmt.setDouble(9, order.getTotalAmount());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET product_name=?, category=?, sales_channel=?, quantity=?, order_date=?, status=?, purchase_price=?, selling_price=?, total_amount=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getProductName());
            pstmt.setString(2, order.getCategory());
            pstmt.setString(3, order.getSalesChannel());
            pstmt.setInt(4, order.getQuantity());
            pstmt.setString(5, order.getOrderDate());
            pstmt.setString(6, order.getStatus());
            pstmt.setDouble(7, order.getPurchasePrice());
            pstmt.setDouble(8, order.getSellingPrice());
            pstmt.setDouble(9, order.getTotalAmount());
            pstmt.setInt(10, order.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteAllOrders() {
        String deleteSql = "DELETE FROM orders";
        String resetSql = "ALTER TABLE orders AUTO_INCREMENT = 1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deleteSql);
            stmt.executeUpdate(resetSql);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
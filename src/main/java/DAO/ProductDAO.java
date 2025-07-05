package DAO;

import Model.Product;
import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getString("sales_channel"),
                    rs.getString("metrics"),
                    rs.getInt("quantity"),
                    rs.getString("sku"),
                    rs.getDouble("purchase_price"),
                    rs.getDouble("selling_price"),
                    rs.getObject("weight") != null ? rs.getDouble("weight") : null // handle nullable
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, sales_channel, metrics, quantity, sku, purchase_price, selling_price, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setString(3, product.getSalesChannel());
            pstmt.setString(4, product.getMetrics());
            pstmt.setInt(5, product.getQuantity());
            pstmt.setString(6, product.getSku());
            pstmt.setDouble(7, product.getPurchasePrice());
            pstmt.setDouble(8, product.getSellingPrice());
            if (product.getWeight() != null) {
                pstmt.setDouble(9, product.getWeight());
            } else {
                pstmt.setNull(9, Types.DOUBLE);
            }
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, category=?, sales_channel=?, metrics=?, quantity=?, sku=?, purchase_price=?, selling_price=?, weight=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setString(3, product.getSalesChannel());
            pstmt.setString(4, product.getMetrics());
            pstmt.setInt(5, product.getQuantity());
            pstmt.setString(6, product.getSku());
            pstmt.setDouble(7, product.getPurchasePrice());
            pstmt.setDouble(8, product.getSellingPrice());
            if (product.getWeight() != null) {
                pstmt.setDouble(9, product.getWeight());
            } else {
                pstmt.setNull(9, Types.DOUBLE);
            }
            pstmt.setInt(10, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllProducts() {
        String deleteSql = "DELETE FROM products";
        String resetSql = "ALTER TABLE products AUTO_INCREMENT = 1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deleteSql);
            stmt.executeUpdate(resetSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
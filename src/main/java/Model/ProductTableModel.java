/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Product;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private final String[] columns = {
    "Product ID", "Name", "Category", "Metrics", "Quantity", "Purchase Price", "Selling Price", "Weight", "SKU"
};
    private List<Product> products;

    public ProductTableModel(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() { return products.size(); }
    @Override
    public int getColumnCount() { return columns.length; }
    @Override
    public String getColumnName(int col) { return columns[col]; }
    @Override
    public Object getValueAt(int row, int col) {
        Product p = products.get(row);
        switch (col) {
            case 0: return (p.getId() == 0) ? "" : String.format("0250%d", p.getId());
            case 1: return p.getName();
            case 2: return p.getCategory();
            case 3: return p.getMetrics();
            case 4: return p.getQuantity();
            case 5: return p.getPurchasePrice();
            case 6: return p.getSellingPrice();
            case 7: return p.getWeight();
            case 8: return p.getSku();
            default: return null;
        }
    }
    @Override
    public boolean isCellEditable(int row, int col) {
        // Only Product ID (0) is not editable
        return col != 0;
    }
    @Override
    public void setValueAt(Object value, int row, int col) {
        Product p = products.get(row);
        switch (col) {
            case 1: p.setName((String)value); break;
            case 2: p.setCategory((String)value); break;
            case 3: p.setMetrics((String)value); break;
            case 4: p.setQuantity(Integer.parseInt(value.toString())); break;
            case 5: p.setPurchasePrice(Double.parseDouble(value.toString())); break;
            case 6: p.setSellingPrice(Double.parseDouble(value.toString())); break;
            case 7: p.setWeight(value == null || value.toString().isEmpty() ? null : Double.parseDouble(value.toString())); break;
            case 8: p.setSku((String)value); break;
        }
        fireTableCellUpdated(row, col);
    }
    public void addProduct(Product p) {
        products.add(p);
        fireTableRowsInserted(products.size() - 1, products.size() - 1);
    }
    public void removeProduct(int row) {
        products.remove(row);
        fireTableRowsDeleted(row, row);
    }
    public Product getProductAt(int row) {
        if (row < 0 || row >= products.size()) return null;
        return products.get(row);
    }
    public List<Product> getProducts() { return products; }
}

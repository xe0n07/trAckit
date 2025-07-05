package Model;

import Model.Product;
import Model.Order;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class StocksTableModel extends AbstractTableModel {
    private final String[] columns = {
        "Product ID", "Product Name", "Category", "Metrics", "Available Stock", "Sold Stock"
    };
    private final List<Product> products;
    private final List<Order> orders;

    public StocksTableModel(List<Product> products, List<Order> orders) {
        this.products = products;
        this.orders = orders;
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
            case 4: return getAvailableStock(p);
            case 5: return getSoldStock(p);
            default: return null;
        }
    }

    private int getSoldStock(Product p) {
        int sold = 0;
        for (Order o : orders) {
            if (o.getProductName().equals(p.getName()) && "Completed".equalsIgnoreCase(o.getStatus())) {
                sold += o.getQuantity();
            }
        }
        return sold;
    }

    private int getAvailableStock(Product p) {
        return p.getQuantity() - getSoldStock(p);
    }
}
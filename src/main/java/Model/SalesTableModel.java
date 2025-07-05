package Model;

import Model.Order;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SalesTableModel extends AbstractTableModel {
    private final String[] columns = {
        "Sale ID", "Product", "Category", "Quantity", "Sale Date",
        "Purchase Price", "Selling Price", "Total Amount", "Profit"
    };
    private List<Order> sales;

    public SalesTableModel(List<Order> sales) {
        this.sales = sales;
    }

    @Override
    public int getRowCount() { return sales.size(); }
    @Override
    public int getColumnCount() { return columns.length; }
    @Override
    public String getColumnName(int col) { return columns[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Order o = sales.get(row);
        switch (col) {
            case 0: return (o.getId() == 0) ? "" : String.format("0101%d", o.getId()); // Sale ID = Order ID
            case 1: return o.getProductName();
            case 2: return o.getCategory();
            case 3: return o.getQuantity();
            case 4: return o.getOrderDate(); // Sale Date = Order Date
            case 5: return o.getPurchasePrice();
            case 6: return o.getSellingPrice();
            case 7: return o.getTotalAmount();
            case 8: return (o.getSellingPrice() - o.getPurchasePrice()) * o.getQuantity(); // Profit
            default: return null;
        }
    }

    public void setSales(List<Order> sales) {
        this.sales = sales;
        fireTableDataChanged();
    }
}
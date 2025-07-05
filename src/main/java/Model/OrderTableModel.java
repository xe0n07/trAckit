package Model;

import Model.Order;
import Model.Product;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Component;

public class OrderTableModel extends AbstractTableModel {
    private final String[] columns = {
        "Order ID", "Product", "Category", "Quantity", "Order Date", "Status", "Purchase Price", "Selling Price", "Total Amount"
    };
    private List<Order> orders;
    private List<Product> products; // Add this line

    public OrderTableModel(List<Order> orders, List<Product> products) {
        this.orders = orders;
        this.products = products;
    }

    @Override public int getRowCount() { return orders.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int col) { return columns[col]; }
    @Override
    public Object getValueAt(int row, int col) {
        Order o = orders.get(row);
        switch (col) {
            case 0: return (o.getId() == 0) ? "" : String.format("0101%d", o.getId());
            case 1: return o.getProductName();
            case 2: return o.getCategory();
            case 3: return o.getQuantity();
            case 4: return o.getOrderDate();
            case 5: return o.getStatus();
            case 6: return o.getPurchasePrice();
            case 7: return o.getSellingPrice();
            case 8: return o.getTotalAmount(); // Should be quantity * selling price
            default: return null;
        }
    }
    @Override
    public boolean isCellEditable(int row, int col) {
        // Only Order ID (0), Category (2), Purchase Price (6), and Total Amount (8) are not editable
        return col != 0 && col != 2 && col != 6 && col != 8;
    }
    @Override
    public void setValueAt(Object value, int row, int col) {
        Order o = orders.get(row);
        switch (col) {
            case 1: o.setProductName((String)value); break;
            case 3: // Quantity
                int enteredQty = Integer.parseInt(value.toString());
                Product product = null;
                for (Product p : products) {
                    if (p.getName().equals(o.getProductName())) {
                        product = p;
                        break;
                    }
                }
                if (product != null && enteredQty > product.getQuantity()) {
                    if (product.getQuantity() == 0) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Out of stock!");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Only " + product.getQuantity() + " available!");
                    }
                    return; // Do not update
                }
                o.setQuantity(enteredQty);
                break;
            case 4: o.setOrderDate((String)value); break;
            case 5: o.setStatus((String)value); break;
            case 6: o.setPurchasePrice(Double.parseDouble(value.toString())); break;
            case 7: o.setSellingPrice(Double.parseDouble(value.toString())); break;
        }
        // Always update total amount when quantity or selling price changes
        if (col == 3 || col == 7) {
            o.setTotalAmount(o.getQuantity() * o.getSellingPrice());
            fireTableCellUpdated(row, 8); // 8 = Total Amount column
        }
        fireTableCellUpdated(row, col);
    }
    public void addOrder(Order o) {
        orders.add(o);
        fireTableRowsInserted(orders.size() - 1, orders.size() - 1);
    }
    public void removeOrder(int row) {
        orders.remove(row);
        fireTableRowsDeleted(row, row);
    }
    public Order getOrderAt(int row) {
        if (row < 0 || row >= orders.size()) return null;
        return orders.get(row);
    }
    public List<Order> getOrders() { return orders; }

    // Date Picker for Order Date column (column index 4)
    public void initDatePicker(JTable table) {
        table.getColumn("Order Date").setCellEditor(new DefaultCellEditor(new JTextField()) {
            JDateChooser dateChooser = new JDateChooser();
            {
                dateChooser.setDateFormatString("yyyy-MM-dd");
            }
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                try {
                    if (value != null && !value.toString().isEmpty()) {
                        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
                        dateChooser.setDate(date);
                    } else {
                        dateChooser.setDate(new java.util.Date());
                    }
                } catch (Exception e) {
                    dateChooser.setDate(new java.util.Date());
                }
                return dateChooser;
            }
            @Override
            public Object getCellEditorValue() {
                java.util.Date date = dateChooser.getDate();
                if (date != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    return "";
                }
            }
        });
    }
}
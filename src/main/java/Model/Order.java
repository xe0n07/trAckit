package Model;

public class Order {
    private int id;
    private String productName;
    private String category;
    private String salesChannel;
    private int quantity;
    private String orderDate;
    private String status;
    private double purchasePrice;
    private double sellingPrice;
    private double totalAmount;

    public Order(int id, String productName, String category, String salesChannel, int quantity, String orderDate, String status) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.salesChannel = salesChannel;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public String getSalesChannel() { return salesChannel; }
    public int getQuantity() { return quantity; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public double getPurchasePrice() { return purchasePrice; }
    public double getSellingPrice() { return sellingPrice; }
    public double getTotalAmount() { return totalAmount; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setCategory(String category) { this.category = category; }
    public void setSalesChannel(String salesChannel) { this.salesChannel = salesChannel; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setStatus(String status) { this.status = status; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
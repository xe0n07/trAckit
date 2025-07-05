package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Product {
    private int id;
    private String name;
    private String category;
    private String salesChannel;
    private String metrics;
    private int quantity;
    private String sku;
    private double purchasePrice;
    private double sellingPrice;
    private Double weight; // Use Double to allow null

    public Product() {}

    public Product(int id, String name, String category, String salesChannel, String metrics, int quantity, String sku, double purchasePrice, double sellingPrice, Double weight) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.salesChannel = salesChannel;
        this.metrics = metrics;
        this.quantity = quantity;
        this.sku = sku;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.weight = weight;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSalesChannel() { return salesChannel; }
    public void setSalesChannel(String salesChannel) { this.salesChannel = salesChannel; }

    public String getMetrics() { return metrics; }
    public void setMetrics(String metrics) { this.metrics = metrics; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public static String generateSKU(String category, String name) {
        String cat = (category.length() >= 3) ? category.substring(0, 3).toUpperCase() : category.toUpperCase();
        String prod = (name.length() >= 3) ? name.substring(0, 3).toUpperCase() : name.toUpperCase();
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        int rand = 100 + new Random().nextInt(900); // 3-digit random number
        return cat + "-" + prod + "-" + date + "-" + rand;
    }
}
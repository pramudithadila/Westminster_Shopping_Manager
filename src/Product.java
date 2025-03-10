public abstract class Product {
    // Fields representing the basic attributes of a product
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    // Abstract method to be implemented by subclasses to provide specific details of a product.
    public abstract String getDetails();

    // Constructor for creating a new Product instance.
    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getter and setter methods for the product's attributes.
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }



}

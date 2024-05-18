public class Electronics extends Product {
    // Additional attributes specific to Electronic items
    private int warrantyPeriod;
    private String brand;

    // Constructor for creating a new Electronic instance.
    public Electronics(String productId, String productName, int availableItems, double price, int warrantyPeriod, String brand) {
        super(productId, productName, availableItems, price);
        this.warrantyPeriod = warrantyPeriod;
        this.brand = brand;
    }

    // Getter and setter methods for clothing-specific attributes.
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Overrides the getDetails method from the Product class.
    public String getDetails() {
        return "Brand: " + getBrand() + "\nWarranty Period: " + getWarrantyPeriod() + " years warranty";
    }

}

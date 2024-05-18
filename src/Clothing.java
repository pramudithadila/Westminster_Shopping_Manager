public class Clothing extends Product {
    // Additional attributes specific to clothing items
    private String size;
    private String colour;

    // Constructor for creating a new Clothing instance.
    public Clothing(String productId, String productName, int availableItems, double price, String size, String colour) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.colour = colour;
    }

    // Getter and setter methods for clothing-specific attributes.
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // Overrides the getDetails method from the Product class.
    public String getDetails() {
        return "Size: " + getSize() + "\nColour: " + getColour();
    }
}

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    // List to store the products in the shopping cart.
    private List<Product> products;

    // Constructor for ShoppingCart.
    public ShoppingCart() {
        products = new ArrayList<>();
    }

    // Methods to add, remove products and Calculate the Total
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }
    public double calculateTotal() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    // Returns the list of products in the shopping cart.
    public List<Product> getProducts() {
        return products;
    }
}

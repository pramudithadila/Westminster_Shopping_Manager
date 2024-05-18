// Interface ShoppingManager representing the management functionalities of a shopping system.
// This defines methods for adding, removing, and finding products.

public interface ShoppingManager {
    void addProduct(Product product);
    void removeProduct(Product product);
    Product findProductById(String productId);
}

import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    // Constants for the maximum number of products and the file name for saving/loading products
    private static final int maximumProducts = 50;
    private static final String fileName = "products.txt";

    // List to store the products and a Scanner for user input
    private List<Product> products;
    private Scanner scanner;

    // Constructor initializing the product list and loading existing products from file.
    public WestminsterShoppingManager() {
        products = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadProducts();
    }

    // Displays the main menu and handles user input for various operations.
    public void displayMenu() {
        System.out.println("\nWestminster Shopping Manager");
        System.out.println("1. Add a new product");
        System.out.println("2. Remove a product");
        System.out.println("3. Print product list");
        System.out.println("4. Open GUI");
        System.out.println("5. Exit");
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        removeProduct();
                        break;
                    case 3:
                        printProductsList();
                        break;
                    case 4:
                        SwingUtilities.invokeLater(() -> new GUI(products));
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    // Handles the addition of a new product to the system.
    private void addNewProduct() {
        if (products.size() >= maximumProducts) {
            System.out.println("Cannot add more products. Maximum limit reached.");
            return;
        }
        System.out.println("Select the type of product to add:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Select an option: ");
        int type = scanner.nextInt();
        scanner.nextLine();
        switch (type) {
            case 1:
                addElectronics();
                break;
            case 2:
                addClothing();
                break;
            default:
                System.out.println("Invalid type selected.");
        }
    }

    // Handles the addition of a new electronic product.
    private void addElectronics() {
        System.out.println("Adding new Electronic product...");
        String id;
        String name;
        int items;
        double price;
        int warranty;
        String brand;
        while (true) {
            System.out.print("Enter product ID: ");
            id = scanner.nextLine();
            if (!id.isEmpty() && isUniqueId(id)) {
                break;
            }
            System.out.println("The product id is existing. Please enter a unique Product ID.");
        }
        while (true) {
            System.out.print("Enter product name: ");
            name = scanner.nextLine();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Product name cannot be empty. Please try again.");
        }
        while (true) {
            try {
                System.out.print("Enter number of available items: ");
                items = scanner.nextInt();
                if (items >= 0) {
                    break;
                }
                System.out.println("Number of available items must be non-negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
        while (true) {
            try {
                System.out.print("Enter price: ");
                price = scanner.nextDouble();
                if (price >= 0) {
                    break;
                }
                System.out.println("Price must be non-negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        while (true) {
            try {
                System.out.print("Enter warranty period (in years): ");
                warranty = scanner.nextInt();
                if (warranty >= 0) {
                    break;
                }
                System.out.println("Warranty period must be non-negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        while (true) {
            System.out.print("Enter brand: ");
            brand = scanner.nextLine();
            if (!brand.isEmpty()) {
                break;
            }
            System.out.println("Brand cannot be empty. Please try again.");
        }
        Electronics electronics = new Electronics(id, name, items, price, warranty, brand);
        products.add(electronics);
        System.out.println("Electronic product added successfully.");
    }

    // Handles the addition of a new clothing product.
    private void addClothing() {
        System.out.println("Adding new Clothing product...");
        String id;
        String name;
        int items;
        double price;
        String size;
        String colour;
        while (true) {
            System.out.print("Enter product ID: ");
            id = scanner.nextLine();
            if (!id.isEmpty() && isUniqueId(id)) {
                break;
            }
            System.out.println("The product id is existing. Please enter a unique Product ID.");
        }
        while (true) {
            System.out.print("Enter product name: ");
            name = scanner.nextLine();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Product name cannot be empty. Please try again.");
        }
        while (true) {
            try {
                System.out.print("Enter number of available items: ");
                items = scanner.nextInt();
                if (items >= 0) {
                    break;
                }
                System.out.println("Number of available items must be non-negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        while (true) {
            try {
                System.out.print("Enter price: ");
                price = scanner.nextDouble();
                if (price >= 0) {
                    break;
                }
                System.out.println("Price must be non-negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        while (true) {
            System.out.print("Enter size: ");
            size = scanner.nextLine();
            if (!size.isEmpty()) {
                break;
            }
            System.out.println("Size cannot be empty. Please try again.");
        }
        while (true) {
            System.out.print("Enter colour: ");
            colour = scanner.nextLine();
            if (!colour.isEmpty()) {
                break;
            }
            System.out.println("Colour cannot be empty. Please try again.");
        }
        Clothing clothing = new Clothing(id, name, items, price, size, colour);
        products.add(clothing);
        System.out.println("Clothing product added successfully.");
    }

    // Checks if a product ID is unique in the system.
    private boolean isUniqueId(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return false;
            }
        }
        return true;
    }

    // Handles the removing of a product from the system.
    private void removeProduct() {
        System.out.print("Enter the product ID to remove: ");
        String id = scanner.nextLine();
        Product toRemove = null;
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                toRemove = product;
                break;
            }
        }
        if (toRemove != null) {
            products.remove(toRemove);
            String productType = toRemove instanceof Electronics ? "Electronics" : "Clothing";
            System.out.println(productType + " product removed successfully.");
            System.out.println("Deleted Product Details: ");
            System.out.println("  Product ID: " + toRemove.getProductId());
            System.out.println("  Product Name: " + toRemove.getProductName());
            System.out.println("  Price: " + toRemove.getPrice());
            System.out.println("Total number of products left in the system: " + products.size());
        } else {
            System.out.println("Product not found.");
        }
    }

    // Prints the list of all products in the system.
    private void printProductsList() {
        if (products.isEmpty()) {
            System.out.println("No products in the system.");
            return;
        }
        Collections.sort(products, Comparator.comparing(Product::getProductId));
        System.out.println("\nList of Products:");
        for (Product product : products) {
            String productType = product instanceof Electronics ? "Electronics" : "Clothing";
            System.out.println("Type: " + productType);
            System.out.println("  Product ID: " + product.getProductId());
            System.out.println("  Product Name: " + product.getProductName());
            System.out.println("  Available Items: " + product.getAvailableItems());
            System.out.println("  Price: " + product.getPrice());
            System.out.println();
        }
    }

    @Override
    public void addProduct(Product product) {   // Code for adding a product to the list.
        if (products.size() < maximumProducts) {
            products.add(product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Cannot add more products. Maximum limit reached.");
        }
    }
    @Override
    public void removeProduct(Product product) {    // Code for removing a product from the list.
        products.remove(product);
    }

    // Saves the current list of products to a file.
    private void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : products) {
                writer.write(productToString(product));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // Loads products from a file into the system.
    private void loadProducts() {
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Product product = stringToProduct(line);
                    if (product != null) {
                        products.add(product);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading products: " + e.getMessage());
            }
        }
    }

    // Converts a Product object to a string for file storage.
    private String productToString(Product product) {
        String productType = product instanceof Electronics ? "Electronics" : "Clothing";
        String basicInfo = product.getProductId() + "," + product.getProductName() + ","
                + product.getAvailableItems() + "," + product.getPrice();

        if (product instanceof Electronics) {
            Electronics elec = (Electronics) product;
            return productType + "," + basicInfo + "," + elec.getWarrantyPeriod() + "," + elec.getBrand();
        } else if (product instanceof Clothing) {
            Clothing cloth = (Clothing) product;
            return productType + "," + basicInfo + "," + cloth.getSize() + ","
                    + cloth.getColour();
        }
        return null;
    }

    // Converts a string from the file to a Product object.
    private Product stringToProduct(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] attributes = line.split(",");
        if (attributes.length < 5) {
            System.out.println("Invalid product line: " + line);
            return null;
        }
        String type = attributes[0];
        String productId = attributes[1];
        String productName = attributes[2];
        int availableItems = Integer.parseInt(attributes[3]);
        double price = Double.parseDouble(attributes[4]);
        if ("Electronics".equals(type)) {
            if (attributes.length < 7) {
                System.out.println("Invalid electronics line: " + line);
                return null;
            }
            int warrantyPeriod = Integer.parseInt(attributes[5]);
            String brand = attributes[6];
            return new Electronics(productId, productName, availableItems, price, warrantyPeriod, brand);
        } else if ("Clothing".equals(type)) {
            if (attributes.length < 7) {
                System.out.println("Invalid clothing line: " + line);
                return null;
            }
            String size = attributes[5];
            String colour = attributes[6];
            return new Clothing(productId, productName, availableItems, price, size, colour);
        }
        return null;
    }
    @Override
    public Product findProductById(String productId) {  // Code for finding a product by ID
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // Main method to run the WestminsterShoppingManager application.
    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        while (true) {
            manager.displayMenu();
            manager.saveProducts();
        }
    }
}
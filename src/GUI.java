import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends JFrame {
    // GUI components declaration.
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryComboBox;
    private JTextArea productDetailsArea;
    private ShoppingCart shoppingCart;
    private List<Product> products;

    // Constructor initializing the GUI components.
    public GUI(List<Product> products) {
        this.products = products;
        shoppingCart = new ShoppingCart();

        // Setup GUI components like tables, buttons, and panels.
        setTitle("Westminster Shopping Centre");
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Initialize and set up the product table.
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Product Category"));
        String[] categories = {"All", "Electronics", "Clothing"};
        categoryComboBox = new JComboBox<>(categories);
        topPanel.add(categoryComboBox);

        JButton cartButton = new JButton("Shopping Cart");
        cartButton.addActionListener(e -> showShoppingCart());
        topPanel.add(cartButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> showProductDetails());
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price(£)");
        tableModel.addColumn("Info");
        loadProductTableData("All");
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String productId = (String) tableModel.getValueAt(table.convertRowIndexToModel(row), 0);
                Product product = findProductById(productId);
                if (product != null && product.getAvailableItems() < 3) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };
        productTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
        JPanel detailsPanel = new JPanel(new BorderLayout());
        productDetailsArea = new JTextArea(7, 20);
        productDetailsArea.setEditable(false);
        detailsPanel.add(new JScrollPane(productDetailsArea), BorderLayout.CENTER);

        JButton addButton = new JButton("Add to Shopping Cart");
        addButton.addActionListener(e -> addToCart());
        detailsPanel.add(addButton, BorderLayout.SOUTH);

        add(detailsPanel, BorderLayout.SOUTH);

        categoryComboBox.addActionListener(e -> loadProductTableData(categoryComboBox.getSelectedItem().toString()));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Loads product data into the table based on the selected category.
    private void loadProductTableData(String category) {
        tableModel.setRowCount(0);
        products.stream()
                .filter(product -> category.equals("All") || product.getClass().getSimpleName().equalsIgnoreCase(category))
                .sorted(Comparator.comparing(Product::getProductId))
                .forEach(product -> {
                    String info = product instanceof Electronics
                            ? ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty"
                            : ((Clothing) product).getSize() + ", " + ((Clothing) product).getColour();
                    tableModel.addRow(new Object[]{product.getProductId(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), info});
                });
    }

    // Displays the details of the selected product in the text area.
    private void showProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = products.stream()
                    .filter(product -> product.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (selectedProduct != null) {
                productDetailsArea.setText(
                        "Product Id: " + selectedProduct.getProductId() +
                                "\nCategory: " + selectedProduct.getClass().getSimpleName() +
                                "\nName: " + selectedProduct.getProductName() +
                                "\nPrice: " + selectedProduct.getPrice() +
                                "\nItems Available: " + selectedProduct.getAvailableItems() +
                                "\n" + selectedProduct.getDetails()
                );

            }
        }
    }

    // Adds the selected product to the shopping cart.
    private void addToCart () {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = products.stream()
                    .filter(product -> product.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (selectedProduct != null) {
                shoppingCart.addProduct(selectedProduct);
                JOptionPane.showMessageDialog(this, "Added to cart: " + selectedProduct.getProductName());
            }
        }
    }

    // Displays the shopping cart in a new dialog with the list of added products.
    private void showShoppingCart() {
        DefaultTableModel cartModel = new DefaultTableModel();
        cartModel.addColumn("Product");
        cartModel.addColumn("Quantity");
        cartModel.addColumn("Price");
        JTable cartTable = new JTable(cartModel);
        Map<String, Integer> quantityMap = new HashMap<>();
        Map<String, String> productDetailsMap = new HashMap<>();
        double total = 0;
        for (Product product : shoppingCart.getProducts()) {
            total += product.getPrice();
            String productId = product.getProductId();
            quantityMap.put(productId, quantityMap.getOrDefault(productId, 0) + 1);
            if (!productDetailsMap.containsKey(productId)) {
                String details = String.format("%s\n%s\n%s",
                        productId,
                        product.getProductName(),
                        product instanceof Electronics ? ((Electronics) product).getBrand() : ((Clothing) product).getSize() + ", " + ((Clothing) product).getColour());
                productDetailsMap.put(productId, details);
            }
        }

        for (Map.Entry<String, Integer> entry : quantityMap.entrySet()) {
            String productId = entry.getKey();
            String productDetails = productDetailsMap.get(productId);
            if (productDetails != null) {
                String formattedDetails = String.format("%s %s %s",
                        productId,
                        productDetails.substring(productId.length(), productId.length() + "Trouser".length()),
                        productDetails.substring(productId.length() + "Trouser".length()));
                cartModel.addRow(new Object[]{
                        formattedDetails,
                        entry.getValue().toString(),
                        String.format("%.2f £", findProductById(productId).getPrice())
                });
            }
        }

        double discount = 0;
        Map<String, Long> categoryCount = shoppingCart.getProducts().stream()
                .collect(Collectors.groupingBy(p -> p.getClass().getSimpleName(), Collectors.counting()));
        for (Long count : categoryCount.values()) {
            if (count >= 3) {
                discount = total * 0.2;
                break;
            }
        }
        double finalTotal = total - discount;
        JPanel totalPanel = new JPanel(new GridLayout(0, 1));
        totalPanel.add(new JLabel("Total: " + String.format("%.2f £", total)));
        if (discount > 0) {
            totalPanel.add(new JLabel("Three Items in same Category Discount (20%): -" + String.format("%.2f £", discount)));
        }
        totalPanel.add(new JLabel("Final Total: " + String.format("%.2f £", finalTotal)));

        JDialog cartDialog = new JDialog(this, "Shopping Cart", true);
        cartDialog.setLayout(new BorderLayout());
        cartDialog.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        cartDialog.add(totalPanel, BorderLayout.SOUTH);
        cartDialog.pack();
        cartDialog.setVisible(true);
    }

    // Finds a product by its ID from the list of products.
    private Product findProductById(String productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}

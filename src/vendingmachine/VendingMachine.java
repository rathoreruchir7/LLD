import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class VendingMachine {

    private final Map<Integer, Product> products =
            new LinkedHashMap<>();
    private int balance = 0;
    private Product selectedProduct;

    //State Objects
    private final VendingState idleState;
    private final VendingState hasMoneyState;
    private final VendingState selectedState;

    //Current state
    private VendingState currentState;

    public VendingMachine() {
        idleState = new IdleState(this);
        hasMoneyState = new HasMoneyState(this);
        selectedState = new SelectedState(this);

        currentState = idleState;
    }

    public void addProduct(Product product) {
        Objects.requireNonNull(product);

        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("duplicate product id");
        }

        products.put(product.getId(), product);
    }

    public void displayProducts() {
        for (Product product : products.values()) {
            System.out.println(
                    product.getId() + " - " +
                            product.getName() + " | Price: " +
                            product.getPrice() + " | Stock: " +
                            product.getQuantity()
            );
        }
    }

    // Public operations delegated to current state

    public void insertMoney(int amount) {
        currentState.insertMoney(amount);
    }

    public void selectProduct(int productId) {
        currentState.selectProduct(productId);
    }

    public void dispense() {
        currentState.dispense();
    }

    public void cancel() {
        currentState.cancel();
    }

    void addBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive");
        }
        balance = Math.addExact(balance, amount);
    }

    int getBalance() {
        return balance;
    }

    Product findProduct(int id) {
        Product product = products.get(id);

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product not found");
        }

        return product;
    }

    void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    void setState(VendingState state) {
        currentState = state;
    }

    VendingState getIdleState() {
        return idleState;
    }

    VendingState getHasMoneyState() {
        return hasMoneyState;
    }

    VendingState getSelectedState() {
        return selectedState;
    }

    void completePurchase() {
        if (selectedProduct == null) {
            throw new IllegalStateException(
                    "No selected product");
        }

        // Must succeed before clearing transaction
        selectedProduct.reduceQuantity();

        int change = balance - selectedProduct.getPrice();

        System.out.println(
                "Dispensed: " + selectedProduct.getName());
        System.out.println("Change: " + change);

        reset();
    }

    void refundAndReset() {
        System.out.println("Refunded: " + balance);
        reset();
    }

    private void reset() {
        balance = 0;
        selectedProduct = null;
        currentState = idleState;
    }


}

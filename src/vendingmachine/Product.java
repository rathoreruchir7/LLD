public class Product {

    private final int id;
    private final String name;
    private final int price;
    private int quantity;

    public Product(int id, String name,
                   int price, int quantity) {

        if (price <= 0 || quantity < 0) {
            throw new IllegalArgumentException(
                    "Invalid price or quantity");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        if (quantity <= 0) {
            throw new IllegalStateException("Out of stock");
        }
        quantity--;
    }
}
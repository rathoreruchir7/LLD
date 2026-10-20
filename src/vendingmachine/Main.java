public class Main {

    public static void main(String[] args) {

        VendingMachine machine = new VendingMachine();

        machine.addProduct(
                new Product(1, "Cola", 40, 5));

        machine.addProduct(
                new Product(2, "Chips", 20, 10));

        machine.displayProducts();

        machine.insertMoney(50);

        machine.selectProduct(1);

        machine.dispense();

        // Duplicate dispense attempt
        try {
            machine.dispense();
        } catch (IllegalStateException e) {
            System.out.println(
                    "Rejected: " + e.getMessage());
        }
    }
}
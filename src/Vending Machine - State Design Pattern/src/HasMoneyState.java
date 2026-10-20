public class HasMoneyState implements VendingState {

    private final VendingMachine machine;

    public HasMoneyState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney(int amount) {

        machine.addBalance(amount);

        System.out.println(
                "Current balance: " + machine.getBalance());
    }

    @Override
    public void selectProduct(int productId) {

        Product product = machine.findProduct(productId);

        if (product.getQuantity() <= 0) {
            throw new IllegalStateException("Out of stock");
        }

        if (machine.getBalance() < product.getPrice()) {
            throw new IllegalStateException(
                    "Insufficient balance");
        }

        machine.setSelectedProduct(product);

        machine.setState(machine.getSelectedState());

        System.out.println(
                "Product selected: " + product.getName());
    }

    @Override
    public void cancel() {
        machine.refundAndReset();
    }
}
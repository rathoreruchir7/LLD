public interface VendingState {

    default void insertMoney(int amount) {
        throw new IllegalStateException("Cannot insert money in this state");
    }

    default void selectProduct(int productId) {
        throw new IllegalStateException("Cannot select product in this state");
    }

    default void dispense() {
        throw new IllegalStateException("Cannot dispense in this state");
    }

    default void cancel() {
        throw new IllegalStateException(
                "Cannot cancel in this state");
    }
}

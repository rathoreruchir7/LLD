public class SelectedState implements VendingState {

    private final VendingMachine machine;

    public SelectedState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void dispense() {
        machine.completePurchase();
    }

    @Override
    public void cancel() {
        machine.refundAndReset();
    }
}
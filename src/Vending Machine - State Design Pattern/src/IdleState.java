public class IdleState implements VendingState {

    private final VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney(int amount) {

        machine.addBalance(amount);

        machine.setState(machine.getHasMoneyState());

        System.out.println("Money inserted: " + amount);
    }
}
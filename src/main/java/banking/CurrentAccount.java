package banking;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000.0; // Example overdraft limit

    public CurrentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount >= -OVERDRAFT_LIMIT) {
            setBalance(getBalance() - amount);
        } else {
            System.out.println("Withdrawal amount exceeds overdraft limit.");
            // Additional handling logic if required, such as notifying the user or logging the event
        }
    }
}
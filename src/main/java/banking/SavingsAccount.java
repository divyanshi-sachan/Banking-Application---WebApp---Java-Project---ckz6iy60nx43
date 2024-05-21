package banking;

public class SavingsAccount extends Account {
    private static final double SAVINGS_ACCOUNT_INTEREST_RATE = 0.06; // Example interest rate for savings account

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
        } else {
            System.out.println("Insufficient funds.");
            // Additional handling logic if required, such as notifying the user or logging the event
        }
    }

    public double calculateInterest() {
        return getBalance() * SAVINGS_ACCOUNT_INTEREST_RATE;
    }
}
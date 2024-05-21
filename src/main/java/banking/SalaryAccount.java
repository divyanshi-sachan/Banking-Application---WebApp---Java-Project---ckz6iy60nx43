package banking;

public class SalaryAccount extends Account {
    private static final double SALARY_ACCOUNT_INTEREST_RATE = 0.05; // Example interest rate for salary account

    public SalaryAccount(String accountNumber, double balance) {
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
        return getBalance() * SALARY_ACCOUNT_INTEREST_RATE;
    }
}
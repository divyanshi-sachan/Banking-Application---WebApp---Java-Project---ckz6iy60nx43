package banking;

public class PersonalLoan extends Loan {
    private static final double PERSONAL_LOAN_INTEREST_RATE = 0.08; // Example interest rate for personal loan

    public PersonalLoan(double principalAmount, int loanPeriod) {
        super(principalAmount, PERSONAL_LOAN_INTEREST_RATE, loanPeriod);
    }

    @Override
    public double calculateInterest() {
        return getPrincipalAmount() * getInterestRate() * getLoanPeriod();
    }
}
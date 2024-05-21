package banking;

public class EducationLoan extends Loan {
    private static final double EDUCATION_LOAN_INTEREST_RATE = 0.06; // Example interest rate for education loan

    public EducationLoan(double principalAmount, int loanPeriod) {
        super(principalAmount, EDUCATION_LOAN_INTEREST_RATE, loanPeriod);
    }

    @Override
    public double calculateInterest() {
        return getPrincipalAmount() * getInterestRate() * getLoanPeriod();
    }
}
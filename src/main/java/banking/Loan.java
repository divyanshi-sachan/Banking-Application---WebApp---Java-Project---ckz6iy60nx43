package banking;

public abstract class Loan {
    private double principalAmount;
    private double interestRate;
    private int loanPeriod;

    public Loan(double principalAmount, double interestRate, int loanPeriod) {
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.loanPeriod = loanPeriod;
    }

    public abstract double calculateInterest();

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }
}

public class EducationLoan extends Loan {
    public EducationLoan(double principalAmount, double interestRate, int loanPeriod) {
        super(principalAmount, interestRate, loanPeriod);
    }

    @Override
    public double calculateInterest() {
        // Implement logic to calculate education loan interest
        return getPrincipalAmount() * getInterestRate() * getLoanPeriod();
    }
}

public class PersonalLoan extends Loan {
    public PersonalLoan(double principalAmount, double interestRate, int loanPeriod) {
        super(principalAmount, interestRate, loanPeriod);
    }

    @Override
    public double calculateInterest() {
        // Implement logic to calculate personal loan interest
        return getPrincipalAmount() * getInterestRate() * getLoanPeriod();
    }
}
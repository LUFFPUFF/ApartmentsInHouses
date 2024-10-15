package apartment.in.houses.service.mortgagecalculator;

import java.util.*;

public class MortgageCalculator {

    private static double calculateMonthlyPayment(double loanAmount, double annualInterestRate, int loanTermInYears) {
        double monthlyInterestRate = annualInterestRate / 100 / 12;
        int numberOfPayments = loanTermInYears * 12;

        return loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments)) /
                (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
    }

    public static List<Redemption> generateAmortizationSchedule(double loanAmount, double annualInterestRate, int loanTermInYears, double monthlyPayment) {
        int numberOfPayments = loanTermInYears * 12;
        double remainingBalance = loanAmount;
        double monthlyInterestRate = annualInterestRate / 100 / 12;

        List<Redemption> schedule = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int month = 1; month <= numberOfPayments; month++) {
            double interestForThisMonth = remainingBalance * monthlyInterestRate;
            double principalForThisMonth = monthlyPayment - interestForThisMonth;
            remainingBalance -= principalForThisMonth;

            String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru"));
            calendar.add(Calendar.MONTH, 1);

            Redemption redemption = new Redemption(month, monthName, monthlyPayment, interestForThisMonth, remainingBalance);
            schedule.add(redemption);
        }

        return schedule;
    }

    public static double totalInterest(double loanAmount, double annualInterestRate, int loanTermInYears, double monthlyPayment) {
        return generateAmortizationSchedule(loanAmount, annualInterestRate, loanTermInYears, monthlyPayment).stream()
                .mapToDouble(Redemption::getInterestPayment).sum();
    }

    public static double totalAmount(double loanAmount, double annualInterestRate, int loanTermInYears, double monthlyPayment, double downPayment) {
        double principal = loanAmount - downPayment;
        return totalInterest(loanAmount, annualInterestRate, loanTermInYears, monthlyPayment) + principal;
    }
}

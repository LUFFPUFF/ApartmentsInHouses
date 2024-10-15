package apartment.in.houses.service.mortgagecalculator;

public class Redemption {
    private int number;
    private String month;
    private double paymentAmount;
    private double interestPayment;
    private double remainder;

    public Redemption(int number, String month, double paymentAmount, double interestPayment, double remainder) {
        this.number = number;
        this.month = month;
        this.paymentAmount = paymentAmount;
        this.interestPayment = interestPayment;
        this.remainder = remainder;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(double interestPayment) {
        this.interestPayment = interestPayment;
    }

    public double getRemainder() {
        return remainder;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }

    @Override
    public String toString() {
        return String.format("Месяц %d (%s): Платёж: %.2f руб., Проценты: %.2f руб., Остаток долга: %.2f руб.",
                number, month, paymentAmount, interestPayment, remainder);
    }
}

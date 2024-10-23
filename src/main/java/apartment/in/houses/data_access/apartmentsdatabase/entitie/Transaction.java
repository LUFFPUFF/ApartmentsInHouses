package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int id;
    private Apartment apartmentId;
    private Client clientId;
    private Date transactionDate;
    private double amount;

    public Transaction(int id, Apartment apartmentId, Client clientId, Date transactionDate, double amount) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.clientId = clientId;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApartmentId() {
        return apartmentId.getId();
    }

    public void setApartmentId(Apartment apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getClientId() {
        return clientId.getId();
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Component
@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Apartment.class, optional = false)
    @Column(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne(targetEntity = Client.class, optional = false)
    @Column(name = "client_id")
    private Client client;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "amount")
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApartment() {
        return apartment.getId();
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public int getClient() {
        return client.getId();
    }

    public void setClient(Client client) {
        this.client = client;
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

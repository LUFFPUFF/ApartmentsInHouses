package apartment.in.houses.data_access.apartmentsdatabase.entities;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Component
@Entity
@Table(name = "Client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Apartment.class, optional = false)
    @Column(name = "apartment_id")
    private Apartment apartmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    public Client(int id, Apartment apartment_id, String name, int phoneNumber, String email, String passportNumber, Date dateOfBirth) {
        this.id = id;
        this.apartmentId = apartment_id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apartment getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Apartment apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

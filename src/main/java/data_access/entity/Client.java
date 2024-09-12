package data_access.entity;

import java.util.Date;

public class Client {

    private int id;
    private Apartment apartment_id;
    private String name;
    private int phoneNumber;
    private String email;
    private int passportNumber;
    private Date dateOfBirth;

    public Client(int id, Apartment apartment_id, String name, int phoneNumber, String email, int passportNumber, Date dateOfBirth) {
        this.id = id;
        this.apartment_id = apartment_id;
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

    public Apartment getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(Apartment apartment_id) {
        this.apartment_id = apartment_id;
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

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

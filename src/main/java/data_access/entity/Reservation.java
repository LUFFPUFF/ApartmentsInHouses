package data_access.entity;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {

    private int id;
    private Apartment apartmentId;
    private House houseId;
    private Date reservationDate;
    private Date expiryDate;

    public Reservation(int id, Apartment apartmentId, House houseId, Date reservationDate, Date expiryDate) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.houseId = houseId;
        this.reservationDate = reservationDate;
        this.expiryDate = expiryDate;
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

    public int getHouseId() {
        return houseId.getId();
    }

    public void setHouseId(House houseId) {
        this.houseId = houseId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}

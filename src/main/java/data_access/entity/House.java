package data_access.entity;

import java.util.Date;

public class House {

    private int id;
    private String address;
    private String name;
    private Date startConstructionDate;
    private Date endConstructionDate;
    private Date commissioningDate;

    public House(int id, String address, String name, Date startConstructionDate, Date endConstructionDate, Date commissioningDate) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.startConstructionDate = startConstructionDate;
        this.endConstructionDate = endConstructionDate;
        this.commissioningDate = commissioningDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartConstructionDate() {
        return startConstructionDate;
    }

    public void setStartConstructionDate(Date startConstructionDate) {
        this.startConstructionDate = startConstructionDate;
    }

    public Date getEndConstructionDate() {
        return endConstructionDate;
    }

    public void setEndConstructionDate(Date endConstructionDate) {
        this.endConstructionDate = endConstructionDate;
    }

    public Date getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(Date commissioningDate) {
        this.commissioningDate = commissioningDate;
    }
}

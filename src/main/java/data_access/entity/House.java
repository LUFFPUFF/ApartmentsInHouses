package data_access.entity;


import BL.createHouse.HouseDataProvider;
import util.DI.annotation.Autowired;
import util.DI.annotation.Component;
import util.DI.annotation.Qualifier;

import java.io.Serializable;
import java.sql.Date;

@Component
public class House implements Serializable {

    private int id;
    private String address;

    private String name;

    private Date start_construction_date;

    private Date end_construction_date;

    private Date commissioning_date;

    @Autowired
    HouseDataProvider houseDataProvider;


    public House() {
    }

    @Autowired
    public House(HouseDataProvider provider) {

        this.id = provider.getId();
        this.address = provider.getAddress();
        this.name = provider.getName();
        this.start_construction_date = provider.getStartConstructionDate();
        this.end_construction_date = provider.getEndConstructionDate();
        this.commissioning_date = provider.getCommissioningDate();
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

    public Date getStart_construction_date() {
        return start_construction_date;
    }

    public void setStart_construction_date(Date start_construction_date) {
        this.start_construction_date = start_construction_date;
    }

    public Date getEnd_construction_date() {
        return end_construction_date;
    }

    public void setEnd_construction_date(Date end_construction_date) {
        this.end_construction_date = end_construction_date;
    }

    public Date getCommissioning_date() {
        return commissioning_date;
    }

    public void setCommissioning_date(Date commissioning_date) {
        this.commissioning_date = commissioning_date;
    }
}

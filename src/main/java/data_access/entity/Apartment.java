package data_access.entity;

import util.DI.annotation.Autowired;
import util.DI.annotation.Component;

import java.io.Serializable;

@Component
public class Apartment implements Serializable {

    public enum SaleCondition {
        FREE, RESERVED, SOLD
    }

    private int id;
    private int house_id;
    private double totalArea;
    private double livingArea;
    private int rooms;
    private int floor;
    private int entrance;
    private double price;
    private SaleCondition saleCondition;


    @Autowired
    public Apartment(int id, int houseId, double totalArea, double livingArea, int rooms, int floor, int entrance, double price, String saleCondition) {
        this.id = id;
        this.house_id = houseId;
        this.totalArea = totalArea;
        this.livingArea = livingArea;
        this.rooms = rooms;
        this.floor = floor;
        this.entrance = entrance;
        this.price = price;
        this.saleCondition = SaleCondition.valueOf(saleCondition);
    }

    public Apartment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouse_id() {

        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(double livingArea) {
        this.livingArea = livingArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSaleCondition() {
        return saleCondition.name();
    }

    public void setSaleCondition(SaleCondition saleCondition) {
        this.saleCondition = saleCondition;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", house_id=" + house_id +
                ", totalArea=" + totalArea +
                ", livingArea=" + livingArea +
                ", rooms=" + rooms +
                ", floor=" + floor +
                ", entrance=" + entrance +
                ", price=" + price +
                ", saleCondition=" + saleCondition +
                '}';
    }
}

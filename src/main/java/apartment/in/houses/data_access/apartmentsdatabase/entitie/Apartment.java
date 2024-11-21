package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.annotation.*;

import java.io.Serializable;

@Component
@Entity
@Table(name = "Apartments")
public class Apartment implements Serializable {

    public enum SaleCondition {
        FREE, RESERVED, SOLD
    }

    @Id
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = House.class, optional = false)
    @Column(name = "house_id")
    private House house;
    @Column(name = "total_area")
    private double totalArea;
    @Column(name = "living_area")
    private double livingArea;
    @Column(name = "rooms")
    private int rooms;
    @Column(name = "floor")
    private int floor;
    @Column(name = "entrance")
    private int entrance;
    @Column(name = "price")
    private double price;
    @Column(name = "sale_condition")
    private String saleCondition;
//    @Column(name = "image_path")
//    private String imagePath;

    public Apartment(int id, House house, double totalArea, double livingArea, int rooms, int floor, int entrance, double price, String saleCondition) {
        this.id = id;
        this.house = house;
        this.totalArea = totalArea;
        this.livingArea = livingArea;
        this.rooms = rooms;
        this.floor = floor;
        this.entrance = entrance;
        this.price = price;
        this.saleCondition = saleCondition;
    }

    public Apartment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public House getHouseId() {

        return house;
    }

    public void setHouseId(House houseId) {
        this.house = houseId;
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
        return saleCondition;
    }

    public void setSaleCondition(String saleCondition) {
        this.saleCondition = saleCondition;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", house_id=" + house +
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

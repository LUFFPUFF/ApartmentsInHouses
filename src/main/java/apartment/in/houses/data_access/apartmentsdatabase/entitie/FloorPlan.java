package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import java.io.Serializable;

public class FloorPlan implements Serializable {

    private int id;
    private House house;
    private int floorNumber;
    private String imagePath;

    public FloorPlan(int id, House houseId, int floorNumber, String imagePath) {
        this.id = id;
        this.house = houseId;
        this.floorNumber = floorNumber;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouse() {
        return house.getId();
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

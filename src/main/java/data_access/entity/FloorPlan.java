package data_access.entity;

import java.io.Serializable;

public class FloorPlan implements Serializable {

    private int id;
    private House houseId;
    private int floorNumber;
    private String imagePath;

    public FloorPlan(int id, House houseId, int floorNumber, String imagePath) {
        this.id = id;
        this.houseId = houseId;
        this.floorNumber = floorNumber;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseId() {
        return houseId.getId();
    }

    public void setHouseId(House houseId) {
        this.houseId = houseId;
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

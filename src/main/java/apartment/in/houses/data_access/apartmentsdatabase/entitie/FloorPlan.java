package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.annotation.*;

import java.io.Serializable;

@Component
@Entity
@Table(name = "FloorPlans")
public class FloorPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = House.class, optional = false)
    @Column(name = "house_id")
    private House house;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "image_path")
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

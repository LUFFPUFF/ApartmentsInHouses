package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.annotation.*;

import java.io.Serializable;
import java.sql.Date;

@Component
@Entity
@Table(name = "Houses")
public class House implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    private int id;

//    @ManyToOne(targetEntity = Admin.class, optional = false)
//    @Column(name = "admin_id")
//    private Admin admin;
    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "start_construction_date")
    private Date start_construction_date;
    @Column(name = "end_construction_date")
    private Date end_construction_date;

    @Column(name = "commissioning_date")
    private Date commissioning_date;

//    @Column(name = "image_path")
//    private String imagePath;

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

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", start_construction_date=" + start_construction_date +
                ", end_construction_date=" + end_construction_date +
                ", commissioning_date=" + commissioning_date +
                '}';
    }
}

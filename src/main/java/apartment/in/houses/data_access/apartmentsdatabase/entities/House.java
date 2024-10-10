package apartment.in.houses.data_access.apartmentsdatabase.entities;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Component
@Entity
@Table(name = "Houses")
public class House implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    private int id;
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

    @OneToMany(mappedBy = "house_id")
    private List<Apartment> apartments;

    public House() {
    }

    public House(int id, String address, String name, Date start_construction_date, Date end_construction_date, Date commissioning_date) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.start_construction_date = start_construction_date;
        this.end_construction_date = end_construction_date;
        this.commissioning_date = commissioning_date;
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

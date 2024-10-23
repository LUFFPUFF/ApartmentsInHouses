package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.*;

import java.io.Serializable;

@Entity
@Component
@Table(name = "Permissions")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

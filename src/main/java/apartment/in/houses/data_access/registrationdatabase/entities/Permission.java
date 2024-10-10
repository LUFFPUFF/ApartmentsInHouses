package apartment.in.houses.data_access.registrationdatabase.entities;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.Id;
import apartment.in.houses.util.orm.annotation.Table;

@Component
@Table(name = "Permission")
public class Permission {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Permission(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

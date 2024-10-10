package apartment.in.houses.data_access.registrationdatabase.entities.userrole;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.Id;
import apartment.in.houses.util.orm.annotation.Table;

@Component
@Table(name = "Roles")
public class Role {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name_role")
    private String nameRole;

    @Column(name = "description")
    private String description;

}

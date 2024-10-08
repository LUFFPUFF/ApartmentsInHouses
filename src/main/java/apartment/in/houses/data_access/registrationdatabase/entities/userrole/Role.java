package apartment.in.houses.data_access.registrationdatabase.entities.userrole;

import apartment.in.houses.data_access.util.exucutor.annotation.Column;
import apartment.in.houses.data_access.util.exucutor.annotation.Id;
import apartment.in.houses.data_access.util.exucutor.annotation.Table;
import apartment.in.houses.util.DI.annotation.Component;

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

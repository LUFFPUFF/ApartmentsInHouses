package apartment.in.houses.service.service.registrationservice;

import apartment.in.houses.data_access.registrationdatabase.entities.Registration;
import apartment.in.houses.util.DI.context.ApplicationContext;

import java.util.List;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {


        ApplicationContext context = new ApplicationContext("java");

        RegistrationService registrationService = context.getBean(RegistrationService.class);
        AdministratorService administratorService = context.getBean(AdministratorService.class);
    }
}

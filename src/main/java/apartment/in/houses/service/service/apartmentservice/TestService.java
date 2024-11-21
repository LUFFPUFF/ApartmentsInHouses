package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.framework.spring.DI.context.ApplicationContext;

import java.util.List;

public class TestService {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext("java");

        ApartmentService service = context.getBean(ApartmentService.class);

        List<Apartment> apartments = service.getAll();

        for (Apartment apartment : apartments) {
            System.out.println(apartment);
        }
    }
}

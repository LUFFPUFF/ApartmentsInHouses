package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entities.House;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.service.service.apartmentservice.HouseService;
import apartment.in.houses.util.DI.context.ApplicationContext;

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

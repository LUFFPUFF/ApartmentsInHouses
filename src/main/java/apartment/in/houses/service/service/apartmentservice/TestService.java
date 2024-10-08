package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.data_access.apartmentsdatabase.entities.House;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.service.service.apartmentservice.HouseService;
import apartment.in.houses.util.DI.context.ApplicationContext;

import java.util.List;

public class TestService {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext("java");

        context.refresh();

        ApartmentService service = context.getBean(ApartmentService.class);
        HouseService houseService = context.getBean(HouseService.class);

        System.out.println("Все квартиры: ");
        List<House> houses = houseService.getAll();

        for (House apartment : houses) {
            System.out.println(apartment);
        }
    }
}

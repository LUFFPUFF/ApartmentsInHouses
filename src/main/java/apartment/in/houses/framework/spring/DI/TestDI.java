package apartment.in.houses.framework.spring.DI;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.House;
import apartment.in.houses.framework.spring.DI.context.ApplicationContext;


public class TestDI {

    public static void main(String[] args) {

        ApplicationContext context = new ApplicationContext("java");

        //Пример объекта
        House house = context.getBean(House.class);

        house.setId(1);
        house.setName("Name");

        System.out.println("Проверка DI: " + house.getId() + " - " + house.getName());

    }
}

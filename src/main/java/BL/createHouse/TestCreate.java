package BL.createHouse;

import data_access.entity.House;
import util.DI.injector.Injector;

public class TestCreate {

    public static void main(String[] args) {

        Injector.startApplication(TestCreate.class);

        House house = Injector.getService(House.class);

        house.setId(5);

        System.out.println(house.getId());
    }
}

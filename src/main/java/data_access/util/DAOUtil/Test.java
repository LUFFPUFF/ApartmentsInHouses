package data_access.util.DAOUtil;

import data_access.entity.Apartment;
import data_access.entity.House;
import util.DI.annotation.Autowired;
import util.DI.injector.Injector;

import java.sql.Date;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        // Запуск инжектора с основным классом приложения
        Injector.startApplication(Test.class);

        // Получение экземпляра класса House из контейнера
        House house = Injector.getService(House.class);

        // Проверка значений, чтобы убедиться, что объект был создан
        System.out.println("House ID: " + house.getId());
        System.out.println("House Address: " + house.getAddress());
        System.out.println("House Name: " + house.getName());
        System.out.println("Construction Start Date: " + house.getStart_construction_date());
        System.out.println("Construction End Date: " + house.getEnd_construction_date());
        System.out.println("Commissioning Date: " + house.getCommissioning_date());
    }
}

package BL.createHouse;

import data_access.entity.House;
import data_access.util.DAOUtil.HouseController;
import util.DI.injector.Injector;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class TestCreate {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Injector.startApplication(TestCreate.class);
        startMenu();
    }

    private static void startMenu() {
        int option;

        do {

            System.out.println("=================================");
            System.out.println("    Домашняя система управления");
            System.out.println("=================================");
            System.out.println("1. Добавить новый дом");
            System.out.println("2. Показать все дома");
            System.out.println("3. Найти дом по ID");
            System.out.println("4. Выход");
            System.out.print("Выберите опцию: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    createHouse();
                    break;
                case 2:
                    getAll();
                    break;
                case 3:
                    System.out.print("Введите ID дома: ");
                    int id = scanner.nextInt();
                    getEntityById(id);
                    break;
                case 4:
                    System.out.println("Выход из программы. Спасибо за использование!");
                    break;
                default:
                    System.out.println("Неверная опция. Пожалуйста, выберите снова.");
            }
        } while (option != 4);
    }

    private static void createHouse() {
        System.out.println("\n=== Добавление нового дома ===");

        System.out.print("Введите ID: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите адрес: ");
        String address = scanner.nextLine();

        System.out.print("Введите название: ");
        String name = scanner.nextLine();

        System.out.print("Введите дату начала строительства (yyyy-mm-dd): ");
        Date start = Date.valueOf(scanner.nextLine());

        System.out.print("Введите дату завершения строительства (yyyy-mm-dd): ");
        Date end = Date.valueOf(scanner.nextLine());

        System.out.print("Введите дату ввода в эксплуатацию (yyyy-mm-dd): ");
        Date commissionDate = Date.valueOf(scanner.nextLine());

        House house = Injector.getService(House.class);
        assert house != null;
        house.setId(id);
        house.setAddress(address);
        house.setName(name);
        house.setStart_construction_date(start);
        house.setEnd_construction_date(end);
        house.setCommissioning_date(commissionDate);

        HouseController houseController = Injector.getService(HouseController.class);
        houseController.insert(house);

        System.out.println("Дом успешно добавлен!\n");
    }

    private static void getAll() {
        System.out.println("\n=== Список всех домов ===");

        HouseController houseController = Injector.getService(HouseController.class);
        List<House> houses = houseController.getAll();

        if (houses.isEmpty()) {
            System.out.println("Дома не найдены.");
        } else {
            for (House house : houses) {
                System.out.println(house);
            }
        }
        System.out.println();
    }

    private static void getEntityById(int id) {
        System.out.println("\n=== Поиск дома по ID ===");

        HouseController houseController = Injector.getService(HouseController.class);
        House house = houseController.getEntityById(id);

        if (house != null) {
            System.out.println(house);
        } else {
            System.out.println("Дом с ID " + id + " не найден.");
        }
        System.out.println();
    }
}

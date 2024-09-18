package BL.createHouse;

import util.DI.annotation.Component;

import java.sql.Date;
import java.util.Scanner;

@Component
public class HouseDataProvider {

    private final Scanner scanner = new Scanner(System.in);

    public int getId() {
        System.out.print("Enter house ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getAddress() {
        System.out.print("Enter house address: ");
        return scanner.nextLine();
    }

    public String getName() {
        System.out.print("Enter house name: ");
        return scanner.nextLine();
    }

    public Date getStartConstructionDate() {
        System.out.print("Enter start construction date (yyyy-mm-dd): ");
        return Date.valueOf(scanner.nextLine());
    }

    public Date getEndConstructionDate() {
        System.out.print("Enter end construction date (yyyy-mm-dd): ");
        return Date.valueOf(scanner.nextLine());
    }

    public Date getCommissioningDate() {
        System.out.print("Enter commissioning date (yyyy-mm-dd): ");
        return Date.valueOf(scanner.nextLine());
    }

}

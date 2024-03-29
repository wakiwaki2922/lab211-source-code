/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class CarManager {
    public static void main(String[] args) throws IOException {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("List all brands");
        ops.add("Add a new brand");
        ops.add("Search a brand based on its ID");
        ops.add("Update a brand");
        ops.add("Save brands to the file, named brands.txt");
        ops.add("List all cars in ascending order of brand names");
        ops.add("List cars based on a part of an input brand name");
        ops.add("Add a car");
        ops.add("Remove a car based on its ID");
        ops.add("Update a car based on its ID");
        ops.add("Save cars to file, named cars.txt");

        BrandList brandList = new BrandList();
        brandList.loadFromFile("brands.txt");

        CarList carList = new CarList(brandList);
        carList.loadFromFile("cars.txt");

        int choice;
        Menu m = new Menu();
        do {
            System.out.println("CAR MANAGER PROGRAM");
            choice = m.int_getChoice(ops);
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter brand ID to be searched: ");
                    String bID = sc.nextLine();
                    int pos = brandList.searchID(bID);
                    if (pos == -1) System.out.println("Not found!");
                    else System.out.println(brandList.bList.get(pos));
                    break;
                case 4:
                    brandList.updateBrand();
                    break;
                case 5:
                    brandList.saveToFile("brands.txt");
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    carList.printBasedBrandName();
                    break;
                case 8:
                    carList.addCar();
                    break;
                case 9:
                    carList.removeCar();
                    break;
                case 10:
                    carList.updateCar();
                    break;
                case 11:
                    carList.saveToFile("cars.txt");
                    break;
            }
            System.out.println();
        } while (choice > 0 && choice <= ops.size());
    }
}

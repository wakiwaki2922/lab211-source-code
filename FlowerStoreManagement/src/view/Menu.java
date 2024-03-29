/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.FlowerStoreFunction;
import controller.Validator;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class Menu {
    private static final Validator vl = new Validator();
    private FlowerStoreFunction f = new FlowerStoreFunction();
    public void mainMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o     Flower Store Manager    o");
        System.out.println("o-----------------------------o");
        System.out.println("     1. Flower menu");
        System.out.println("     2. Order menu");
        System.out.println("     3. Quit");
    }
    public void flowerMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o          Flower menu        o");
        System.out.println("o-----------------------------o");
        System.out.println("    1. Add a flower");
        System.out.println("    2. Find a flower");
        System.out.println("    3. Update a flower");
        System.out.println("    4. Delete a flower");
        System.out.println("    5. Display flower");
        System.out.println("    6. Back to main menu");
    }
    
    public void orderMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o          Order menu         o");
        System.out.println("o-----------------------------o");
        System.out.println("    1. Add an order");
        System.out.println("    2. Display orders");
        System.out.println("    3. Sort orders");
        System.out.println("    4. Save data");
        System.out.println("    5. Load data");
        System.out.println("    6. Back to main menu");
        System.out.println("    7. Find order by total value");
    }
    
    public void mainMenuChoice(int choice) throws ParseException, IOException{
        switch (choice) {
            case 1:
                flowerMenuChoice();
                break;
            case 2:
                orderMenuChoice();
                break;
            case 3:
                f.Quit();
                break;
            default:
                    System.out.println("Invalid choice. Please try again.");
        }
    }
    public void flowerMenuChoice()throws ParseException, IOException{
        while (true) {
            flowerMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    f.addFlower();
                    break;
                case 2:
                    f.findFlower("Enter ID or Name");
                    break;
                case 3:
                    f.updateFlower();
                    break;
                case 4:
                    f.deleteFlower();
                    break;
                case 5:
                    f.displayAllFlowers();
                    break;
                case 6:
                    mainMenu();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 3);
                    mainMenuChoice(tmpChoice);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void orderMenuChoice() throws ParseException, IOException{
        while (true) {
            orderMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 7);
            switch (choice) {
                case 1:
                    f.addOrder();
                    break;
                case 2:
                    f.displayOrders();
                    break;
                case 3:
                    f.sortOrders();
                    break;
                case 4:
                    f.saveData();
                    break;
                case 5:
                    f.loadData();
                    break;
                case 6:
                    mainMenu();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 3);
                    mainMenuChoice(tmpChoice);
                    break;
                case 7:
                    f.findOrder();
                    break;
            }
        }
    }
}

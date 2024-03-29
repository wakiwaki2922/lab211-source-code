/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.Scanner;

import controller.ShowroomFunction;
import controller.Validator;

/**
 *
 * @author kyrov
 */
public class Menu {

    private final Validator vl = new Validator();
    private ShowroomFunction sf = new ShowroomFunction();

    public void mainMenu() {
        String[] options = {
            "Add new vehicle",
            "Check exists vehicle",
            "Update vehicle",
            "Delete vehicle",
            "Search vehicle",
            "Display vehicle",
            "Load vehicle list",
            "Save vehicle list",
            "Print vehicle",
            "Quit"
        };

        vl.menuPrint(options, "Showroom Management Menu");
    }

    public void searchMenu() {
        String[] options = {
            "Search by Name",
            "Search by ID"
        };

        vl.menuPrint(options, "Search Menu");
    }

    public void displayMenu() {
        String[] options = {
            "Display all",
            "Display by price"
        };

        vl.menuPrint(options, "Display Menu");
    }

    public void printMenu() {
        String[] options = {
            "Print all",
            "Print by Year"
        };

        vl.menuPrint(options, "Print Menu");
    }

    public void mainMenuChoice() {
        while (true) {
            mainMenu();
            int choice = vl.inputInt("Enter you choice: ", 1, 10);
            switch (choice) {
                case 1:
                    sf.addVehicle();
                    break;
                case 2:
                    sf.checkVehicle();
                    break;
                case 3:
                    sf.updateVehicle();
                    break;
                case 4:
                    sf.deleteVehicle();
                    break;
                case 5:
                    searchMenuChoice();
                    break;
                case 6:
                    displayMenuChoice();
                    break;
                case 7:
                    sf.loadVehicleData();
                    break;
                case 8:
                    sf.saveVehicleData();
                    break;
                case 9:
                    printMenuChoice();
                    break;
                case 10:
                    sf.quit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void searchMenuChoice() {
        while (true) {
            searchMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 2);
            switch (choice) {
                case 1:
                    sf.searchVehicleByName();
                    break;
                case 2:
                    sf.searchVehicleByID();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            return;
        }
    }

    public void displayMenuChoice() {
        while (true) {
            displayMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 2);
            switch (choice) {
                case 1:
                    sf.displayAllVehicles();
                    ;
                    break;
                case 2:
                    sf.displayVehicleByPrice();
                    ;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            return;
        }
    }

    public void printMenuChoice() {
        while (true) {
            printMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 2);
            switch (choice) {
                case 1:
                    sf.printAllVehicles();
                    ;
                    break;
                case 2:
                    sf.printVehicleByYear();
                    ;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            return;
        }
    }
}

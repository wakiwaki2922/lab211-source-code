/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.StoreManagement;
import controller.Validator;

/**
 *
 * @author kyrov
 */
public class Menu {

    private final Validator vl = new Validator();
    private StoreManagement sm = new StoreManagement();
    
    public void loadData(){
        sm.loadProduct();
        sm.loadReceipt();
        sm.loadBill();
        sm.loadBsProduct();
    }

    public void mainMenu() {
        String[] options = {
            "Purchase Management",
            "Sale Management",
            "Exit"
        };

        vl.menuPrint(options, "Store Management System");
    }

    public void purchaseMenu() {
        String[] options = {
            "Add new purchase receipt",
            "Check storage",
            "Display nearly expired products",
            "Search for available products by name",
            "Display not available products",
            "Display products for specific quantity",
            "Update product",
            "Save file",
            "Back"
        };

        vl.menuPrint(options, "Purchase Management");
    }

    public void saleMenu() {
        String[] options = {
            "Add new bill of sale",
            "Display available products",
            "Save file",
            "Back"
        };

        vl.menuPrint(options, "Sale Management");
    }

    public void mainMenuChoice() {
        while (true) {
            mainMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 3, false);
            switch (choice) {
                case 1:
                    purchaseMenuChoice();
                    break;
                case 2:
                    saleMenuChoice();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }

    public void purchaseMenuChoice() {
        while (true) {
            purchaseMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 9, false);
            switch (choice) {
                case 1:
                    sm.purchaseProduct();
                    break;
                case 2:
                    sm.checkStorage();
                    break;
                case 3:
                    sm.checkExpirationProduct();
                    break;
                case 4:
                    sm.searchAvailable();
                    break;
                case 5:
                    sm.displayNotAvailable();
                    break;
                case 6:
                    sm.displaySpecific();
                    break;
                case 7:
                    sm.updateProduct();
                    break;
                case 8:
                    sm.saveProductListToFile();
                    sm.saveReceiptListToFile();
                    break;
                case 9:
                    mainMenuChoice();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public void saleMenuChoice() {
        while (true) {
            saleMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 4, false);
            switch (choice) {
                case 1:
                    sm.saleProduct();
                    break;
                case 2:
                    sm.displayAvailable();
                    break;
                case 3:
                    sm.saveProductListToFile();
                    sm.saveReceiptListToFile();
                    break;
                case 4:
                    mainMenuChoice();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}

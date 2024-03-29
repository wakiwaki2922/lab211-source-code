/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.HospitalFunction;
import controller.Validator;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class Menu {

    private static final Validator vl = new Validator();
    private HospitalFunction hs = new HospitalFunction();

    public void mainMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o     Hospital Management     o");
        System.out.println("o-----------------------------o");
        System.out.println("     1. Nurse menu");
        System.out.println("     2. Patient menu");
        System.out.println("     3. Quit");
    }

    public void nurseMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o          Nurse menu         o");
        System.out.println("o-----------------------------o");
        System.out.println("    1. Create a nurse");
        System.out.println("    2. Find a nurse");
        System.out.println("    3. Update a nurse");
        System.out.println("    4. Delete a nurse");
        System.out.println("    5. Display nurses");
        System.out.println("    6. Back to main menu");
    }

    public void patientMenu() {
        System.out.println("o-----------------------------o");
        System.out.println("o          Patient menu       o");
        System.out.println("o-----------------------------o");
        System.out.println("    1. Add a patient");
        System.out.println("    2. Display patients");
        System.out.println("    3. Sort patient list");
        System.out.println("    4. Save data");
        System.out.println("    5. Load data");
        System.out.println("    6. Display patients by age");
        System.out.println("    7. Back to main menu");

    }

    public void mainMenuChoice(int choice) throws ParseException{
        switch (choice) {
            case 1:
                nurseMenuChoice();
                break;
            case 2:
                patientMenuChoice();
                break;
            case 3:
                hs.quit();
                break;
            default:
                    System.out.println("Invalid choice. Please try again.");
        }
    }

    public void nurseMenuChoice()throws ParseException{
        while (true) {
            nurseMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    hs.addNurse();
                    break;
                case 2:
                    hs.findNurse();
                    break;
                case 3:
                    hs.updateNurse();
                    break;
                case 4:
                    hs.deleteNurse();
                    break;
                case 5:
                    hs.displayNurse();
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

    public void patientMenuChoice() throws ParseException{
        while (true) {
            patientMenu();
            int choice = vl.inputInt("Enter your choice: ", 1, 7);
            switch (choice) {
                case 1:
                    hs.addPatient();
                    break;
                case 2:
                    hs.displayPatient();
                    break;
                case 3:
                    hs.sortPatient();
                    break;
                case 4:
                    hs.saveData();
                    break;
                case 5:
                    hs.loadData();
                    break;
                case 6:
                    hs.displayPatientsea();
                case 7:
                    mainMenu();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 3);
                    mainMenuChoice(tmpChoice);
                    break;
            }
        }
    }
}

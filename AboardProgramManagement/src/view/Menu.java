/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.AboardProgramFunction;
import controller.Register;
import controller.Report;
import controller.StudentFunction;
import controller.Validator;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class Menu {

    private Validator vl = new Validator();
    private AboardProgramFunction apf = new AboardProgramFunction();
    private StudentFunction sf = new StudentFunction();
    private Register rg = new Register();
    private Report rp = new Report();

    public void mainView() {
        System.out.println("o-----------------------------------o");
        System.out.println("o     Aboard Programs Management    o");
        System.out.println("o-----------------------------------o");
        System.out.println("     1. Manage aboard programs");
        System.out.println("     2. Manage students");
        System.out.println("     3. Rgister");
        System.out.println("     4. Report");
        System.out.println("     5. Exist");
    }

    public void abView() {
        System.out.println("o-----------------------------o");
        System.out.println("o     Aboard Program Menu     o");
        System.out.println("o-----------------------------o");
        System.out.println("     1. Display all");
        System.out.println("     2. Add new");
        System.out.println("     3. Edit by ID");
        System.out.println("     4. Search and display by ID");
        System.out.println("     5. Back to main menu");
    }

    public void sView() {
        System.out.println("o-----------------------------o");
        System.out.println("o         Student Menu        o");
        System.out.println("o-----------------------------o");
        System.out.println("     1. Display all");
        System.out.println("     2. Add new");
        System.out.println("     3. Edit by ID");
        System.out.println("     4. Back to main menu");
    }

    public void rpView() {
        System.out.println("o---------------------------------------------o");
        System.out.println("o                  Report Menu                o");
        System.out.println("o---------------------------------------------o");
        System.out.println("     1. Print out registration by student ID");
        System.out.println("     2. Print out student registerd more than 2");
        System.out.println("     3. Count students by Program ID");
        System.out.println("     4. Back to main menu");
    }

    public void mainMenuChoice(int choice) throws ParseException, IOException {
        switch (choice) {
            case 1:
                abMenuChoice();
                break;
            case 2:
                sMenuChoice();
                break;
            case 3:
                rg.registerS();
                break;
            case 4:
                rpMenuChoice();
                break;
            case 5:
                existFunction();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void abMenuChoice() throws ParseException, IOException {
        while (true) {
            abView();
            int choice = vl.inputInt("Enter your choice: ", 1, 5);
            switch (choice) {
                case 1:
                    apf.displayAll();
                    break;
                case 2:
                    apf.add();
                    break;
                case 3:
                    apf.edit();
                    break;
                case 4:
                    apf.displayByID();
                    break;
                case 5:
                    mainView();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 5);
                    mainMenuChoice(tmpChoice);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void sMenuChoice() throws ParseException, IOException {
        while (true) {
            sView();
            int choice = vl.inputInt("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1:
                    sf.displayAll();
                    break;
                case 2:
                    sf.add();
                    break;
                case 3:
                    sf.edit();
                    break;
                case 4:
                    mainView();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 5);
                    mainMenuChoice(tmpChoice);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void rpMenuChoice() throws ParseException, IOException {
        while (true) {
            rpView();
            int choice = vl.inputInt("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1:
                    rp.displayRegBySID();
                    break;
                case 2:
                    rp.displayStudentReg();
                    break;
                case 3:
                    rp.countStudentByPID();
                    break;
                case 4:
                    mainView();
                    int tmpChoice = vl.inputInt("Enter your choice: ", 1, 5);
                    mainMenuChoice(tmpChoice);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void existFunction() throws IOException{
        sf.saveStudents();
        apf.saveAboardPrograms();
        System.out.println("End");
        System.exit(0);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.AboardProgramFunction;
import controller.StudentFunction;
import controller.Validator;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class main {

    private final static Validator vl = new Validator();

    public static void main(String[] args) throws ParseException, IOException {
        AboardProgramFunction apf;
        StudentFunction sf;
        apf = new AboardProgramFunction("AboardPrograms.txt");
        sf = new StudentFunction("Students.txt");
        Menu menu = new Menu();

        menu.mainView();
        int choice = vl.inputInt("Enter you choice: ", 1, 3);

        menu.mainMenuChoice(choice);
    }
}

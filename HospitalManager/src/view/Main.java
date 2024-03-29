/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Validator;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class Main {
    public static final Validator vl = new Validator();
    public static void main(String[] args) throws ParseException{
        Menu menu =new Menu();
        menu.mainMenu();
        int choice = vl.inputInt("Enter you choice: ", 1, 3);
        menu.mainMenuChoice(choice);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.StoreManagement;
import controller.Validator;
import java.text.ParseException;

/**
 *
 * @author kyrov
 */
public class Main {
    public static final Validator vl = new Validator();
    public static final StoreManagement sm = new StoreManagement();
    public static void main(String[] args) throws ParseException{
        Menu menu =new Menu();
        menu.loadData();
        menu.mainMenuChoice();
    }        
}

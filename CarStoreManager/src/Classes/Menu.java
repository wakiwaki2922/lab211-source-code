/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author giang
 * @param <E>
 */
public class Menu<E> {
    public int int_getChoice(ArrayList<E> options) {
        Scanner sc = new Scanner(System.in);
        int response;
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i+1) + ". " + options.get(i));
        }
        System.out.print("Please choose an option 1..N: ");
        response = sc.nextInt();
        return response;
    }
    public E ref_getChoice(ArrayList<E> options) {
        int response, n = options.size();
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > n);
        
        return options.get(response - 1);
    }
    
}

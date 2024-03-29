/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import controller.Validator;

/**
 *
 * @author kyrov
 */
public class BillOfSale {
    private String bsID;
    private String bsDate;
    private ArrayList<BsProduct> bsProductList = new ArrayList<>();

    public BillOfSale() {
    }

    public BillOfSale(String bsID, String bsDate) {
        this.bsID = bsID;
        this.bsDate = bsDate;
    }

    public String getBsID() {
        return bsID;
    }

    public void setBsID(String bsID) {
        this.bsID = bsID;
    }

    public String getBsDate() {
        return bsDate;
    }

    public void setBsDate(String bsDate) {
        this.bsDate = bsDate;
    }

    public ArrayList<BsProduct> getBsProductList() {
        return bsProductList;
    }
    public ArrayList<BsProduct> setBsProductList(ArrayList<BsProduct> bsProductList) {
        return this.bsProductList = bsProductList;
    }
    
    public ArrayList<BsProduct> inputBsProducts(HashMap<String, Product> productList) {
        Scanner scanner = new Scanner(System.in);
        Validator vl = new Validator();
        System.out.println("Adding products to bill of sale...");

        int numberOfProducts = vl.inputInt("Enter the number of products to add to the bill of sale: ", 1,
                Integer.MAX_VALUE, false);

        for (int i = 0; i < numberOfProducts; i++) {
            String pID;
            double bsPrice;
            int bsQuantity;

            do {
                pID = vl.inputString("Enter the Product ID (Format: PXXXXXX): ", false);
                if (!productList.containsKey(pID)) {
                    System.out.println("Invalid Product ID. Please make sure it exists.");
                } else if (productList.get(pID).getCurQuantity() <= 0) {
                    System.out.println("This product is not available in stock.");
                }
            } while (!productList.containsKey(pID) || productList.get(pID).getCurQuantity() <= 0);

            do {
                bsPrice = vl.inputDouble("Enter the price: ",
                        productList.get(pID).getPurchasePrice(), Double.MAX_VALUE, false);
                if (bsPrice < productList.get(pID).getPurchasePrice()) {
                    System.out.println("Selling price must be greater than or equal to purchase price.");
                }
            } while (bsPrice < productList.get(pID).getPurchasePrice());

            do {
                bsQuantity = vl.inputInt(
                        "Enter the Quantity (<= Current Stock " + productList.get(pID).getCurQuantity() + "): ", 1,
                        productList.get(pID).getCurQuantity(), false);
                if (bsQuantity > productList.get(pID).getCurQuantity()) {
                    System.out.println("Quantity cannot exceed current stock.");
                }
            } while (bsQuantity > productList.get(pID).getCurQuantity());

            Product product = productList.get(pID);
            product.setCurQuantity(product.getCurQuantity() - bsQuantity);
            BsProduct bsProduct = new BsProduct(bsID, pID, bsPrice, bsQuantity);
            bsProductList.add(bsProduct);
            System.out.println("Products added to bill of sale successfully.");
        }
        scanner.close();
        return bsProductList;
    }

}

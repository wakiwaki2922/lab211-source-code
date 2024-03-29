/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kyrov
 */
public class BsProduct {
    private String bsID;
    private String pID;
    private double bsPrice;
    private int bsQuantity;

    public BsProduct(String bsID, String pID, double bsPrice, int bsQuantity) {
        this.bsID = bsID;
        this.pID = pID;
        this.bsPrice = bsPrice;
        this.bsQuantity = bsQuantity;
    }

    // Getter và Setter cho các thuộc tính

    public String getBsID() {
        return bsID;
    }

    public void setBsID(String bsID) {
        this.bsID = bsID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public double getBsPrice() {
        return bsPrice;
    }

    public void setBsPrice(double bsPrice) {
        this.bsPrice = bsPrice;
    }

    public int getBsQuantity() {
        return bsQuantity;
    }

    public void setBsQuantity(int bsQuantity) {
        this.bsQuantity = bsQuantity;
    }

    
}

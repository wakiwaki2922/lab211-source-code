package model;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kyrov
 */
public class Product {

    private String pID;
    private String name;
    private double purchasePrice;
    private int curQuantity;
    private String proDate;
    private String expDate;
    private String receipt;

    public Product() {
    }

    public Product(String pID, String name, double purchasePrice, int curQuantity, String proDate, String expDate, String receipt) {
        this.pID = pID;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.curQuantity = curQuantity;
        this.proDate = proDate;
        this.expDate = expDate;
        this.receipt = receipt;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getCurQuantity() {
        return curQuantity;
    }

    public void setCurQuantity(int curQuantity) {
        this.curQuantity = curQuantity;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }


}

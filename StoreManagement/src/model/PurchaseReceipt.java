/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kyrov
 */
public class PurchaseReceipt {
    private String prID;
    private String prDate;

    public PurchaseReceipt() {
    }

    public PurchaseReceipt(String prID, String prDate) {
        this.prID = prID;
        this.prDate = prDate;
    }

    public String getPrID() {
        return prID;
    }

    public void setPrID(String prID) {
        this.prID = prID;
    }

    public String getPrDate() {
        return prDate;
    }

    public void setPrDate(String prDate) {
        this.prDate = prDate;
    }

    
}

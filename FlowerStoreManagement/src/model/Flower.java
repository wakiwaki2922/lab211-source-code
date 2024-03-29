/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author kyrov
 */
public class Flower implements Serializable{
    private String id;
    private String description;
    private String date;
    private double unitPrice;
    private String category;

    public Flower(String id, String description, String date, double unitPrice, String category) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Flower{" + "id=" + id + ", description=" + description + ", date=" + date + ", unitPrice=" + unitPrice + ", category=" + category + '}';
    }
    
    
}

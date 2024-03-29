/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author kyrov
 */
public class AboardProgram {

    private String id;
    private String name;
    private String time;
    private String startRegDate;
    private String endRegDate;
    private int days;
    private String location;
    private double cost;
    private String content;
    
    public AboardProgram(String id, String name, String time, String startRegDate, String endRegDate, int days, String location, double cost, String content) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.startRegDate = startRegDate;
        this.endRegDate = endRegDate;
        this.days = days;
        this.location = location;
        this.cost = cost;
        this.content = content;
    }

    public AboardProgram() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartRegDate() {
        return startRegDate;
    }

    public void setStartRegDate(String startRegDate) {
        this.startRegDate = startRegDate;
    }

    public String getEndRegDate() {
        return endRegDate;
    }

    public void setEndRegDate(String endRegDate) {
        this.endRegDate = endRegDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void display() {
    System.out.println("Details of the study programs ");
    System.out.println("0. ID and Name: " + getId() + "-" + getName());
    System.out.println("1. Time: " + getTime());
    System.out.println("2. Day: " + getDays());
    System.out.println("3. Location: "+ getLocation());
    System.out.println("4. Cost: " + getCost());
    System.out.println("5. Content: " + getContent());
}
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kyrov
 */
public class Vehicle {

    private String vehicleID;
    private String vehicleName;
    private String vehileColor;
    private double vehiclePrice;
    private String vehicleBrand;
    private String type;
    private int productYear;

    public Vehicle() {
    }

    public Vehicle(String vehicleID, String vehicleName, String vehileColor, double vehiclePrice, String vehicleBrand, String type, int productYear) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.vehileColor = vehileColor;
        this.vehiclePrice = vehiclePrice;
        this.vehicleBrand = vehicleBrand;
        this.type = type;
        this.productYear = productYear;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleColor() {
        return vehileColor;
    }

    public void setVehicleColor(String vehileColor) {
        this.vehileColor = vehileColor;
    }

    public double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

}

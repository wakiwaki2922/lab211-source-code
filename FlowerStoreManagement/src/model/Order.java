/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.FlowerStoreFunction;
import controller.Validator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author kyrov
 */
public class Order implements Serializable {

    private String orderID;
    private String orderDate;
    private String customerName;
    ArrayList<OrderDetail> orderdetail = new ArrayList<>();
    private Double orderTotal = 0.0;

    public Order() {
    }

    public Order(String orderID, String orderDate, String customerName) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<OrderDetail> getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(ArrayList<OrderDetail> orderdetail) {
        this.orderdetail = orderdetail;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", orderDate=" + orderDate + ", customerName=" + customerName + '}';
    }

    public ArrayList<OrderDetail> inputOrderDetail(Set<Flower> flowerSet) {
        Scanner scanner = new Scanner(System.in);
        Validator vl = new Validator();
        System.out.println("Adding order details:");

        int orderDetailId = 1;
        String continueAdd;

        do {
            Flower flower = null;
            String flowerId;
            int quantity;

            // Input Flower ID
            do {
                flower = vl.findFlower(flowerSet, "Enter the Flower ID (Format: FXXX): ");
                if (flower == null) {
                    System.out.println("Invalid Flower ID. Please make sure it exists.");
                }
            } while (flower == null);
            flowerId = flower.getId();

            // Input quantity
            System.out.print("Quantity: ");
            while (true) {
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity <= 0) {
                        System.out.println("Quantity must be a positive integer.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                }
            }
            double flowerCost = quantity * flower.getUnitPrice();

            OrderDetail orderDetail = new OrderDetail(orderDetailId, flowerId, quantity, flowerCost);
            orderdetail.add(orderDetail);

            orderDetailId++;

            continueAdd = vl.inputYN("Do you want to add another order detail? (Y/N): ");
        } while (continueAdd.equalsIgnoreCase("Y"));

        System.out.println("Order details added successfully.");
        return orderdetail;
    }

    public void totalCal() {
        for (OrderDetail orderDetail : orderdetail) {
            orderTotal += orderDetail.getPrice();
        }
    }
}

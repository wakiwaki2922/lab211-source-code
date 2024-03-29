/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Flower;
import model.Order;
import model.OrderDetail;

/**
 *
 * @author kyrov
 */
public class FlowerStoreFunction {

    private final Validator vl = new Validator();
    private Set<Flower> flowerList = new HashSet<>();
    private Set<Order> orderList = new HashSet<>();
    private final Scanner sc = new Scanner(System.in);
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private boolean dataChanged = false;

    public FlowerStoreFunction() {

    }

    //Function 1:Add a flower
    public void addFlower() {
        while (true) {
            // input data flower
            String id = vl.inputID(flowerList, "Enter id: ");
            String description = vl.inputDescription("Enter description: ");
            String date = vl.inputDate("Enter date: ");
            String category = vl.inputString("Enter category: ");
            double unitPrice = vl.inputDouble("Enter unit price: ", 0, 1000000);

            Flower newFlower = new Flower(id, description, date, unitPrice, category);

            flowerList.add(newFlower);

            // asking user to want adding a new flower 
            if (vl.inputString("Do you want adding a new flower ( enter `Y` ) or back to the main menu ( enter `N` )").equalsIgnoreCase("y")) {
                continue;
            } else {
                break;
            }
        }
        // set changed value 
        dataChanged = true;
    }

    //Function 2:Find a flower
    public Flower findFlower(String msg) {
        // Check if field is ID or name
        String findField = vl.inputString(msg);
        boolean exists = false;

        for (Flower fl : flowerList) {
            if (fl.getId().equals(findField) || fl.getDescription().equals(findField)) {
                System.out.println(fl);
                exists = true;
                return fl;
            }
        }
        if (!exists) {
            System.out.println("The flower does not exist");
        }
        return null;
    }

    //Function 3:Update a flower
    public void updateFlower() {
        displayAllFlowers();
        do {
            Flower fl = findFlower("Enter flower name to update: ");
            if (fl == null) {

            } else {

                // Input new data flower
                String description = vl.inputDescriptionU("Enter new description: ");
                String date = vl.inputDateU("Enter new date: ");
                System.out.println("Enter new category: ");
                String category = sc.nextLine();
                double unitPrice = vl.inputDoubleU("Enter new unit price: ", 0, 100000000);

                // Keep the old values if the user does not enter anything
                if (!description.isEmpty()) {
                    fl.setDescription(description);
                    dataChanged = true;
                }
                if (!date.isEmpty()) {
                    fl.setDate(date);
                    dataChanged = true;
                }
                if (!category.isEmpty()) {
                    fl.setCategory(category);
                    dataChanged = true;
                }
                if (unitPrice != 0) {
                    fl.setUnitPrice(unitPrice);
                    dataChanged = true;
                }
                System.out.println("Success");
                break;
            }
        } while (true);
    }

    //Function 4:Delete flower
    public void deleteFlower() {
        displayAllFlowers();
        Flower fl = findFlower("Enter the flower ID to delete: ");
        if (fl == null) {
            System.out.println("The flower does not exist.");
            return;
        }

        // Check if the flower exists in any order detail
        for (Order order : orderList) {
            ArrayList<OrderDetail> orderdetail = order.getOrderdetail();
            for (OrderDetail orderDetail : orderdetail) {
                if (orderDetail.getFlowerID().equals(fl.getId())) {
                    System.out.println("The flower cannot be deleted as it exists in an order detail.");
                    return;
                }
            }
        }

        // Confirmation message before deleting
        System.out.println("Are you sure you want to delete the flower with ID " + fl.getId() + "? (Y/N)");
        String confirmation = vl.inputString("");
        if (confirmation.equalsIgnoreCase("Y")) {
            flowerList.remove(fl);
            dataChanged = true;
            System.out.println("The flower has been successfully deleted.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    //Function 4.1:Display flower
    public void displayFlower(Flower flower) {
        if (flower != null) {
            System.out.printf("%-20s %-20s %-30s %-20s %-10s\n", flower.getId(), flower.getCategory(), flower.getDescription(), flower.getDate(), flower.getUnitPrice());
        } else {
            System.out.println("Flower not found.");
        }
    }

    public void displayAllFlowers() {
        if (flowerList.isEmpty()) {
            System.out.println("No flowers found.");
        } else {
            System.out.println("Flower List:");
            System.out.printf("%-20s %-20s %-30s %-20s %-10s\n", "ID", "Category", "Description", "Import Date", "Unit Price");
            for (Flower flower : flowerList) {
                displayFlower(flower);
            }
        }
    }

    //Function 5:Add an order
    public void addOrder() {
        System.out.println("Adding an order:");

        // Order header
        String orderID = vl.inputOID(orderList, "Ener Order ID: ");
        String orderDate = vl.inputDate("Order Date (dd/MM/yyyy): ");
        System.out.print("Customer's Name: ");
        String customerName = sc.nextLine();
        Order od = new Order(orderID, orderDate, customerName);
        // Order details
        displayAllFlowers();
        od.inputOrderDetail(flowerList);
        od.totalCal();
        System.out.println("Order added successfully.");
        orderList.add(od);
        dataChanged = true;
    }

    //Function 6:Display order
    public void displayOrders() throws ParseException {
        String sdate = vl.inputDate("Enter start date: ");
        String edate = vl.inputDateAfter("Enter end date: ", sdate);
        System.out.println("LIST OF ORDERS FROM " + sdate + " TO " + edate);
        System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15s\n", "No.", "Order ID", "Order Date", "Customer", "Flower Count", "Order Total");

        int count = 1;
        int totalFlowerCount = 0;
        double totalOrderTotal = 0.0;

        for (Order order : orderList) {
            if (vl.dateCompare(order.getOrderDate(), sdate, edate)) {
                ArrayList<OrderDetail> orderDetails = order.getOrderdetail();

                String orderId = order.getOrderID();
                String orderDate = order.getOrderDate();
                String customer = order.getCustomerName();
                int flowerCount = 0;

                for (OrderDetail orderDetail : orderDetails) {
                    int quantity = orderDetail.getQuantity();
                    flowerCount += quantity;
                }

                double orderTotal = 0.0;
                for (OrderDetail orderDetail : orderDetails) {
                    orderTotal += orderDetail.getPrice();
                }

                totalFlowerCount += flowerCount;
                totalOrderTotal += orderTotal;

                System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15.2f\n", count, orderId, orderDate, customer, flowerCount, orderTotal);
                count++;
            }
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-63s%-15s%-15.2f\n", "Total:", totalFlowerCount, totalOrderTotal);
    }

    //Function 7:Sort order
    public void sortOrders() {
        String sortField;
        String sortOrder;
        while (true) {
            sortField = vl.inputString("Enter sort field (ID/Date/Name/Total): ");
            if (sortField.equalsIgnoreCase("ID") || sortField.equalsIgnoreCase("Date") || sortField.equalsIgnoreCase("Name") || sortField.equalsIgnoreCase("Total")) {
                break;
            } else {
                System.out.println("Invalid sort field. Please enter 'ID', 'Date', 'Name', or 'Total'.");
            }
        }
        while (true) {
            sortOrder = vl.inputString("Enter sort order (ASC/DESC): ");
            if (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC")) {
                break;
            } else {
                System.out.println("Invalid sort order. Please enter 'ASC' or 'DESC'. ");
            }
        }
        ArrayList<Order> sortedOrders = new ArrayList<>(orderList);

//        // Tính tổng giá trị và thêm vào danh sách orderTotals
//        for (Order order : orderList) {
//            double orderTotal = 0;
//            for (OrderDetail orderDetail : order.getOrderdetail()) {
//                orderTotal += orderDetail.getPrice();
//            }
//            orderTotals.add(orderTotal);
//        }
        if (sortField.equalsIgnoreCase("ID")) {
            sortedOrders.sort(Comparator.comparing(Order::getOrderID));
        } else if (sortField.equalsIgnoreCase("Date")) {
            Collections.sort(sortedOrders, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    try {
                        Date date1 = sdf.parse(o1.getOrderDate());
                        Date date2 = sdf.parse(o2.getOrderDate());

                        if (date1.equals(date2)) {
                            return 0;
                        } else if (date1.before(date2)) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } catch (ParseException e) {
                        System.out.println("Error!");
                    }
                    return 0;
                }
            });
        } else if (sortField.equalsIgnoreCase("Name")) {
            sortedOrders.sort(Comparator.comparing(Order::getCustomerName));
        } else if (sortField.equalsIgnoreCase("Total")) {
//            sortedOrders.sort(Comparator.comparingDouble(Order::getOrderTotal));
            Collections.sort(sortedOrders, new Comparator<Order>() {

                @Override
                public int compare(Order o1, Order o2) {
                    double totalValue1 = 0;
                    double totalValue2 = 0;
                    for (OrderDetail orderDetail : o1.getOrderdetail()) {
                        totalValue1 += orderDetail.getPrice();
                    }
                    for (OrderDetail orderDetail : o2.getOrderdetail()) {
                        totalValue2 += orderDetail.getPrice();
                    }
                    return Double.compare(totalValue1, totalValue2);
                }
            });
        }
        if (sortOrder.equalsIgnoreCase("DESC")) {
            Collections.reverse(sortedOrders);
        }
        System.out.println("LIST OF ORDERS :");
        System.out.println("Sorted by: " + sortField);
        System.out.println("Sort order : " + sortOrder);
        System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15s\n", "No.", "Order ID", "Order Date", "Customer", "Flower Count", "Order Total");

        int count = 1;
        int totalFlowerCount = 0;
        double totalOrderTotal = 0.0;

        for (Order order : sortedOrders) {
            ArrayList<OrderDetail> orderDetails = order.getOrderdetail();

            String orderId = order.getOrderID();
            String orderDate = order.getOrderDate();
            String customer = order.getCustomerName();
            int flowerCount = 0;

            for (OrderDetail orderDetail : orderDetails) {
                int quantity = orderDetail.getQuantity();
                flowerCount += quantity;
            }

            double orderTotal = 0.0;
            for (OrderDetail orderDetail : orderDetails) {
                orderTotal += orderDetail.getPrice();
            }

            totalFlowerCount += flowerCount;
            totalOrderTotal += orderTotal;

            System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15.2f\n", count, orderId, orderDate, customer, flowerCount, orderTotal);
            count++;
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-63s%-15s%-15.2f\n", "Total:", totalFlowerCount, totalOrderTotal);
    }
    //Function 8:Save data

    public void saveData() {
        if (vl.checkFileExistence("Flower.txt")) {
            try (BufferedWriter flowerWriter = new BufferedWriter(new FileWriter("Flower.txt"))) {
                for (Flower flower : flowerList) {
                    String line = flower.getId() + "," + flower.getDescription() + "," + flower.getDate() + ","
                            + flower.getUnitPrice() + "," + flower.getCategory();
                    flowerWriter.write(line);
                    flowerWriter.newLine();
                }
                flowerWriter.close();
                System.out.println("Flower data save successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving Flower data.");
            }
        }
        if (vl.checkFileExistence("Order.txt")) {
            try (BufferedWriter orderWriter = new BufferedWriter(new FileWriter("Order.txt"))) {
                for (Order order : orderList) {
                    String orderLine = order.getOrderID() + "," + order.getOrderDate() + "," + order.getCustomerName() + ","
                            + order.getOrderdetail().size();
                    orderWriter.write(orderLine);
                    orderWriter.newLine();

                    for (OrderDetail detail : order.getOrderdetail()) {
                        String detailLine = detail.getOrderDetailID() + "," + detail.getFlowerID() + "," + detail.getQuantity() + "," + detail.getPrice();
                        orderWriter.write(detailLine);
                        orderWriter.newLine();
                    }
                }
                orderWriter.close();
                System.out.println("Order data save successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving Order data.");
            }
        }
    }

    //Function 9:Load data
    public void loadData() throws FileNotFoundException, IOException {
        File flowerFile = new File("Flower.txt");
        if (!flowerFile.exists()) {
            throw new FileNotFoundException("Flower file does not exist.");
        }

        File orderFile = new File("Order.txt");
        if (!orderFile.exists()) {
            throw new FileNotFoundException("Order file does not exist.");
        }

        try (BufferedReader flowerReader = new BufferedReader(new FileReader(flowerFile))) {
            String line;
            while ((line = flowerReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String id = data[0];
                    String description = data[1];
                    String date = data[2];
                    double unitPrice = Double.parseDouble(data[3]);
                    String category = data[4];

                    Flower flower = new Flower(id, description, date, unitPrice, category);
                    flowerList.add(flower);
                }
            }
            flowerReader.close();
            System.out.println("Flower data loaded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Flower data.");
        }

        try (BufferedReader orderReader = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = orderReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String orderId = data[0];
                    String orderDate = data[1];
                    String customerName = data[2];
                    int orderDetailSize = Integer.parseInt(data[3]);

                    Order order = new Order(orderId, orderDate, customerName);

                    for (int i = 0; i < orderDetailSize; i++) {
                        line = orderReader.readLine();
                        String[] detailData = line.split(",");
                        if (detailData.length == 4) {
                            int detailId = Integer.parseInt(detailData[0]);
                            String flowerId = detailData[1];
                            int quantity = Integer.parseInt(detailData[2]);
                            double price = Double.parseDouble(detailData[3]);

                            OrderDetail detail = new OrderDetail(detailId, flowerId, quantity, price);
                            order.getOrderdetail().add(detail);
                        }
                    }
                    order.totalCal();
                    orderList.add(order);
                }
            }
            orderReader.close();
            System.out.println("Order data loaded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Order data.");
        }
    }

    //Function 10:Quit
    public void Quit() {
        String exitChoice = vl.inputYN("Do you want to exit the program?(Y/N)");
        if (exitChoice.equalsIgnoreCase("Y")) {
            if (dataChanged) {
                String choice = vl.inputYN("Detected data change,do you want to save the data before quitting? (Y/N)");
                if (choice.equalsIgnoreCase("Y")) {
                    saveData();
                }
            }
            System.out.println("Exiting the program. Goodbye!");
            System.exit(0);
        }
    }

    //Function : Find Order by total
    public void findOrder() {
        System.out.println("Enter order total value: ");
        Double odTotal = sc.nextDouble();
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order : orderList) {
            double orderTotal = 0;
            ArrayList<OrderDetail> orderDetails = order.getOrderdetail();
            for (OrderDetail orderDetail : orderDetails) {
                orderTotal += orderDetail.getPrice();
            }
            if (odTotal == orderTotal) {
                orders.add(order);
            }
        }
        System.out.println("LIST OF ORDERS ");
        System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15s\n", "No.", "Order ID", "Order Date", "Customer", "Flower Count", "Order Total");

        int count = 1;
        int totalFlowerCount = 0;
        double totalOrderTotal = 0.0;

        for (Order order : orders) {
            ArrayList<OrderDetail> orderDetails = order.getOrderdetail();

            String orderId = order.getOrderID();
            String orderDate = order.getOrderDate();
            String customer = order.getCustomerName();
            int flowerCount = 0;

            for (OrderDetail orderDetail : orderDetails) {
                int quantity = orderDetail.getQuantity();
                flowerCount += quantity;
            }

            double orderTotal = 0.0;
            for (OrderDetail orderDetail : orderDetails) {
                orderTotal += orderDetail.getPrice();
            }

            totalFlowerCount += flowerCount;
            totalOrderTotal += orderTotal;

            System.out.printf("%-3s%-15s%-15s%-30s%-15s%-15.2f\n", count, orderId, orderDate, customer, flowerCount, orderTotal);
            count++;
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-63s%-15s%-15.2f\n", "Total:", totalFlowerCount, totalOrderTotal);
    }
}

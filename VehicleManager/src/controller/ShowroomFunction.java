/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Vehicle;

/**
 *
 * @author kyrov
 */
public class ShowroomFunction {

    private HashMap<String, Vehicle> vehicleList = new HashMap<>();

    private final Validator vl = new Validator();
    private Boolean dataChanged = false;

    // Function 1: Add new vehicle
    public void addVehicle() {
        while (true) {
            String vehicleID = vl.inputVehicleID(vehicleList, "Enter vehicle ID (in the format VXXXXX): ");
            String vehicleName = vl.inputString("Enter vehicle name: ",false);
            String vehicleColor = vl.inputString("Enter vehicle color: ",false);
            double vehiclePrice = vl.inputDouble("Enter vehicle price: ", 0.0, Double.MAX_VALUE,false);
            String vehicleBrand = vl.inputString("Enter vehicle brand: ",false);
            String type = vl.inputString("Enter vehicle type: ",false);
            int productYear = vl.inputYear("Enter vehicle product year: ",false);
            Vehicle vehicle = new Vehicle(vehicleID, vehicleName, vehicleColor, vehiclePrice, vehicleBrand, type,
                    productYear);
            vehicleList.put(vehicleID, vehicle);

            System.out.println("Vehicle added successfully!");
            dataChanged = true;
            String continueInput = vl.inputYN("Do you want to continue adding vehicles? (Y/N): ");
            if (continueInput.equalsIgnoreCase("N")) {
                break; // Exit the loop if the user doesn't want to continue.
            }
        }
    }

    // Function 2: Check exist Vehicle
    public void checkVehicle() {
        String vehicleID = vl.inputVehicleID("Enter vehicle ID (in the format VXXXXX): ");
        if (vehicleList.containsKey(vehicleID)) {
            System.out.println("Existed Vehicle");
        } else {
            System.out.println("No Vehicle Found!");
        }
    }

    // Function extend: Search for vehicle by id and return vehicle
    public Vehicle searchVehicleById(HashMap<String, Vehicle> vehicleList, String vehicleID) {
        if (vehicleList.containsKey(vehicleID)) {
            return vehicleList.get(vehicleID);
        } else {
            return null;
        }
    }

    // Function 3: Update a vehicle
    public void updateVehicle() {
        Scanner scanner = new Scanner(System.in);
        String vehicleID = null;
        Vehicle vehicle = null;

        while (vehicle == null) {
            vehicleID = vl.inputVehicleID("Enter the vehicle ID: ");
            vehicle = searchVehicleById(vehicleList, vehicleID);
            if (vehicle == null) {
                System.out.println("The vehicle does not exist. Please try again.");
            }
        }
        System.out.println("Enter new information for the vehicle:");

        // Input vehicle name
        String name = vl.inputString("Name (" + vehicle.getVehicleName() + "): ",true);
        if (!name.isEmpty()) {
            vehicle.setVehicleName(name);
            dataChanged = true;
        }

        // Input vehicle color
        String color = vl.inputString("Color (" + vehicle.getVehicleColor() + "): ",true);
        if (!color.isEmpty()) {
            vehicle.setVehicleColor(color);
            dataChanged = true;
        }

        // Input vehicle price
        double price = vl.inputDouble("Price (" + vehicle.getVehiclePrice() + "): ", 0, Double.MAX_VALUE,true);
        if (price != vehicle.getVehiclePrice() && price != 0) {
            vehicle.setVehiclePrice(price);
            dataChanged = true;
        }

        // Input vehicle brand
        String brand = vl.inputString("Brand (" + vehicle.getVehicleBrand() + "): ",true);
        if (!brand.isEmpty()) {
            vehicle.setVehicleBrand(brand);
            dataChanged = true;
        }

        // Input vehicle type
        String type = vl.inputString("Type (" + vehicle.getType() + "): ",true);
        if (!type.isEmpty()) {
            vehicle.setType(type);
            dataChanged = true;
        }

        // Input vehicle product year
        int productYear = vl.inputYear("Product Year (" + vehicle.getProductYear() + "): ",true);
        if (productYear != vehicle.getProductYear() && productYear != 0) {
            vehicle.setProductYear(productYear);
            dataChanged = true;
        }

        System.out.println("Vehicle information updated successfully.");
        System.out.println("\nVEHICLES AFTER UPDATE");
        System.out.printf("%-12s%-16s%-10s%-12s%-15s%-15s\n", "Vehicle ID", "Name", "Color", "Price", "Brand",
                "Product Year");

        System.out.printf("%-12s%-16s%-10s%-12.2f%-15s%-15d\n", vehicle.getVehicleID(), vehicle.getVehicleName(),
                vehicle.getVehicleColor(), vehicle.getVehiclePrice(), vehicle.getVehicleBrand(),
                vehicle.getProductYear());

    }

    // Function 4: Delete a vehicle
    public void deleteVehicle() {
        String vehicleID = vl.inputVehicleID("Enter vehicle ID (in the format VXXXXX): ");
        if (vehicleList.containsKey(vehicleID)) {
            vehicleList.remove(vehicleID);
            System.out.println("Vehicle deleted successfully!");
        } else {
            System.out.println("No Vehicle Found!");
        }
    }

    // Function extend: display all vehicle from an arraylist
    public void displayVehicle(ArrayList<Vehicle> vList) {
        System.out.println("\nVEHICLES");
        System.out.printf("%-12s%-20s%-10s%-12s%-15s%-15s%-15s\n", "Vehicle ID", "Name", "Color", "Price", "Brand","Type",
                "Product Year");

        for (Vehicle vehicle : vList) {
            System.out.printf("%-12s%-20s%-10s%-12.2f%-15s%-15s%-15d\n", vehicle.getVehicleID(), vehicle.getVehicleName(),
                    vehicle.getVehicleColor(), vehicle.getVehiclePrice(), vehicle.getVehicleBrand(),vehicle.getType(),
                    vehicle.getProductYear());
        }
    }

    // Function 5: Search vehicle by Name
    public void searchVehicleByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a name to search for: ");
        String searchName = scanner.nextLine().trim();

        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicleList.values()) {
            if (vehicle.getVehicleName().toLowerCase().contains(searchName.toLowerCase())) {
                matchingVehicles.add(vehicle);
            }
        }
        if (matchingVehicles.isEmpty()) {
            System.out.println("No vehicles found with a name containing \"" + searchName + "\".");
        } else {
            Collections.sort(matchingVehicles, new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                    return vehicle2.getVehicleName().compareToIgnoreCase(vehicle1.getVehicleName());
                }
            });
            displayVehicle(matchingVehicles);
        }
    }

    // Function 5.2: Search by id
    public void searchVehicleByID() {
        String searchID = vl.inputVehicleID("Enter vehicle ID to search (in the format VXXXXX): ");

        Vehicle foundVehicle = searchVehicleById(vehicleList, searchID);

        if (foundVehicle != null) {
            ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
            matchingVehicles.add(foundVehicle);

            displayVehicle(matchingVehicles);
        } else {
            System.out.println("No vehicle found with ID: " + searchID);
        }
    }

    // Function 6.1: Display all vehicle
    public void displayAllVehicles() {
        if (vehicleList.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        List<Vehicle> tempList = new ArrayList<>(vehicleList.values());

        Collections.sort(tempList, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                int id1 = Integer.parseInt(vehicle1.getVehicleID().substring(1));
                int id2 = Integer.parseInt(vehicle2.getVehicleID().substring(1));
                return Integer.compare(id1, id2);
            }
        });

        System.out.println("\nALL VEHICLES");

        System.out.printf("%-12s%-20s%-10s%-12s%-15s%-15s%-15s\n", "Vehicle ID", "Name", "Color", "Price", "Brand","Type",
                "Product Year");

        for (Vehicle vehicle : tempList) {
            System.out.printf("%-12s%-20s%-10s%-12.2f%-15s%-15s%-15d\n", vehicle.getVehicleID(), vehicle.getVehicleName(),
                    vehicle.getVehicleColor(), vehicle.getVehiclePrice(), vehicle.getVehicleBrand(),vehicle.getType(),
                    vehicle.getProductYear());
        }
    }

    // Function 6.2: Display all vehicle by price
    public void displayVehicleByPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a price to filter vehicles by: ");
        double priceFilter = vl.inputDouble("Enter a price to filter vehicles by: ", 0, Double.MAX_VALUE,false);

        ArrayList<Vehicle> filteredVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicleList.values()) {
            if (vehicle.getVehiclePrice() < priceFilter) {
                filteredVehicles.add(vehicle);
            }
        }

        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found with a price lower than " + priceFilter + ".");
        } else {
            Collections.sort(filteredVehicles, new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                    return Double.compare(vehicle2.getVehiclePrice(), vehicle1.getVehiclePrice());
                }
            });

            displayVehicle(filteredVehicles);
        }
    }

    // Function 7: Load data from file
    public void loadVehicleData() {
        File file = new File("VehicleData.txt");
        if (!file.exists()) {
            System.out.println("VehicleData.txt does not exist.");
            return; // Thoát khỏi phương thức nếu tệp không tồn tại
        }

        boolean fileExists = true; // Biến cờ để kiểm tra file tồn tại

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Bỏ qua dòng trống
                }

                String[] data = line.split(",");
                if (data.length == 7) {
                    String vehicleID = data[0];
                    String vehicleName = data[1];
                    String vehicleColor = data[2];
                    double vehiclePrice = Double.parseDouble(data[3]);
                    String vehicleBrand = data[4];
                    String type = data[5];
                    int productYear = Integer.parseInt(data[6]);

                    // Tạo một đối tượng Vehicle và thêm vào danh sách phương tiện
                    Vehicle vehicle = new Vehicle(vehicleID, vehicleName, vehicleColor, vehiclePrice, vehicleBrand,
                            type, productYear);
                    vehicleList.put(vehicleID, vehicle);
                }
            }
            System.out.println("Vehicle data loaded successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Vehicle data.");
            fileExists = false; // Đánh dấu rằng file không tồn tại trong trường hợp lỗi
        }

        if (!fileExists) {
            System.out.println("Unable to load data because the file does not exist.");
        }
    }

    // Function 8: Save data to file
    public void saveVehicleData() {
        try {
            ArrayList<Vehicle> sortedVehicles = new ArrayList<>(vehicleList.values());

            Collections.sort(sortedVehicles, new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                    int id1 = Integer.parseInt(vehicle1.getVehicleID().substring(1));
                    int id2 = Integer.parseInt(vehicle2.getVehicleID().substring(1));
                    return Integer.compare(id1, id2);
                }
            });

            BufferedWriter writer = new BufferedWriter(new FileWriter("VehicleData.txt"));

            for (Vehicle vehicle : sortedVehicles) {
                String line = vehicle.getVehicleID() + ","
                        + vehicle.getVehicleName() + ","
                        + vehicle.getVehicleColor() + ","
                        + vehicle.getVehiclePrice() + ","
                        + vehicle.getVehicleBrand() + ","
                        + vehicle.getType() + ","
                        + vehicle.getProductYear();

                writer.write(line);
                writer.newLine();
            }

            writer.close();

            System.out.println("Vehicle data saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving Vehicle data.");
        }
    }

    // Function 9.1: Print the vehicle list
    public void printAllVehicles() {
        String currentDate = vl.getCurrentDate();
        ArrayList<Vehicle> sortedVehicles = new ArrayList<>(vehicleList.values());

        Collections.sort(sortedVehicles, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                int id1 = Integer.parseInt(vehicle1.getVehicleID().substring(1));
                int id2 = Integer.parseInt(vehicle2.getVehicleID().substring(1));
                return Integer.compare(id1, id2);
            }
        });
        System.out.println(" _____________________________________________________________________________________");
        System.out.println("|                                    FPT Auto                                         |");
        System.out.println("|                                                      Address: X District, Y City    |");
        System.out.println("|             " + currentDate + "                                                              |");
        System.out.println(" _____________________________________________________________________________________");
        System.out.printf("%-12s%-20s%-10s%-12s%-15s%-15s\n", "Vehicle ID", "Name", "Color", "Price", "Brand",
                "Product Year");

        for (Vehicle vehicle : sortedVehicles) {
            System.out.printf("%-12s%-20s%-10s%-12.2f%-15s%-15d\n", vehicle.getVehicleID(), vehicle.getVehicleName(),
                    vehicle.getVehicleColor(), vehicle.getVehiclePrice(), vehicle.getVehicleBrand(),
                    vehicle.getProductYear());
        }
    }

    // Function 9.2: Print the vehicle list by year
    public void printVehicleByYear() {
        int year = vl.inputYear("Enter a year to filter vehicles by: ",false);
        String currentDate = vl.getCurrentDate();
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicleList.values()) {
            if (vehicle.getProductYear() >= year) {
                matchingVehicles.add(vehicle);
            }
        }

        Collections.sort(matchingVehicles, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                return Integer.compare(vehicle2.getProductYear(), vehicle1.getProductYear());
            }
        });

        System.out.println(" _____________________________________________________________________________________");
        System.out.println("|                                    FPT Auto                                         |");
        System.out.println("|                                                      Address: X District, Y City    |");
        System.out.println("|             " + currentDate + "                                                              |");
        System.out.println(" _____________________________________________________________________________________");
        System.out.printf("%-12s%-20s%-10s%-12s%-15s%-15s\n", "Vehicle ID", "Name", "Color", "Price", "Brand",
                "Product Year");

        for (Vehicle vehicle : matchingVehicles) {
            System.out.printf("%-12s%-20s%-10s%-12.2f%-15s%-15d\n", vehicle.getVehicleID(), vehicle.getVehicleName(),
                    vehicle.getVehicleColor(), vehicle.getVehiclePrice(), vehicle.getVehicleBrand(),
                    vehicle.getProductYear());
        }
    }

    //Function 10: Exit
    public void quit() {
        String exitChoice = vl.inputYN("Do you want to exit the program? (Y/N)");
        if (exitChoice.equalsIgnoreCase("Y")) {
            if (dataChanged) {
                String choice = vl.inputYN("Detected data change, do you want to save the data before quitting? (Y/N)");
                if (choice.equalsIgnoreCase("Y")) {
                    saveVehicleData();
                }
            }
            System.out.println("Exiting the program. Goodbye!");
            System.exit(0);
        }
    }
}

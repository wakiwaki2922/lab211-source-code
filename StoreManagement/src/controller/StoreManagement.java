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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import model.BillOfSale;
import model.BsProduct;
import model.Product;
import model.PurchaseReceipt;

/**
 *
 * @author kyrov
 */
public class StoreManagement {
    private HashMap<String, Product> productList = new HashMap<>();
    private HashMap<String, PurchaseReceipt> receiptList = new HashMap<>();
    private HashMap<String, BillOfSale> billList = new HashMap<>();

    private Validator vl = new Validator();

    // Purchasing Management
    // Function 1: Purchase products
    public void purchaseProduct() {
        String prID = vl.generatePrId(receiptList);

        String orderDate = vl.inputReceiptDate();

        int n = vl.inputInt("Enter the number of product types purchased: ", 1, Integer.MAX_VALUE, false);

        for (int i = 0; i < n; i++) {
            String pID = vl.generateProductId(productList);

            String productName = vl.inputString("Enter product name: ", false);
            double purchasePrice = vl.inputDouble("Enter purchase price: ", 0.0, Double.MAX_VALUE, false);
            int initialQuantity = vl.inputInt("Enter initial quantity: ", 1, Integer.MAX_VALUE, false);
            String productionDate = vl.inputBeforeDate(orderDate);
            String expirationDate = vl.inputAfterDate(orderDate);

            Product product = new Product(pID, productName, purchasePrice,
                    initialQuantity, productionDate, expirationDate, prID);
            productList.put(pID, product);
        }

        PurchaseReceipt purchaseReceipt = new PurchaseReceipt(prID, orderDate);
        receiptList.put(prID, purchaseReceipt);

        System.out.println("Purchase receipt created successfully.");
    }

    // Function 2: Check storage
    public void checkStorage() {
        // Tạo danh sách các sản phẩm tồn kho
        ArrayList<Product> storageList = new ArrayList<>();
        for (Product product : productList.values()) {
            if (product.getCurQuantity() > 0) {
                storageList.add(product);
            }
        }

        Collections.sort(storageList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                String prID1 = product1.getReceipt();
                String prID2 = product2.getReceipt();
                String pID1 = product1.getpID();
                String pID2 = product2.getpID();

                int prIDComparison = prID1.compareTo(prID2);
                if (prIDComparison != 0) {
                    return prIDComparison;
                }

                return pID1.compareTo(pID2);
            }
        });

        // In danh sách hàng tồn
        System.out.println("Storage List (Quantity > 0):");
        System.out.printf("%-15s%-15s%-15s%-10s%-15s\n", "Product ID", "Product Name", "Purchase Price", "Quantity","Receipt ID");
        for (Product product : storageList) {
            System.out.printf("%-15s%-15s%-15.2f%-10d%-15s\n", product.getpID(), product.getName(),
                    product.getPurchasePrice(), product.getCurQuantity(), product.getReceipt());
        }
    }

    // Function 3: Check expiration products
    public void checkExpirationProduct() {
        LocalDate currentDate = LocalDate.now();

        System.out.println("Products Near Expiration (Within 10 days from today):");
        System.out.printf("%-10s%-15s%-15s%-20s\n", "Product ID", "Product Name", "Expiration Date", "Days Left");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Product product : productList.values()) {
            LocalDate expirationDate = LocalDate.parse(product.getExpDate(), dateFormatter);
            long daysLeft = currentDate.until(expirationDate).getDays();

            if (daysLeft >= 0 && daysLeft <= 10) {
                System.out.printf("%-10s%-15s%-15s%-20d\n", product.getpID(), product.getName(),
                        product.getExpDate(), daysLeft);
            }
        }
    }

    // Function 4: Search product still available
    public void searchAvailable() {
        String productName = vl.inputString("Enter a part of the product name to search for: ", false);
        System.out.println("Available Products with Name Containing: " + productName);
        System.out.printf("%-15s%-15s%-15s%-10s\n", "Product ID", "Product Name", "Purchase Price", "Quantity");

        for (Product product : productList.values()) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase()) && product.getCurQuantity() > 0) {
                System.out.printf("%-15s%-15s%-15.2f%-10d\n", product.getpID(), product.getName(),
                        product.getPurchasePrice(), product.getCurQuantity());
            }
        }
    }

    // Function 5: Display product not available
    public void displayNotAvailable() {
        System.out.println("Products Out of Stock:");
        System.out.printf("%-15s%-15s%-15s%-10s\n", "Product ID", "Product Name", "Purchase Price", "Quantity");

        for (Product product : productList.values()) {
            if (product.getCurQuantity() == 0) {
                System.out.printf("%-15s%-15s%-15.2f%-10d\n", product.getpID(), product.getName(),
                        product.getPurchasePrice(), product.getCurQuantity());
            }
        }
    }

    // Function 6: Display product with quantity less than n
    public void displaySpecific() {
        int n = vl.inputInt("Enter the minimum quantity (n): ", 0, Integer.MAX_VALUE, false);

        System.out.println("Products with Quantity Less than " + n + ":");
        System.out.printf("%-15s%-15s%-15s%-10s\n", "Product ID", "Product Name", "Purchase Price", "Quantity");

        for (Product product : productList.values()) {
            if (product.getCurQuantity() < n) {
                System.out.printf("%-15s%-15s%-15.2f%-10d\n", product.getpID(), product.getName(),
                        product.getPurchasePrice(), product.getCurQuantity());
            }
        }
    }

    // Function extra: Search by product ID
    public Product searchProductById(HashMap<String, Product> productList, String pID) {
        if (productList.containsKey(pID)) {
            return productList.get(pID);
        } else {
            return null;
        }
    }

    // Function 7: Update product
    public void updateProduct() {
        Scanner scanner = new Scanner(System.in);
        String pID = null;
        Product product = null;

        while (product == null) {
            pID = vl.inputString("Enter the product ID: ", true);
            product = searchProductById(productList, pID);
            if (product == null) {
                System.out.println("The product does not exist. Please try again.");
            }
        }
        System.out.println("Enter new information for the product:");

        // Input product name
        String name = vl.inputString("Name (" + product.getName() + "): ", true);
        if (!name.isEmpty()) {
            product.setName(name);
        }

        // Input purchase price
        double purchasePrice = vl.inputDouble("Purchase Price (" + product.getPurchasePrice() + "): ", 0,
                Double.MAX_VALUE, true);
        if (purchasePrice != product.getPurchasePrice() && purchasePrice != 0) {
            product.setPurchasePrice(purchasePrice);
        }

        // Input current quantity
        int curQuantity = vl.inputInt("Current Quantity (" + product.getCurQuantity() + "): ", 0, Integer.MAX_VALUE,
                true);
        if (curQuantity != product.getCurQuantity() && curQuantity != 0) {
            product.setCurQuantity(curQuantity);
        }
        scanner.close();
        System.out.println("Product information updated successfully.");

        System.out.println("Updated Product Information:");
        System.out.println("Product ID: " + product.getpID());
        System.out.println("Name: " + product.getName());
        System.out.println("Purchase Price: " + product.getPurchasePrice());
        System.out.println("Current Quantity: " + product.getCurQuantity());
    }

    // Function 8: Save file
    public void saveReceiptListToFile() {
        String fileName = "imports.txt";

        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (PurchaseReceipt receipt : receiptList.values()) {
                    String line = receipt.getPrID() + "," + receipt.getPrDate();
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File '" + fileName + "' does not exist.");
        }
    }

    public void saveProductListToFile() {
        String fileName = "products.txt";

        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Product product : productList.values()) {
                    String line = product.getpID() + "," + product.getName() + "," +
                            product.getPurchasePrice() + "," + product.getCurQuantity() + "," +
                            product.getProDate() + "," + product.getExpDate() + "," + product.getReceipt();
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File '" + fileName + "' does not exist.");
        }
    }

    // Sale Mangement
    // Function 1: Create bill of sale
    public void saleProduct() {
        String bsID = vl.generateBsId(billList);
        String bsDate = vl.inputSaleDate();
        BillOfSale billOfSale = new BillOfSale(bsID, bsDate);
        billOfSale.inputBsProducts(productList);
        billList.put(bsID, billOfSale);
    }

    // Function 2: Display available products
    public void displayAvailable() {
        System.out.println("Available Products:");
        System.out.printf("%-15s%-15s%-15s%-10s\n", "Product ID", "Product Name", "Purchase Price", "Quantity");

        for (Product product : productList.values()) {
            if (product.getCurQuantity() > 0) {
                System.out.printf("%-15s%-15s%-15.2f%-10d\n", product.getpID(), product.getName(),
                        product.getPurchasePrice(), product.getCurQuantity());
            }
        }
    }

    // Function 3: Save file
    public void saveBillOfSaleToFile() {
        String fileName = "exports.txt";

        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (BillOfSale billOfSale : billList.values()) {
                    String line = billOfSale.getBsID() + "," + billOfSale.getBsDate();
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File '" + fileName + "' does not exist.");
        }
    }

    public void saveBsProductToFile() {
        String fileName = "bsproducts.txt";
        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (BillOfSale billOfSale : billList.values()) {
                    String bsID = billOfSale.getBsID();
                    writer.write(bsID);

                    ArrayList<BsProduct> bsProductList = billOfSale.getBsProductList();
                    int totalQuantity = bsProductList.size();

                    writer.write("," + totalQuantity);
                    writer.newLine();

                    for (BsProduct bsProduct : bsProductList) {
                        String line = bsProduct.getpID() + "," + bsProduct.getBsPrice() + ","
                                + bsProduct.getBsQuantity();
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File '" + fileName + "' does not exist.");
        }
    }

    // Function extra
    public void loadProduct() {
        String fileName = "products.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    String pID = data[0].trim();
                    String name = data[1].trim();
                    double purchasePrice = Double.parseDouble(data[2].trim());
                    int curQuantity = Integer.parseInt(data[3].trim());
                    String proDate = data[4].trim();
                    String expDate = data[5].trim();
                    String receipt = data[6].trim();

                    Product product = new Product(pID, name, purchasePrice, curQuantity, proDate, expDate, receipt);
                    productList.put(pID, product);
                }
            }
            br.close();
            System.out.println("Products data loaded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while loading products data.");
        }
    }

    public void loadReceipt() {
        String fileName = "imports.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String prID = data[0].trim();
                    String prDate = data[1].trim();

                    PurchaseReceipt receipt = new PurchaseReceipt(prID, prDate);
                    receiptList.put(prID, receipt);
                }
            }
            br.close();
            System.out.println("Purchase data loaded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while loading purchases data.");

        }
    }

    public void loadBill() {
        String fileName = "exports.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String bsID = data[0].trim();
                    String bsDate = data[1].trim();
                    BillOfSale bill = new BillOfSale(bsID, bsDate);
                    billList.put(bsID, bill);
                }
            }
            br.close();
            System.out.println("Bills data loaded successfully");

        } catch (IOException e) {   
            System.out.println("An error occurred while loading bills data.");

        }
    }

    public void loadBsProduct() {
        String filename = "bsproducts.txt";
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File '" + filename + "' does not exist.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String bsId = data[0];
                    int bsProductSize = Integer.parseInt(data[1]);

                    BillOfSale bill = billList.get(bsId);

                    for (int i = 0; i < bsProductSize; i++) {
                        line = br.readLine();
                        String[] detailData = line.split(",");
                        if (detailData.length == 3) {
                            String pID = detailData[0];
                            double price = Double.parseDouble(detailData[1]);
                            int quantity = Integer.parseInt(detailData[2]);

                            BsProduct bs = new BsProduct(bsId, pID, price, quantity);
                            bill.getBsProductList().add(bs);
                        }
                    }
                }
            }
            br.close();
            System.out.println("Sale products data loaded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while loading sale products data.");
        }
    }

}

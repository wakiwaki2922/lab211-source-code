/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BillOfSale;
import model.Product;
import model.PurchaseReceipt;

/**
 *
 * @author kyrov
 */
public class Validator {

    private final Scanner sc = new Scanner(System.in);

    // Non-empty string and can config for allow null
    public String inputString(String msg, boolean allowNull) {
        String str = "";
        while (true) {
            System.out.println(msg);
            str = sc.nextLine();
            if (!allowNull && str.trim().isEmpty()) {
                System.out.println("Not allowed to be empty, please input again.");
            } else {
                break;
            }
        }
        return str;
    }

    // Auto generate code
    public static String generateCode(String prefix, int length, int curNumber) {
        String formatStr = "%0" + length + "d";
        return prefix + String.format(formatStr, curNumber);
    }

    // Auto generate receipt ID
    public String generatePrId(HashMap<String, PurchaseReceipt> receiptList) {
        int nextId = 1;

        for (String existingPrId : receiptList.keySet()) {
            int existingId = Integer.parseInt(existingPrId.substring(2));

            if (existingId >= nextId) {
                nextId = existingId + 1;
            }
        }

        String newPrId = generateCode("IM", 7, nextId);

        return newPrId;
    }

    // Auto generate product ID
    public String generateProductId(HashMap<String, Product> productList) {
        int nextNumber = 1;

        for (Product product : productList.values()) {
            String productId = product.getpID();
            if (productId != null && productId.matches("P\\d{6}")) {
                int currentNumber = Integer.parseInt(productId.substring(1));
                if (currentNumber >= nextNumber) {
                    nextNumber = currentNumber + 1;
                }
            }
        }

        String productId = generateCode("P", 6, nextNumber);

        return productId;
    }

    // Check input date matched the same pattetn dd/mm/yyyy
    public String inputDate(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String str = "";

        while (true) {
            str = inputString(msg, false);

            if (str.isEmpty() || str.length() != 10) {
                System.out.println("The date must be in the format dd-MM-yyyy and not empty.");
                continue;
            }

            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                System.out.println("The date format is incorrect. Please use dd-MM-yyyy format.");
                continue;
            }

            return str;
        }
    }

    // Input receipt create date (must be4 or in today)
    public String inputReceiptDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            String input = inputString("Enter the receipt date (dd-MM-yyyy): ", false);

            try {
                LocalDate receiptDate = LocalDate.parse(input, dateFormatter);
                if (!receiptDate.isAfter(currentDate)) {
                    return input;
                }
                System.out.println("Receipt date cannot be in the future or today. Please enter a valid date.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a date in dd-MM-yyyy format.");
            }
        }
    }

    // Input date before
    public String inputBeforeDate(String date) {
        LocalDate specificDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String input;

        while (true) {
            input = inputDate("Enter a date before " + date + " (dd-MM-yyyy): ");
            LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            if (inputDate.isBefore(specificDate)) {
                return input;
            }

            System.out.println("Entered date must be before " + date + ". Please enter a valid date.");
        }

    }

    // Input date after
    public String inputAfterDate(String date) {
        LocalDate specificDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String input;

        while (true) {
            input = inputDate("Enter a date after " + date + " (dd-MM-yyyy): ");
            LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            if (inputDate.isAfter(specificDate)) {
                return input;
            }

            System.out.println("Entered date must be after " + date + ". Please enter a valid date.");
        }
    }

    // Input int type number,value between min to max
    public int inputInt(String msg, int min, int max, boolean allowNull) {
        System.out.println(msg);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (allowNull && input.isEmpty()) {
                    return 0;
                }

                if (input.matches("\\D")) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    continue;
                }

                int num = Integer.parseInt(input);

                if (num < min || num > max) {
                    System.out.println("This number must be between " + min + " and " + max);
                    continue;
                }

                return num;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Input double type number, value between min to max, must be positive
    public double inputDouble(String msg, double min, double max, boolean allowNull) {
        System.out.println(msg);
        while (true) {
            try {
                String input = sc.nextLine().trim();

                if (allowNull && input.isEmpty()) {
                    return 0.0; // Trả về 0.0 nếu cho phép để trống
                }

                double num = Double.parseDouble(input);

                if (num < min || num > max) {
                    System.out.println("This number must be " + min + " to " + max);
                    continue;
                }

                return num;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Auto generate bill of sale ID
    public String generateBsId(HashMap<String, BillOfSale> billOfSaleList) {
        int nextId = 1;

        for (String existingBsId : billOfSaleList.keySet()) {
            int existingId = Integer.parseInt(existingBsId.substring(2));

            if (existingId >= nextId) {
                nextId = existingId + 1;
            }
        }

        String newBsId = generateCode("BS", 6, nextId);

        return newBsId;
    }

    //Sale date
    public String inputSaleDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String today = currentDate.format(dateFormatter);

        System.out.println("Sale date: " + today);
        return today;
    }

    // Center text
    public static String centerText(String text, int length) {
        int padding = length - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;

        StringBuilder centeredText = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) {
            centeredText.append(" ");
        }
        centeredText.append(text);
        for (int i = 0; i < rightPadding; i++) {
            centeredText.append(" ");
        }

        return centeredText.toString();
    }

    // Print menu
    public void menuPrint(String[] optionList, String menuName) {
        int maxOptionLength = 0;
        for (String option : optionList) {
            if (option.length() > maxOptionLength) {
                maxOptionLength = option.length();
            }
        }

        int menuWidth = maxOptionLength + 8;

        System.out.print("o");
        for (int i = 0; i < menuWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("o");

        System.out.print("|");
        System.out.print(centerText(menuName, menuWidth - 2));
        System.out.println("|");

        System.out.print("o");
        for (int i = 0; i < menuWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("o");

        for (int i = 0; i < optionList.length; i++) {
            String option = (i + 1) + ". " + optionList[i];
            System.out.print("| ");
            System.out.print(centerText(option, menuWidth - 4));
            System.out.println(" |");
        }

        System.out.print("o");
        for (int i = 0; i < menuWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("o");
    }
}

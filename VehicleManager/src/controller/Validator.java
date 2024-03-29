/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Vehicle;

/**
 *
 * @author kyrov
 */
public class Validator {

    private final Scanner sc = new Scanner(System.in);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Non-emty string and can config for allow null
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

    // Input VehicleID and check for already exist
    public String inputVehicleID(HashMap<String, Vehicle> vehicleList, String msg) {
        String vehicleID = "";
        String pattern = "V\\d{5}"; // Format VXXXXX
        while (true) {
            System.out.print(msg);
            vehicleID = sc.nextLine().trim();
            if (vehicleID.matches(pattern)) {
                if (vehicleList.containsKey(vehicleID)) {
                    System.out.println("Vehicle ID already exists. Please input again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid Vehicle ID format. Please input again in the format VXXXXX.");
            }
        }
        return vehicleID;
    }

    // Input VehicleID and not check for exist
    public String inputVehicleID(String msg) {
        String vehicleID = "";
        String pattern = "V\\d{5}"; // Format VXXXXX
        while (true) {
            System.out.print(msg);
            vehicleID = inputString(msg, false);
            if (vehicleID.matches(pattern)) {
                break;
            } else {
                System.out.println("Invalid Vehicle ID format. Please input again in the format VXXXXX.");
            }
        }
        return vehicleID;
    }

    // Input a year, check if it valid and not over current year
    public Integer inputYear(String msg, boolean allowNull) {
        Integer year = null;
        int currentYear = LocalDate.now().getYear();

        while (true) {
            try {
                System.out.println(msg);
                String input = sc.nextLine().trim();

                if (allowNull && input.isEmpty()) {
                    return 0;
                }

                year = Integer.parseInt(input);

                if (year < 1800 || year > currentYear) {
                    System.out.println("Year must be between 1800 and " + currentYear);
                    continue;
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year.");
            }
        }

        return year;
    }

    // Input int type number,value between min to max
    public int inputInt(String msg, int min, int max) {
        System.out.println(msg);
        while (true) {
            try {
                String input = sc.nextLine().trim();
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

    // Input date format dd/mm/yyyy
    public String inputDate(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String date = "";

        while (true) {
            date = inputString(msg, false);
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                System.out.println("The date format is incorrect. Please enter in dd/mm/yyyy format.");
                continue;
            }
            return date;
        }
    }

    // Check file, if exist ask to overide
    public boolean checkFileExistence(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("File " + filename + " already exists.");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Do you want to overwrite it? (yes/no): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    return true;
                } else if (input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        } else {
            return true;
        }
    }

    // Input yes or no
    public String inputYN(String msg) {
        String str = "";
        boolean validInput = false;
        while (!validInput) {
            str = inputString(msg, false);
            if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
        return str;
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

    //Take date from now
    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }
}

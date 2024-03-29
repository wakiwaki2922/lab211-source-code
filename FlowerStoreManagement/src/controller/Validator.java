/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Flower;
import model.Order;

/**
 *
 * @author kyrov
 */
public class Validator {

    private Scanner sc = new Scanner(System.in);
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //Check input non-null string
    public String inputString(String msg) {
        String str = "";
        while (true) {
            sc = new Scanner(System.in);
            System.out.println(msg);
            str = sc.nextLine();
            if (str == "") {
                System.out.println("Not allowed null please input again");
            } else {
                break;
            }
        }
        return str;
    }

    //Check input name between 3 to 50 characters
    public String inputDescription(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            int lengthDescription = str.length();
            if (lengthDescription < 3 || lengthDescription > 50) {
                System.out.println("Must be 3 to 50 characters");
            } else {
                break;
            }
        }
        return str;
    }

    //Check input date matched the same pattetn dd/mm/yyyy
    public String inputDate(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String str = "";

        while (true) {
            str = inputString(msg);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                System.out.println("The format date incorrect");
                continue;
            }
            return str;
        }
    }

    //Check input date before and in right pattern dd/mm/yyyy
    public String inputDateAfter(String msg, String dateBefore) throws ParseException {
        String date = "";
        while (true) {
            date = inputDate(msg);
            Date dateB = sdf.parse(date);
            Date dateA = sdf.parse(dateBefore);
            if (dateB.before(dateA)) {
                System.out.println("The second date must after the first date.");
                continue;
            }
            return date;
        }
    }

    //Check if flower id exist and follow the right pattern FXXX
    public String inputID(Set<Flower> ls, String msg) {
        String str = "";
        String pattern = "F\\d{3}";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                boolean idExists = false;
                for (Flower f : ls) {
                    if (f.getId().equals(str)) {
                        idExists = true;
                        System.out.println("Flower ID already exists! Please enter again.");
                        break;
                    }
                }
                if (!idExists) {
                    break;
                }
            } else {
                System.out.println("Invalid flower ID format! Please enter again in the format FXXX.");
            }
        }
        return str;
    }

    //Check number input between min to max value
    public double inputDouble(String msg, double min, double max) {
        System.out.println(msg);
        while (true) {
            String str = sc.nextLine();
            try {
                double num = Double.parseDouble(str);
                if (num < min || num > max) {
                    System.out.println("This number must be " + min + " to " + max);
                    continue;

                }
                return num;
            } catch (NumberFormatException e) {
                System.out.println("This must be number ");
            }

        }
    }

    //Input int type number,value between min to max
    public int inputInt(String msg, int min, int max) {
        System.out.println(msg);
        while (true) {
            try {
                String input = sc.nextLine();
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

    //Check if order id exist and follow the right pattern XXXX
    public String inputOID(Set<Order> ls, String msg) {
        String pattern = "\\d{4}";
        String str = "";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                boolean idExists = false;
                for (Order o : ls) {
                    if (o.getOrderID().equals(str)) {
                        idExists = true;
                        System.out.println("Order ID already exist! Please enter again.");
                        break;
                    }
                }
                if (!idExists) {
                    break;
                }
            } else {
                System.out.println("Invalid Order ID format! Please enter again in the format XXXX.");
            }
        }
        return str;
    }

    //Find flower by ID or Name
    public Flower findFlower(Set<Flower> flowerSet, String msg) {
        // Check if field is ID or name
        String findField = inputString(msg);
        for (Flower fl : flowerSet) {
            if (fl.getId().equals(findField)) {
                System.out.println(fl);
                return fl;
            }
        }
        System.out.println("The flower does not exist");
        return null;
    }

    //Input Y/N
    public String inputYN(String msg) {
        String str = "";
        boolean validInput = false;
        while (!validInput) {
            str = inputString(msg);
            if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
        return str;
    }

    //Date compare
    public boolean dateCompare(String dateString, String startDateString, String endDateString) throws ParseException {
        try {
            Date date = sdf.parse(dateString);
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            return (date.equals(startDate) || date.after(startDate)) && (date.equals(endDate) || date.before(endDate));
        } catch (ParseException e) {
            return false;
        }
    }

    //Check file existence
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

    //Input for update function
    //Input date
    public String inputDateU(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String str = "";

        while (true) {
            System.out.println(msg);
            str = sc.nextLine();
            Matcher matcher = pattern.matcher(str);
            if (str.isEmpty()) {
                return str;
            } else if (!matcher.matches()) {
                System.out.println("The format date incorrect");
                continue;
            }
            return str;
        }
    }

    //Input description
    public String inputDescriptionU(String msg) {
        String str = "";
        while (true) {
            System.out.println(msg);
            str = sc.nextLine();
            int lengthDescription = str.length();
            if (str.isEmpty()) {
                return str;
            } else if (lengthDescription < 3 || lengthDescription > 50) {
                System.out.println("Must be 3 to 50 characters");
                continue;
            }
            return str;
        }
    }

    //Input value
    public double inputDoubleU(String msg, double min, double max) {
        System.out.println(msg);
        while (true) {
            String str = sc.nextLine();
            if (str.isEmpty()) {
                double num = 0;
                return num;
            } else {
                try {
                    double num = Double.parseDouble(str);
                    if (num < min || num > max) {
                        System.out.println("This number must be " + min + " to " + max);
                        continue;

                    }
                    return num;
                } catch (NumberFormatException e) {
                    System.out.println("This must be number ");
                }
            }
        }
    }

    //Calculation total
//    public Double totalCal(Order order) {
//        double orderTotal = 0;
//        for (OrderDetail orderDetail : order.getOrderdetail()) {
//            orderTotal += orderDetail.getPrice();
//        }
//        return orderTotal;
//    }
}

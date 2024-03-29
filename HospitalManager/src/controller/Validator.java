/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Nurse;
import model.Patient;

/**
 *
 * @author kyrov
 */
public class Validator {

    private final Scanner sc = new Scanner(System.in);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //Non-emty string
    public String inputString(String msg) {
        String str = "";
        while (true) {
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
    
    //Compare date
    public boolean dateCompare(String date1, String date2, String date3) throws ParseException {
        Date startDate = sdf.parse(date1);
        Date endDate = sdf.parse(date2);
        Date comparedDate = sdf.parse(date3);
        if ((comparedDate.equals(startDate) || comparedDate.after(startDate))
                && (comparedDate.equals(endDate) || comparedDate.before(endDate))) {
            return true;
        } else {
            return false;
        }
    }
    //Input StaffID
    public String inputStaffID(HashMap<String, Nurse> ls, String msg) {
        String str = "";
        String pattern = "N\\d{4}"; // Định dạng NXXXX
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                if (ls.containsKey(str)) {
                    System.out.println("StaffID already exists. Please input again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid StaffID format. Please input again in the format NXXXX.");
            }
        }
        return str;
    }

    //Input patient ID
    public String inputID(HashMap<String, Patient> ls, String msg) {
        String str = "";
        String pattern = "P\\d{4}"; // Định dạng PXXXX
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                if (ls.containsKey(str)) {
                    System.out.println("Patient ID already exists. Please input again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid Patient ID format. Please input again in the format PXXXX.");
            }
        }
        return str;
    }

    //Input Shift Day/Night
    public String inputShift(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            if (str.equalsIgnoreCase("Day") || str.equalsIgnoreCase("Night")) {
                break;
            } else {
                System.out.println("Invalid shift. Please choose Day or Night shift");
            }
        }
        return str;
    }

    //Input gender, only Male or Female
    public String inputGender(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            if (str.equalsIgnoreCase("F") || str.equalsIgnoreCase("M")) {
                break;
            } else {
                System.out.println("Invalid gender, Please choose F(Female) or M(Male)");
            }
        }
        return str;
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

    //Input phone number, check if phone string length between 10 to 11 characters
    public String inputPhone(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            int lengthPhone = str.length();
            if (lengthPhone < 10 || lengthPhone > 11) {
                System.out.println("Phone must be 10 to 11 numbers");
            } else {
                break;
            }
        }
        return str;
    }

    //Input department, check if department string length between 3 to 50 characters
    public String inputDepartment(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            int lengthDepartment = str.length();
            if (lengthDepartment < 3 || lengthDepartment > 50) {
                System.out.println("Must be 3 to 50 characters");
            } else {
                break;
            }
        }
        return str;
    }

    //Input double type number, value between min to max, must be positive
    public double inputDouble(String msg, double min, double max) {
        System.out.println(msg);
        while (true) {
            try {
                double num = sc.nextDouble();
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

    //Input date format dd/mm/yyyy
    public String inputDate(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String date = "";

        while (true) {
            date = inputString(msg);
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                System.out.println("The date format is incorrect. Please enter in dd/mm/yyyy format.");
                continue;
            }
            return date;
        }
    }

    //Input date of discharge
    public String inputDischarge(String msg, String adDate) throws ParseException {
        String date = "";
        while (true) {
            date = inputDate(msg);
            Date dischargeDate = sdf.parse(date);
            Date admissionDate = sdf.parse(adDate);
            if(dischargeDate.before(admissionDate)){
                System.out.println("The second date must after the first date.");
                continue;
            }
            return date;
        }
    }

    //Check file, if exist ask to overide
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
    
    //Input yes or no
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
}

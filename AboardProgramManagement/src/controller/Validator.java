/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.AboardProgram;
import model.Student;

/**
 *
 * @author kyrov
 */
public class Validator {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //Check input string if null
    public String inputString(String msg) {
        Scanner sc = new Scanner(System.in);
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

    //Check input program id if null, wrong data format or already exists
    public String inputPID(HashSet<AboardProgram> apList, String msg) {
        String str = "";
        String pattern = "^[A-Z]{3}\\d{3}$";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                boolean idExists = false;
                for (AboardProgram ab : apList) {
                    if (ab.getId().equals(str)) {
                        idExists = true;
                        System.out.println("Aboard program's ID already exists! Please enter again.");
                        break;
                    }
                }
                if (!idExists) {
                    break;
                }
            } else {
                System.out.println("Invalid program's ID format! Please enter again in the format ABCXXX.");
            }
        }
        return str;
    }

    //Check input program's time, accept only: January,March,may,May,July,September,November
    public String inputPTime(String msg) {
        String[] validMonths = {"January", "March", "May", "July", "September", "November"};
        String str = "";
        boolean isValid = false;
        while (!isValid) {
            str = inputString(msg);
            for (String month : validMonths) {
                if (str.equalsIgnoreCase(month)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                System.out.println("Invalid month. Please enter a valid month.");
                System.out.println("Accept only: January, March, May, May, July, September, November");
            }
        }
        return str;
    }

    //Check input date if dont follow format dd/MM/yyyy
    public String inputADate(String msg) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        String str = "";
        while (true) {
            str = inputString(msg);
            Matcher matcher = pattern.matcher(str);

            if (matcher.matches()) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy.");
            }
        }

        return str;
    }

    //check 2nd date input if it b4 1st date
    public String inputBDate(String msg, String ADate) {
        String str = "";
        LocalDate startDate = LocalDate.parse(ADate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        while (true) {
            str = inputADate(msg);
            LocalDate endDate = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (endDate.isAfter(startDate)) {
                break;
            } else {
                System.out.println("The second date must be after the first date");
            }
        }
        return str;
    }

    //check input number if it out of min to max range
    public int inputInt(String msg, int min, int max) {
        while (true) {
            try {
                String input = inputString(msg);
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

    //Check number input if it out of min to max range
    public double inputDouble(String msg, double min, double max) {
        System.out.println(msg);
        while (true) {
            String str = inputString(msg);
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

    //check input content if it doesnt contain .doc or .pdf extend
    public String inputContent(String msg) {
        String str = "";
        while (true) {
            str = inputString(msg);
            if (!str.matches(".*\\.(doc|pdf)$")) {
                System.out.println("Invalid input. Please enter a valid content with .doc or .pdf extension.");
                continue;
            }
            return str;
        }
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

    //update pID
    public String inputPID(String msg) {
        String str = "";
        String pattern = "^[A-Z]{3}\\d{3}$";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                break;

            } else {
                System.out.println("Invalid program's ID format! Please enter again in the format ABCXXX.");
            }
        }
        return str;
    }

    //check student's id if it null, duplicated or wrong format
    public String inputSID(HashSet<Student> studentList, String msg) {
        String str = "";
        String pattern = "\\d{4}";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                boolean idExists = false;
                for (Student s : studentList) {
                    if (s.getId().equals(str)) {
                        idExists = true;
                        System.out.println("Student's ID already exists! Please enter again.");
                        break;
                    }
                }
                if (!idExists) {
                    break;
                }
            } else {
                System.out.println("Invalid Student's ID format! Please enter again in the format XXXX.");
            }
        }
        return str;
    }

    //check major if it doesnt  "SE", "SB", "GD" or "MC"
    public String inputMajor(String msg) {
        String str = "";
        boolean isValid = false;
        do {
            str = inputString(msg);
            if (str.equals("SE") || str.equals("SB") || str.equals("GD") || str.equals("MC")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid major.");
            }
        } while (!isValid);
        return str;
    }

    //check email if it doesnt contain "@fpt.edu.vn" at the end
    public String inputEmail(String msg) {
        String str;

        while (true) {
            str = inputString(msg);

            if (!str.endsWith("@fpt.edu.vn")) {
                System.out.println("Invalid email address. Please enter a valid email ending with @fpt.edu.vn.");
            } else {
                break;
            }
        }

        return str;
    }

    //update sID
    public String inputSID(String msg) {
        String str = "";
        String pattern = "\\d{4}";
        while (true) {
            str = inputString(msg);
            if (str.matches(pattern)) {
                break;
            } else {
                System.out.println("Invalid Student's ID format! Please enter again in the format XXXX.");
            }
        }
        return str;
    }

    //date between 2 date
    public String inputDateBetween(String msg, String startDate, String endDate) {
        String str = "";
        boolean isValid = false;
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        do {
            str = inputADate(msg);
            LocalDate date = LocalDate.parse(str);

            if (date.isEqual(sDate) || date.isEqual(eDate) || (date.isAfter(sDate) && date.isBefore(eDate))) {
                isValid = true;
            } else {
                System.out.println("Invalid date. Please enter a date between " + startDate + " and " + endDate + ".");
            }

        } while (!isValid);

        return str;
    }
    
    
}

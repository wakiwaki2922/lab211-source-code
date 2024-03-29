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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import model.Nurse;
import model.Patient;

/**
 *
 * @author kyrov
 */
public class HospitalFunction {

    private static final Validator vl = new Validator();
    //Hashmap to manage nurses and patients list
    private HashMap<String, Nurse> nurseList = new HashMap<>();
    private HashMap<String, Patient> patientList = new HashMap<>();
    //SimpleDateFormat to manage date and time
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //Check if data changed
    private boolean dataChanged = false;

    //Nurse's functions
    //Function 1: Create nurse
    public void addNurse() {
        //input all nurse's field
        String staffID = vl.inputStaffID(nurseList, "Enter staffID (NXXXX) : ");
        String name = vl.inputString("Enter name: ");
        int age = vl.inputInt("Enter age: ", 0, 100);
        String gender = vl.inputGender("Enter gender (F/M): ");
        String address = vl.inputString("Enter address: ");
        String phone = vl.inputPhone("Enter phone (10 to 11 characters): ");
        String department = vl.inputDepartment("Enter department: ");
        String shift = vl.inputShift("Enter shift (Day/Night): ");
        double salary = vl.inputDouble("Enter salary: ", 0, 1000000);
        //put nurse into nurseList
        Nurse newNurse = new Nurse(staffID, department, shift, salary, name, age, gender, address, phone);
        nurseList.put(staffID, newNurse);
        //ask to countinue or end adding
        String choice = vl.inputYN("Do you want to add another nurse? (Y/N)");
        if (choice.equalsIgnoreCase("Y")) {
            addNurse();
        }
        dataChanged = true;
    }

    //Function 2:Find nurse by name or part of name
    public void findNurse() {
        String str = vl.inputString("Enter nurse's name: ").trim();
        boolean nurseFound = false;
        for (Nurse nurse : nurseList.values()) {
            if (nurse.getName().contains(str)) {
                System.out.println(nurse);
                nurseFound = true;
            }
        }
        if (!nurseFound) {
            System.out.println("Nurse's name enter does not exist ");
        }
    }

    public Nurse findNurseByStaffID(String staffID) {
        if (nurseList.containsKey(staffID)) {
            return nurseList.get(staffID);
        } else {
            return null;
        }
    }

    //Function 3:Update nurse
    public void updateNurse() {
        Scanner scanner = new Scanner(System.in);
        String staffID = null;
        Nurse nurse = null;
        displayNurse();
        while (nurse == null) {
            System.out.print("Enter the nurse's staff ID: ");
            staffID = scanner.nextLine();
            nurse = findNurseByStaffID(staffID);

            if (nurse == null) {
                System.out.println("The nurse does not exist. Please try again.");
            }
        }

        System.out.println("Enter new information for the nurse:");

        //Input name
        System.out.print("Name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = nurse.getName();
        } else {
            dataChanged = true;
        }

        //Input age
        System.out.print("Age: ");
        String ageInput = scanner.nextLine();
        int age;
        while (!ageInput.isEmpty() && !ageInput.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number or leave it blank.");
            System.out.print("Age: ");
            ageInput = scanner.nextLine();
        }
        if (ageInput.isEmpty()) {
            age = nurse.getAge();
        } else {
            age = Integer.parseInt(ageInput);
            dataChanged = true;
        }

        //Input gender
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        while (!gender.isEmpty() && !gender.matches("[FM]")) {
            System.out.println("Invalid input. Please enter 'F' for female, 'M' for male, or leave it blank.");
            System.out.print("Gender: ");
            gender = scanner.nextLine();
        }
        if (gender.isEmpty()) {
            gender = nurse.getGender();
        } else {
            dataChanged = true;
        }

        //Input address
        System.out.print("Address: ");
        String address = scanner.nextLine();
        if (address.isEmpty()) {
            address = nurse.getAddress();
        } else {
            dataChanged = true;
        }

        //Input phone
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        while (!phone.isEmpty() && (phone.length() < 10 || phone.length() > 11)) {
            System.out.println("Invalid input. Please enter a phone number of 10 to 11 characters or leave it blank.");
            System.out.print("Phone: ");
            phone = scanner.nextLine();
        }
        if (phone.isEmpty()) {
            phone = nurse.getPhone();
        } else {
            dataChanged = true;
        }

        System.out.print("Department: ");
        String department = scanner.nextLine();
        if (department.isEmpty()) {
            department = nurse.getDepartment();
        } else {
            dataChanged = true;
        }

        //Input shift
        System.out.print("Shift: ");
        String shift = scanner.nextLine();
        while (!shift.isEmpty() && !shift.equalsIgnoreCase("Day") && !shift.equalsIgnoreCase("Night")) {
            System.out.println("Invalid input. Please enter 'Day', 'Night', or leave it blank.");
            System.out.print("Shift: ");
            shift = scanner.nextLine();
        }
        if (shift.isEmpty()) {
            shift = nurse.getShift();
        } else {
            dataChanged = true;
        }

        //Input salary
        System.out.print("Salary: ");
        String salaryInput = scanner.nextLine();
        double salary;
        while (!salaryInput.isEmpty() && !salaryInput.matches("\\d+(\\.\\d+)?")) {
            System.out.println("Invalid input. Please enter a valid salary or leave it blank.");
            System.out.print("Salary: ");
            salaryInput = scanner.nextLine();
        }
        if (salaryInput.isEmpty()) {
            salary = nurse.getSalary();
        } else {
            salary = Double.parseDouble(salaryInput);
            dataChanged = true;
        }

        nurse.setName(name);
        nurse.setAge(age);
        nurse.setGender(gender);
        nurse.setAddress(address);
        nurse.setPhone(phone);
        nurse.setDepartment(department);
        nurse.setShift(shift);
        nurse.setSalary(salary);

        System.out.println("Nurse information updated successfully.");

    }

    //Function 4:Delete nurse
    public void deleteNurse() {
        Scanner scanner = new Scanner(System.in);
        String staffID = null;
        Nurse nurse = null;
        displayNurse();
        while (nurse == null) {
            System.out.print("Enter the nurse's staff ID: ");
            staffID = scanner.nextLine();
            nurse = findNurseByStaffID(staffID);

            if (nurse == null) {
                System.out.println("The nurse does not exist. Please try again.");
            } else if (!nurse.getPatientAssigned().isEmpty()) {
                System.out.println("The nurse cannot be deleted as she has assigned patients.");
                nurse = null;
            }
        }

//        if (!nurse.getPatientAssigned().isEmpty()) {
//            System.out.println("The nurse cannot be deleted as she has assigned patients.");
//            return;
//        }
        System.out.println("Are you sure you want to delete the nurse? (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            nurseList.remove(staffID);
            System.out.println("Nurse deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    //Function 4.5:Display nurse
    public void displayNurse() {

        System.out.println("\nLIST OF NURSES");
        System.out.printf("%-12s%-16s%-8s%-8s%-15s%-20s%-20s%-20s%-20s%-20s\n", "Nurse Id", "Name", "Age", "Gender", "Address", "Phone", "Department", "Shift", "Salary", "Assigned Patients");

        for (Nurse nurse : nurseList.values()) {
            StringBuilder assignedPatientIds = new StringBuilder();
            for (Patient patient : nurse.getPatientAssigned()) {
                if (assignedPatientIds.length() > 0) {
                    assignedPatientIds.append(", ");
                }
                assignedPatientIds.append(patient.getId());
            }
            System.out.printf("%-12s%-16s%-8d%-8s%-15s%-20s%-20s%-20s%-20f%-20s\n", nurse.getStaffID(), nurse.getName(), nurse.getAge(), nurse.getGender(), nurse.getAddress(), nurse.getPhone(), nurse.getDepartment(), nurse.getShift(), nurse.getSalary(), assignedPatientIds.toString());

        }
    }

    //Function 5:Add patient
    public void addPatient() throws ParseException {
        System.out.println("Enter patient information:");

        String id = vl.inputID(patientList, "ID: ");
        String name = vl.inputString("Name: ");
        int age = vl.inputInt("Age: ", 1, 150);
        String gender = vl.inputGender("Gender: ");
        String address = vl.inputString("Address: ");
        String phone = vl.inputPhone("Phone: ");
        String diagnosis = vl.inputString("Diagnosis: ");
        String admissionDate = vl.inputDate("Admission Date (dd/MM/yyyy): ");
        String dischargeDate = vl.inputDischarge("Discharge Date (dd/MM/yyyy): ", admissionDate);

        // Kiểm tra tính hợp lệ của dữ liệu bệnh nhân
        if (id.isEmpty()) {
            System.out.println("Invalid patient information. ID is required.");
            return;
        }

        if (patientList.containsKey(id)) {
            System.out.println("Invalid patient information. ID must be unique.");
            return;
        }

        // Tạo đối tượng bệnh nhân mới
        Patient patient = new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate);
        displayNurse();
        patient.inputNurseAssigned(nurseList);
        // Thêm bệnh nhân vào danh sách
        patientList.put(id, patient);

        System.out.println("Patient added successfully.");

        // Hỏi người dùng có muốn tiếp tục thêm bệnh nhân hay quay lại menu chính
        String choice = vl.inputYN("Do you want to add another patient? (Y/N)");
        if (choice.equalsIgnoreCase("Y")) {
            addPatient();
        }
    }

    //Function 6:Display patient
    public void displayPatient() throws ParseException {
        String sdate = vl.inputDate("Enter start date: ");
        String edate = vl.inputDischarge("Enter end date: ", sdate);
        System.out.println("");
        System.out.println("LIST OF PATIENTS");
        System.out.println("Start date: " + sdate);
        System.out.println("End date: " + edate);
        System.out.printf("%-4s%-12s%-12s%-12s%-16s%-12s%-20s%-20s%-5s\n", "No.", "Patient Id", "Age", "Gender", "Admission Date", "Full name", "Phone", "Diagnosis", "Assigned Nurse");

        int count = 1;
        for (Patient patient : patientList.values()) {
            if (vl.dateCompare(sdate, edate, patient.getDischargeDate()) && vl.dateCompare(sdate, edate, patient.getAdmissionDate())) {
                String admissionDate = patient.getAdmissionDate();
                String fullName = patient.getName();
                String phone = patient.getPhone();
                String diagnosis = patient.getDiagnosis();
                StringBuilder assignedNurseIds = new StringBuilder();

                for (Nurse nurse : patient.getNurseAssigned()) {
                    if (assignedNurseIds.length() > 0) {
                        assignedNurseIds.append(", ");
                    }
                    assignedNurseIds.append(nurse.getStaffID());
                }

                System.out.printf("%-4s%-12s%-12d%-12s%-16s%-12s%-20s%-20s%-5s\n", count, patient.getId(), patient.getAge(), patient.getGender(), admissionDate, fullName, phone, diagnosis, assignedNurseIds.toString());
                count++;
            }
        }
    }

    //Function 7:Sort patient
    public void sortPatient() {
        String sortField;
        while (true) {
            sortField = vl.inputString("Enter sort field (ID/Name): ");
            if (sortField.equalsIgnoreCase("ID") || sortField.equalsIgnoreCase("Name")) {
                break;
            } else {
                System.out.println("Invalid sort field. Please enter 'ID' or 'Name'.");
            }
        }

        String sortOrder;
        while (true) {
            sortOrder = vl.inputString("Enter sort order (ASC/DESC): ");
            if (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC")) {
                break;
            } else {
                System.out.println("Invalid sort order. Please enter 'ASC' or 'DESC'.");
            }
        }

        ArrayList<Patient> sortedPatients = new ArrayList<>(patientList.values());

        if (sortField.equalsIgnoreCase("id")) {
            sortedPatients.sort(Comparator.comparing(Patient::getId));
        } else if (sortField.equalsIgnoreCase("name")) {
            sortedPatients.sort(Comparator.comparing(Patient::getName));
        }

        if (sortOrder.equalsIgnoreCase("desc")) {
            Collections.reverse(sortedPatients);
        }

        System.out.println("SORTED PATIENTS");
        System.out.println("Sort field: " + sortField);
        System.out.println("Sort order: " + sortOrder);
        System.out.printf("%-4s%-12s%-16s%-12s%-15s%-20s\n", "No.", "Patient Id", "Admission Date", "Full name", "Phone", "Diagnosis");
        int count = 1;
        for (Patient patient : sortedPatients) {
            String admissionDate = patient.getAdmissionDate();
            String fullName = patient.getName();
            String phone = patient.getPhone();
            String diagnosis = patient.getDiagnosis();
            System.out.printf("%-4d%-12s%-16s%-12s%-15s%-20s\n", count, patient.getId(), admissionDate, fullName, phone, diagnosis);
            count++;
        }
    }

    //Function 8:Save data
    public void saveData() {
        // Save Nurse data
        if (vl.checkFileExistence("NurseData.txt")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("NurseData.txt"))) {
                for (Nurse nurse : nurseList.values()) {
                    StringBuilder line = new StringBuilder();
                    line.append(nurse.getStaffID()).append(",");
                    line.append(nurse.getName()).append(",");
                    line.append(nurse.getAge()).append(",");
                    line.append(nurse.getGender()).append(",");
                    line.append(nurse.getAddress()).append(",");
                    line.append(nurse.getPhone()).append(",");
                    line.append(nurse.getDepartment()).append(",");
                    line.append(nurse.getShift()).append(",");
                    line.append(nurse.getSalary()).append(",");
                    ArrayList<Patient> patients = nurse.getPatientAssigned();
                    if (patients.size() > 0) {
                        line.append(patients.get(0).getId());
                    }
                    if (patients.size() > 1) {
                        line.append(",").append(patients.get(1).getId());
                    }
                    writer.write(line.toString());
                    writer.newLine();
                }
                System.out.println("Nurse data saved successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving Nurse data.");
            }
        }

        // Save Patient data
        if (vl.checkFileExistence("PatientData.txt")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("PatientData.txt"))) {
                for (Patient patient : patientList.values()) {
                    StringBuilder line = new StringBuilder();
                    line.append(patient.getId()).append(",");
                    line.append(patient.getName()).append(",");
                    line.append(patient.getAge()).append(",");
                    line.append(patient.getGender()).append(",");
                    line.append(patient.getAddress()).append(",");
                    line.append(patient.getPhone()).append(",");
                    line.append(patient.getDiagnosis()).append(",");
                    line.append(patient.getAdmissionDate()).append(",");
                    line.append(patient.getDischargeDate()).append(",");
                    ArrayList<Nurse> nurses = patient.getNurseAssigned();
                    if (nurses.size() > 0) {
                        line.append(nurses.get(0).getStaffID());
                    }
                    if (nurses.size() > 1) {
                        line.append(",").append(nurses.get(1).getStaffID());
                    }
                    writer.write(line.toString());
                    writer.newLine();
                }
                System.out.println("Patient data saved successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving Patient data.");
            }
        }
    }

    //Function 9:Load data
    public void loadData() {
        //Load nurse
        try {
            File file = new File("NurseData.txt");
            if (!file.exists()) {
                throw new FileNotFoundException("NurseData.txt does not exist.");
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) {
                    String staffID = data[0];
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String gender = data[3];
                    String address = data[4];
                    String phone = data[5];
                    String department = data[6];
                    String shift = data[7];
                    double salary = Double.parseDouble(data[8]);
                    Nurse nurse = new Nurse(staffID, department, shift, salary, name, age, gender, address, phone);
                    nurseList.put(staffID, nurse);
                    for (int i = 9; i < data.length; i++) {
                        String patientID = data[i];
                        if (patientList.containsKey(patientID)) {
                            Patient patient = patientList.get(patientID);
                            nurse.getPatientAssigned().add(patient);
                        }
                    }
                }
            }
            System.out.println("Nurse data loaded successfully.");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("NurseData.txt does not exist.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Nurse data.");
        }

        //Load patient
        try {
            File file = new File("PatientData.txt");
            if (!file.exists()) {
                throw new FileNotFoundException("PatientData.txt does not exist.");
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) {
                    String id = data[0];
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String gender = data[3];
                    String address = data[4];
                    String phone = data[5];
                    String diagnosis = data[6];
                    String admissionDate = data[7];
                    String dischargeDate = data[8];
                    Patient patient = new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate);
                    patientList.put(id, patient);
                }
            }
            System.out.println("Patient data loaded successfully.");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("PatientData.txt does not exist.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Patient data.");
        }
        //Load nurse assigned
        try {
            File file = new File("NurseData.txt");
            if (!file.exists()) {
                throw new FileNotFoundException("NurseData.txt does not exist.");
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String staffID = data[0];
                Nurse nurse = nurseList.get(staffID);;
                for (int i = 9; i < data.length; i++) {
                    String patientID = data[i];
                    if (patientList.containsKey(patientID)) {
                        Patient patient = patientList.get(patientID);
                        nurse.getPatientAssigned().add(patient);
                    }
                }
            }
            System.out.println("Nurse patient assigned loaded successfully.");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("NurseData.txt does not exist.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Nurse data.");
        }
        //Load patient assigned
        try {
            File file = new File("PatientData.txt");
            if (!file.exists()) {
                throw new FileNotFoundException("PatientData.txt does not exist.");
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                Patient patient = patientList.get(id);
                for (int i = 9; i < data.length; i++) {
                    String nurseID = data[i];
                    if (nurseList.containsKey(nurseID)) {
                        Nurse nurse = nurseList.get(nurseID);
                        patient.getNurseAssigned().add(nurse);
                    }
                }
            }
            System.out.println("Patient nurse assigned loaded successfully.");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("PatientData.txt does not exist.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading Patient data.");
        }
    }

    //Function 10:Quit
    public void quit() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to exit the program? (Y/N)");
        String exitChoice = sc.next();
        while (!exitChoice.equalsIgnoreCase("Y") && !exitChoice.equalsIgnoreCase("N")) {
            System.out.println("Invalid choice. Please enter Y or N.");
            exitChoice = sc.next();
        }
        if (exitChoice.equalsIgnoreCase("Y")) {
            if (dataChanged) {
                System.out.println("Detected data change,do you want to save the data before quitting? (Y/N)");
                String choice = sc.next();
                while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
                    System.out.println("Invalid choice. Please enter Y or N.");
                    choice = sc.next();
                }
                if (choice.equalsIgnoreCase("Y")) {
                    saveData();
                }
            }
            System.out.println("Exiting the program. Goodbye!");
            System.exit(0);
        }

    }

    //Function : Display sdate , edate, age
    public void displayPatientsea() throws ParseException {
        String sdate = vl.inputDate("Enter start date: ");
        String edate = vl.inputDischarge("Enter end date: ", sdate);
        int age = vl.inputInt("Enter age:", 0, 150);
        System.out.println("");
        System.out.println("LIST OF PATIENTS");
        System.out.println("Start date: " + sdate);
        System.out.println("End date: " + edate);
        System.out.printf("%-4s%-12s%-12s%-12s%-16s%-12s%-20s%-20s%-5s\n", "No.", "Patient Id", "Age", "Gender", "Admission Date", "Full name", "Phone", "Diagnosis", "Assigned Nurse");

        int count = 1;
        for (Patient patient : patientList.values()) {
            Date adDate = sdf.parse(patient.getAdmissionDate());
            if ((vl.dateCompare(sdate, edate, patient.getAdmissionDate()) && vl.dateCompare(sdate, edate, patient.getDischargeDate())) && patient.getAge() == age) {
                String admissionDate = patient.getAdmissionDate();
                String fullName = patient.getName();
                String phone = patient.getPhone();
                String diagnosis = patient.getDiagnosis();
                StringBuilder assignedNurseIds = new StringBuilder();

                for (Nurse nurse : patient.getNurseAssigned()) {
                    if (assignedNurseIds.length() > 0) {
                        assignedNurseIds.append(", ");
                    }
                    assignedNurseIds.append(nurse.getStaffID());
                }

                System.out.printf("%-4s%-12s%-12d%-12s%-16s%-12s%-20s%-20s%-5s\n", count, patient.getId(), patient.getAge(), patient.getGender(), admissionDate, fullName, phone, diagnosis, assignedNurseIds.toString());
                count++;
            }

        }
    }

    //Function : Display all patient by input Array
    public void displayPatientAll(ArrayList<Patient> pList) throws ParseException {
        System.out.println("");
        System.out.println("LIST OF PATIENTS");
        System.out.printf("%-4s%-12s%-12s%-12s%-16s%-12s%-20s%-20s%-5s\n", "No.", "Patient Id", "Age", "Gender", "Admission Date", "Full name", "Phone", "Diagnosis", "Assigned Nurse");

        int count = 1;
        for (Patient patient : pList) {
            String admissionDate = patient.getAdmissionDate();
            String fullName = patient.getName();
            String phone = patient.getPhone();
            String diagnosis = patient.getDiagnosis();
            StringBuilder assignedNurseIds = new StringBuilder();

            for (Nurse nurse : patient.getNurseAssigned()) {
                if (assignedNurseIds.length() > 0) {
                    assignedNurseIds.append(", ");
                }
                assignedNurseIds.append(nurse.getStaffID());
            }

            System.out.printf("%-4s%-12s%-12d%-12s%-16s%-12s%-20s%-20s%-5s\n", count, patient.getId(), patient.getAge(), patient.getGender(), admissionDate, fullName, phone, diagnosis, assignedNurseIds.toString());
            count++;
        }
    }
}

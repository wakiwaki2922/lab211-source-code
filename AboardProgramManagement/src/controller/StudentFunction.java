/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import model.Student;

/**
 *
 * @author kyrov
 */
public class StudentFunction {

    private HashSet<Student> studentList = new HashSet<>();
    private Validator vl = new Validator();

    public HashSet<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(HashSet<Student> studentList) {
        this.studentList = studentList;
    }

    public StudentFunction(String filepath) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 7) {
                    String id = data[0];
                    String name = data[1];
                    String major = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String passport = data[5];
                    String address = data[6];

                    Student student = new Student(id, name, major, email, phone, passport, address);
                    studentList.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }
    }

    public StudentFunction() {
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //save students data
    public void saveStudents() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Students.txt"));
            StringBuilder sb = new StringBuilder();
            for (Student student : studentList) {
                sb.append(student.getId()).append(",");
                sb.append(student.getName()).append(",");
                sb.append(student.getMajor()).append(",");
                sb.append(student.getEmail()).append(",");
                sb.append(student.getPhone()).append(",");
                sb.append(student.getPassport()).append(",");
                sb.append(student.getAddress()).append("\n");
            }

            bw.write(sb.toString());
            bw.close();

            System.out.println("Students data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: Save students data fail!");
        }
    }

    //Function 1: display all students
    public void displayAll() {
        if (!studentList.isEmpty()) {
            System.out.printf("%-10s %-10s %-10s %-20s %-10s %-10s %-10s\n", "ID", "NAME", "MAJOR", "EMAIL", "PHONE", "PASSPORT", "ADDRESS");
            for (Student s : studentList) {
                s.display();
            }
        } else {
            System.out.println("List is null");
        }

    }

    //Function 1.5: display an imported sID
    public void display(String id) {
        Student s = findByID(id);
        s.display();
    }

    //Function 2: add a new student
    public void add() {
        System.out.println("Adding a student:");

        String id = vl.inputSID("Enter student ID (XXXX): ");
        String name = vl.inputString("Enter student name: ");
        String major = vl.inputMajor("Enter student major ( SE,SB,GD or MC): ");
        String email = vl.inputEmail("Enter student email (@fpt.edu.vn): ");
        String phone = vl.inputString("Enter student phone number: ");
        String passport = vl.inputString("Enter student passport number: ");
        String address = vl.inputString("Enter student address: ");

        Student student = new Student(id, name, major, email, phone, passport, address);
        studentList.add(student);

        System.out.println("Student added successfully.");
    }

    //Function 3: edit information by id
    public void edit() {
        String id = vl.inputSID("Enter the ID of the student to edit: ");

        Student student = findByID(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        boolean continueEditing = true;

        while (continueEditing) {
            System.out.println("Student found:");
            student.display();

            System.out.println("Select a field to edit:");
            System.out.println("1. Name");
            System.out.println("2. Major");
            System.out.println("3. Email");
            System.out.println("4. Phone");
            System.out.println("5. Passport");
            System.out.println("6. Address");
            System.out.println("7. End editing");

            int choice = vl.inputInt("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1:
                    String newName = vl.inputString("Enter the new name: ");
                    student.setName(newName);
                    break;
                case 2:
                    String newMajor = vl.inputMajor("Enter the new major: ");
                    student.setMajor(newMajor);
                    break;
                case 3:
                    String newEmail = vl.inputEmail("Enter the new email: ");
                    student.setEmail(newEmail);
                    break;
                case 4:
                    String newPhone = vl.inputString("Enter the new phone number: ");
                    student.setPhone(newPhone);
                    break;
                case 5:
                    String newPassport = vl.inputString("Enter the new passport number: ");
                    student.setPassport(newPassport);
                    break;
                case 6:
                    String newAddress = vl.inputString("Enter the new address: ");
                    student.setAddress(newAddress);
                    break;
                case 7:
                    continueEditing = false;
                    System.out.println("Editing ended.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            System.out.println("Student with ID " + id + " has been updated.");

            String continueChoice = vl.inputYN("Do you want to update another field? (Y/N): ");

            if (!continueChoice.equalsIgnoreCase("Y")) {
                continueEditing = false;
            }
        }
    }

    //Function 3.5: find student by id,return Student
    public Student findByID(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    //Function 4: back to main menu
}

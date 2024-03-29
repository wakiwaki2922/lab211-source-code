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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import model.AboardProgram;

/**
 *
 * @author kyrov
 */
public class AboardProgramFunction {

    private HashSet<AboardProgram> apList = new HashSet<>();
    private Validator vl = new Validator();

    public HashSet<AboardProgram> getApList() {
        return apList;
    }
    public void setApList(HashSet<AboardProgram> apList) {
        this.apList = apList;
    }

    public AboardProgramFunction(String filepath) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 9) {
                    String id = data[0];
                    String name = data[1];
                    String time = data[2];
                    String startDate = data[3];
                    String endDate = data[4];
                    int days = Integer.parseInt(data[5]);
                    String location = data[6];
                    double cost = Double.parseDouble(data[7]);
                    String content = data[8];
                    
                    AboardProgram program = new AboardProgram(id, name, time, startDate, endDate, days, location, cost, content);
                    apList.add(program);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }    
    }

    public AboardProgramFunction() {
    }
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //save aboard programs
    public void saveAboardPrograms() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("AboardPrograms.txt"));
            for (AboardProgram program : apList) {
                StringBuilder sb = new StringBuilder();
                sb.append(program.getId()).append(",");
                sb.append(program.getName()).append(",");
                sb.append(program.getTime()).append(",");
                sb.append(program.getStartRegDate()).append(",");
                sb.append(program.getEndRegDate()).append(",");
                sb.append(program.getDays()).append(",");
                sb.append(program.getLocation()).append(",");
                sb.append(program.getCost()).append(",");
                sb.append(program.getContent()).append(",");
                bw.write(sb.toString());
            }
            bw.close();
            System.out.println("Aboard programs data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: Save aboard progams data fail!");
        }
    }

    //Function 1: display all aboard programs
    public void displayAll() {
        for (AboardProgram ab : apList) {
            ab.display();
        }
    }

    //Function 2: add a new aboard program
    public void add() throws ParseException {
        System.out.println("Adding an abroad program:");

        String pID = vl.inputPID(apList, "Enter abroad program ID: ");
        String pName = vl.inputString("Enter abroad program name: ");
        String pTime = vl.inputPTime("Enter abroad program time: ");
        String pStartReg = vl.inputADate("Enter abroad program start date register: ");
        String pEndReg = vl.inputBDate("Enter abroad program end date register: ", pStartReg);
        int pDays = vl.inputInt("Enter abroad program days: ", 30, 40);
        double pCost = vl.inputDouble("Enter abroad program cost: ", 5000, 2000000);
        String pContent = vl.inputContent("Enter abroad program content: ");
        String pLocation = vl.inputString("Enter aboard program location: ");

        AboardProgram ab = new AboardProgram(pID, pName, pTime, pStartReg, pEndReg, pDays,pLocation, pCost, pContent);

        apList.add(ab);

        System.out.println("Abroad program added successfully.");
    }

    //Function 3: edit information a program by id
    public void edit() throws ParseException {
        String id = vl.inputPID("Enter the ID of the AboardProgram to edit: ");

        AboardProgram program = findByID(id);
        if (program == null) {
            System.out.println("AboardProgram with ID " + id + " not found.");
            return;
        }

        boolean continueEditing = true;

        while (continueEditing) {
            System.out.println("AboardProgram found:");
            program.display();

            System.out.println("Select a field to edit:");
            System.out.println("1. Name");
            System.out.println("2. Time");
            System.out.println("3. Start Date");
            System.out.println("4. End Date");
            System.out.println("5. Days");
            System.out.println("6. Location");
            System.out.println("7. Cost");
            System.out.println("8. Content");
            System.out.println("9. End editing");

            int choice = vl.inputInt("Enter your choice: ", 1, 8);

            switch (choice) {
                case 1:
                    String newName = vl.inputString("Enter the new name: ");
                    program.setName(newName);
                    break;
                case 2:
                    String newTime = vl.inputPTime("Enter the new time: ");
                    program.setTime(newTime);
                    break;
                case 3:
                    String newStartDate = vl.inputADate("Enter the new start date: ");
                    program.setStartRegDate(newStartDate);
                    break;
                case 4:
                    String newEndDate = vl.inputBDate("Enter the new end date: ", program.getStartRegDate());
                    program.setEndRegDate(newEndDate);
                    break;
                case 5:
                    int newDays = vl.inputInt("Enter the new number of days: ", 30, 40);
                    program.setDays(newDays);
                    break;
                case 6:
                    String newLocation = vl.inputString("Enter the new location: ");
                    program.setLocation(newLocation);
                    break;
                case 7:
                    double newCost = vl.inputDouble("Enter the new cost: ", 5000, 2000000);
                    program.setCost(newCost);
                    break;
                case 8:
                    String newContent = vl.inputContent("Enter the new content: ");
                    program.setContent(newContent);
                    break;
                case 9:
                    continueEditing = false;
                    System.out.println("Editing ended.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            System.out.println("AboardProgram with ID " + id + " has been updated.");

            String continueChoice = vl.inputYN("Do you want to update another field? (Y/N): ");

            if (!continueChoice.equalsIgnoreCase("Y")) {
                continueEditing = false;
            }
        }
    }

    //Function 3.5: find aboard program by id, return AboardProgram
    public AboardProgram findByID(String id) {
        for (AboardProgram program : apList) {
            if (program.getId().equals(id)) {
                return program;
            }
        }
        return null;
    }

    //Function 4: search and display a program by id
    public void displayByID() {
        String str = vl.inputPID("Enter a program's ID to display: ");
        AboardProgram ap = findByID(str);
    }

    //Function 5: back to main menu
}

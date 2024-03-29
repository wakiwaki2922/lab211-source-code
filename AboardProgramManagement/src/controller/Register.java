/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import model.AboardProgram;
import model.Student;

/**
 *
 * @author kyrov
 */
public class Register {

    private Validator vl = new Validator();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private AboardProgramFunction apf = new AboardProgramFunction();
    private StudentFunction sf = new StudentFunction();

    public void registerS() throws IOException {
        System.out.println("Register Menu: ");
        boolean check;
        //Enter program id
        apf.displayAll();
        check = true;
        String pID = "";
        while (check) {
            pID = vl.inputPID("Enter program ID: ");
            for (AboardProgram ap : apf.getApList()) {
                if (pID.equalsIgnoreCase(ap.getId())) {
                    check = false;
                    break;
                }
            }
        }
        //Enter student id
        sf.displayAll();
        check = true;
        String sID = "";
        while (check) {
            sID = vl.inputSID("Enter student ID: ");
            for (Student s : sf.getStudentList()) {
                if (sID.equalsIgnoreCase(s.getId())) {
                    check = false;
                    break;
                }
            }
        }
        AboardProgram ap = apf.findByID(pID);
        Student s = sf.findByID(sID);
        //Enter registration date
        String regDate = vl.inputDateBetween("Enter registration date: ", ap.getStartRegDate(), ap.getEndRegDate());
        //Enter parents's email
        String parentEmail = vl.inputString("Enter parent email: ");
        //Enter parents's phone
        String parentPhone = vl.inputString("Enter parent phone: ");

        saveFile(s, ap, regDate, parentEmail, parentPhone);
    }

    private void saveFile(Student a, AboardProgram b, String rDate, String pEmail, String pPhone) throws IOException {
        String fname = "register/" + a.getId() + "_" + b.getId() + ".doc";
        File yourFile = new File(fname);

        FileWriter fw = new FileWriter(yourFile);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.format("%50s%n", "Aboard Program Registration Form"));
        bw.write("Information Student:\n");
        bw.write(String.format("%-25s %-30s%n", "Student id:  " + a.getId(), "Student name: " + a.getName()));
        bw.write(String.format("%-25s %-30s%-25s %-25s%n", "Major: " + a.getMajor(), "Email: " + a.getEmail(), "Phone:" + a.getPhone(), "Passport" + a.getPassport()));
        bw.write(String.format("%-25s %-30s %-30s%n", "Address: " + a.getAddress(), "Email of the parents:   " + pEmail, "Phone of the parents:" + pPhone));
        bw.write("Information of the aboard program:\n");
        bw.write(String.format("%-25s %-30s%n", "Program’s id: " + b.getId(), "Program’s name: " + b.getName()));
        bw.write(String.format("%-25s %-10s %-10s %-10s%n", "Time: " + b.getTime(), "Days" + b.getDays(), "Location" + b.getLocation(), "Cost" + b.getCost()));
        bw.write("Information of the registration:\n");
        bw.write(String.format("%-25s %-30s%n", "registration date:", rDate));

        bw.close();
        fw.close();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author kyrov
 */
public class Report {

    private Validator vl = new Validator();
    private StudentFunction sf = new StudentFunction();
    private AboardProgramFunction apf = new AboardProgramFunction();

    //take list of files in register folder
    private String[] getListFile() {
        File files = new File("register");
        return files.list();
    }

    //find list of filenames by id
    public ArrayList<String> getFnameByID(String id) {
        ArrayList<String> listFname = new ArrayList<>();
        String[] filenames = getListFile();
        for (String s : filenames) {
            String[] a = s.split("_");
            if (a[0].equals(id));
            listFname.add(s);
        }
        return listFname;
    }

    //display list of imported filenames
    public void display(ArrayList<String> filenames) throws FileNotFoundException, IOException {
        for (String filename : filenames) {
            String fname = "register/" + filename;
            BufferedReader br = new BufferedReader(new FileReader(fname));
            while (br.readLine() != null) {
                System.out.println(br.readLine());
            }
        }
    }

    //list of registered student with number of program
    private HashMap<String, Integer> getStudentRegCount() {
        HashMap<String, Integer> studentReg = new HashMap<>();
        String[] filenames = getListFile();

        for (String filename : filenames) {
            String[] parts = filename.split("_");
            String studentID = parts[0];
            studentReg.put(studentID, studentReg.getOrDefault(studentID, 0) + 1);
        }

        return studentReg;
    }

    //Function 1: print out the registration by student id
    public void displayRegBySID() throws IOException {
        String sID = vl.inputSID("Enter student's id: ");
        ArrayList<String> listFname = getFnameByID(sID);
        if (!listFname.isEmpty()) {
            display(listFname);
        } else {
            System.out.println("Student don't have any register.");
        }

    }

    //Function 2: print out the student that registered more than 2 program
    public void displayStudentReg(){
        HashMap<String,Integer> studentReg = getStudentRegCount();
        System.out.printf("%-50s\n","STUDENTS REGISTRATION MORE THAN 2 PROGRAMs");
        System.out.printf("%-10s %-10s %-10s %-20s %-10s %-10s %-10s\n", "ID", "NAME", "MAJOR", "EMAIL", "PHONE", "PASSPORT", "ADDRESS");
        for(Map.Entry<String, Integer> entry : studentReg.entrySet()){
            String sID = entry.getKey();
            int programCount = entry.getValue();
            if(programCount >= 2){
                sf.display(sID);
            }
        }
    }
    
    //Function 3: count student that registered the program
    public void countStudentByPID(){
        String pID = vl.inputPID("Enter program ID: ");
       ArrayList<String> listFind = new ArrayList();
        String[] filenames = getListFile();
        int count = 0;
        for (String s : filenames) {
            String[] a = s.split("_");
            String[] b=a[1].split(Pattern.quote("."));
            
            if(b[0].equals(pID)){
                count++;
            }
        }
        System.out.println("The program "+pID+" have "+count+" registers");
    }
}

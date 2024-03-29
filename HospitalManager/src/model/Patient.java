/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.HospitalFunction;
import controller.Validator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kyrov
 */
public class Patient extends Person implements Serializable {
    private static final Validator vl = new Validator();
    //Field
    private String id;
    private String diagnosis;
    private String admissionDate;
    private String dischargeDate;
    ArrayList<Nurse> nurseAssigned = new ArrayList<>();

    //Constructor//
    public Patient() {
    }

    public Patient(String id, String name, int age, String gender, String address, String phone, String diagnosis, String admissionDate, String dischargeDate) {
        super(name, age, gender, address, phone);
        this.id = id;
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    //Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public ArrayList<Nurse> getNurseAssigned() {
        return nurseAssigned;
    }

    public void setNurseAssigned(ArrayList<Nurse> nurseAssigned) {
        this.nurseAssigned = nurseAssigned;
    }
    //Overide toString()

    @Override
    public String toString() {
        return id + "," +this.getName()+", "+this.getAge()+", "+this.getGender()+", "+this.getAddress()+", "+this.getPhone()+ "," + diagnosis + "," + admissionDate + "," + dischargeDate;
    }

    public ArrayList<Nurse> inputNurseAssigned(HashMap<String,Nurse> nurseList) {
        for (int count = 0; count < 2; count++) {
            String staffID = vl.inputString("Enter Nurse's Staff ID: ");
            if (nurseList.containsKey(staffID)) {
                Nurse nurse = nurseList.get(staffID);
                if (nurse.getPatientAssigned().size() < 2) {
                    nurseAssigned.add(nurse);
                    nurse.getPatientAssigned().add(this);
                    System.out.println("Nurse assigned successfully");
                } else {
                    System.out.println("This Nurse has reached the maximum patient limit.");
                    count--;
                }
            } else {
                System.out.println("Nurse with the given Staff ID does not exist.");
                count--;
            }
            if(nurseAssigned.size()==2){
            break;
        }
        }
        return nurseAssigned;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Enums.DentistStetus;

/**
 *
 * @author ASUS
 */
public class Dentist {

    private int id;
    private String fullName;
    private String specialization;
    private String contactNumber;
    private DentistStetus stetus;

    public Dentist(String fullName, String specialization, String contactNumber, DentistStetus stetus) {
        this.fullName = fullName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.stetus = stetus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public DentistStetus getStetus() {
        return stetus;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setStetus(DentistStetus stetus) {
        this.stetus = stetus;
    }

    @Override
    public String toString() {
        return "Dentists{" + "id=" + id + ", fullName=" + fullName + ", specialization=" + specialization + ", contactNumber=" + contactNumber + ", stetus=" + stetus + '}';
    }
    
}

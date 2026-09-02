/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.DentistStetus;

/**
 *
 * @author ASUS
 */
public class DentiestDTO {
    private int id;
    private String fullName;
    private String specialization;
    private String contactNumber;
    private String email;
    private DentistStetus stetus;

    public DentiestDTO(String fullName, String specialization, String contactNumber, String email, DentistStetus stetus) {
        this.fullName = fullName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.email = email;
        this.stetus = stetus;
    }

    public DentiestDTO(int id, String fullName, String specialization, String contactNumber, DentistStetus stetus) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.stetus = stetus;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public DentistStetus getStetus() {
        return stetus;
    }

    @Override
    public String toString() {
        return "DentistDTO{" + "id=" + id + ", fullName=" + fullName + ", specialization=" + specialization + ", contactNumber=" + contactNumber + ", stetus=" + stetus + '}';
    }
}

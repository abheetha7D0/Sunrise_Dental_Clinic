/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Patient {

    private int id;
    private String fullName;
    private String address;
    private String contactNumber;

    public Patient(String fullName, String address, String contactNumber) {
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public Patient(int id, String fullName, String address, String contactNumber) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", fullName=" + fullName + ", address=" + address + ", contactNumber=" + contactNumber + '}';
    }
    
}

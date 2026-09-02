/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Bill {
    
    private int id;
    private String billNumber; 
    private int appointmentId;       
    private double consultationFee; 
    private double treatmentFee;    
    private double discount;        
    private double totalFee;        
    private String billDate; 

    public Bill(int appointmentId,String billNumber, double consultationFee, double treatmentFee, double discount) {
        this.appointmentId = appointmentId;
        this.billNumber =  billNumber;
        this.consultationFee = consultationFee;
        this.treatmentFee = treatmentFee;
        this.discount = discount;
        this.totalFee = (consultationFee + treatmentFee) - discount;
    }

    public int getId() {
        return id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTreatmentFee() {
        return treatmentFee;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setTreatmentFee(double treatmentFee) {
        this.treatmentFee = treatmentFee;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    
    
}

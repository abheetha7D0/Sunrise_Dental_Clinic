/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ASUS
 */
public class BillDTO {
    
    private int appointmentId;
    private String appointmentNumber;
    private double consultationFee;
    private double treatmentCost;
    private double discount;

    public BillDTO() {
    }
    
    public BillDTO(int appointmentId, double consultationFee, double treatmentCost, double discount) {
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.treatmentCost = treatmentCost;
        this.discount = discount;
    }

    public BillDTO(String appointmentNumber, double consultationFee, double treatmentCost, double discount) {
        this.appointmentNumber = appointmentNumber;
        this.consultationFee = consultationFee;
        this.treatmentCost = treatmentCost;
        this.discount = discount;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}

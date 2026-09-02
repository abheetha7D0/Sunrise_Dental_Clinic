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
    private double TreatmentCost;
    private double discount;

    public BillDTO(int appointmentId, double consultationFee, double TreatmentCost, double discount) {
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.TreatmentCost = TreatmentCost;
        this.discount = discount;
    }

    public BillDTO(String appointmentNumber, double consultationFee, double TreatmentCost, double discount) {
        this.appointmentNumber = appointmentNumber;
        this.consultationFee = consultationFee;
        this.TreatmentCost = TreatmentCost;
        this.discount = discount;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTreatmentCost() {
        return TreatmentCost;
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

    public void setTreatmentCost(double TreatmentCost) {
        this.TreatmentCost = TreatmentCost;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}

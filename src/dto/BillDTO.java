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
    
    private int id;
    private int billNumber;
    private double consultationFee;
    private double TreatmentCost;
    private String billDate;

    public BillDTO(int id, int billNumber, double consultationFee, double TreatmentCost, String billDate) {
        this.id = id;
        this.billNumber = billNumber;
        this.consultationFee = consultationFee;
        this.TreatmentCost = TreatmentCost;
        this.billDate = billDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setTreatmentCost(double TreatmentCost) {
        this.TreatmentCost = TreatmentCost;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public int getId() {
        return id;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTreatmentCost() {
        return TreatmentCost;
    }

    public String getBillDate() {
        return billDate;
    }
}

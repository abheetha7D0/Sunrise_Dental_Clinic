/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ASUS
 */
public class TreatmentDTO {

    private int id;
    private double treatmentCost;
    private String tretmentName;
    private String description;

    public TreatmentDTO(double treatmentCost, String tretmentName, String description) {
        this.treatmentCost = treatmentCost;
        this.tretmentName = tretmentName;
        this.description = description;
    }

    public TreatmentDTO(int id, double treatmentCost, String tretmentName, String description) {
        this.id = id;
        this.treatmentCost = treatmentCost;
        this.tretmentName = tretmentName;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public void setTretmentName(String tretmentName) {
        this.tretmentName = tretmentName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public String getTretmentName() {
        return tretmentName;
    }

    public String getDescription() {
        return description;
    }

}

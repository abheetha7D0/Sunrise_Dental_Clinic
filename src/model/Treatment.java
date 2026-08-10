/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Treatment {
    
    private int id;
    private double treatmentCost;
    private String tretmentName;
    private String description;

    public Treatment(double treatmentCost, String tretmentType, String description) {
        this.treatmentCost = treatmentCost;
        this.tretmentName = tretmentType;
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

    @Override
    public String toString() {
        return "Treatment{" + "id=" + id + ", treatmentCost=" + treatmentCost + ", tretmentType=" + tretmentName + ", description=" + description + '}';
    }
    
    
}

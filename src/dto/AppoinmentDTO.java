/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.AppointmentStetus;

/**
 *
 * @author ASUS
 */
public class AppoinmentDTO {
    private int id;
    private int Appointment_number;
    private int patientId;
    private int dentistId;
    private int treatmentId;
    private String AppointmentDate;
    private String AppointmentTime;
    private AppointmentStetus Stetus;

    public AppoinmentDTO(int id, int Appointment_number, int patientId, int dentistId, int treatmentId, String AppointmentDate, String AppointmentTime, AppointmentStetus Stetus) {
        this.id = id;
        this.Appointment_number = Appointment_number;
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.treatmentId = treatmentId;
        this.AppointmentDate = AppointmentDate;
        this.AppointmentTime = AppointmentTime;
        this.Stetus = Stetus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppointment_number(int Appointment_number) {
        this.Appointment_number = Appointment_number;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public void setAppointmentDate(String AppointmentDate) {
        this.AppointmentDate = AppointmentDate;
    }

    public void setAppointmentTime(String AppointmentTime) {
        this.AppointmentTime = AppointmentTime;
    }

    public void setStetus(AppointmentStetus Stetus) {
        this.Stetus = Stetus;
    }

    public int getId() {
        return id;
    }

    public int getAppointment_number() {
        return Appointment_number;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDentistId() {
        return dentistId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public AppointmentStetus getStetus() {
        return Stetus;
    }
    
    
}

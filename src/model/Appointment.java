/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Enums.AppointmentStetus;

/**
 *
 * @author ASUS
 */
public class Appointment {

    private int id;
    private int Appointment_number;
    private String Appointment_date;
    private String Appointment; 
    private String time;
    private AppointmentStetus Stetus;

    public Appointment(int Appointment_number, String Appointment_date, String Appointment, String time, AppointmentStetus Stetus) {
        this.Appointment_number = Appointment_number;
        this.Appointment_date = Appointment_date;
        this.Appointment = Appointment;
        this.time = time;
        this.Stetus = Stetus;
    }

    public int getId() {
        return id;
    }

    public int getAppointment_number() {
        return Appointment_number;
    }

    public String getAppointment_date() {
        return Appointment_date;
    }

    public String getAppointment() {
        return Appointment;
    }

    public String getTime() {
        return time;
    }

    public AppointmentStetus getStetus() {
        return Stetus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppointment_number(int Appointment_number) {
        this.Appointment_number = Appointment_number;
    }

    public void setAppointment_date(String Appointment_date) {
        this.Appointment_date = Appointment_date;
    }

    public void setAppointment(String Appointment) {
        this.Appointment = Appointment;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStetus(AppointmentStetus Stetus) {
        this.Stetus = Stetus;
    }  
}

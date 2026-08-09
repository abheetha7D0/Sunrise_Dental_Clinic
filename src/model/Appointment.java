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
    
    
   
}

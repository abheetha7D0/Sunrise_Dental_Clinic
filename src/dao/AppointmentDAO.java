/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import model.Appointment;
import model.Dentiest;

/**
 *
 * @author ASUS
 */
public interface AppointmentDAO {
    
    Appointment crreateAppoinment(Appointment Appoinment);

    Dentiest findByDentiestName(String name);
    
    Dentiest findBySpecialization(String specialization);

}

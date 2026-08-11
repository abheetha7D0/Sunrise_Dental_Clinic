/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import model.Appointment;

/**
 *
 * @author ASUS
 */
public interface AppointmentDAO extends SuperDAO{

    Appointment crreateAppoinment(Appointment Appoinment);

    Appointment updateAppoinment(Appointment Appoinment);

    Appointment cancelAppoinment(Appointment Appoinment);

    Appointment findByAppoinmentNum(int appoinmentNum);

}

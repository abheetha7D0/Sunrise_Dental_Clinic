/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.SQLException;
import model.Appointment;

/**
 *
 * @author ASUS
 */
public interface AppointmentDAO extends SuperDAO{

    Appointment crreateAppoinment(Appointment Appoinment)  throws SQLException;

    Appointment updateAppoinment(Appointment Appoinment)  throws SQLException;

    Appointment cancelAppoinment(Appointment Appoinment)  throws SQLException;

    Appointment findByAppoinmentNum(int appoinmentNum)  throws SQLException;

}

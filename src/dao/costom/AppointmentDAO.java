/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import dto.AppoinmentDTO;
import java.sql.SQLException;
import java.util.List;
import model.Appointment;

/**
 *
 * @author ASUS
 */
public interface AppointmentDAO extends SuperDAO {

    List<AppoinmentDTO> getAllAppointments() throws SQLException ;

    boolean createAppoinment(Appointment Appoinment) throws SQLException;

    boolean updateAppoinment(Appointment Appoinment) throws SQLException;

    boolean cancelAppoinment(String appoinmentNumber) throws SQLException;

    Appointment findByAppoinmentNum(String appoinmentNum) throws SQLException;

    List<Appointment> getAllAppoinment() throws SQLException;

    String generateNextAppointmentNumber() throws SQLException;
}

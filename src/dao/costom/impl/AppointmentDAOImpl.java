/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import Enums.AppointmentStetus;
import java.sql.PreparedStatement;
import java.sql.Connection;
import dao.costom.AppointmentDAO;
import db.DBConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import java.sql.ResultSet;
/**
 *
 * @author ASUS
 */
public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public boolean createAppoinment(Appointment appoinment) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO Appointment(appointment_number,patient_id,dentist_id,treatment_id,appointment_date,appointment_time,status) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, appoinment.getAppointment_number());

        pst.setInt(2, appoinment.getPatientId());

        pst.setInt(3, appoinment.getDentistId());

        pst.setInt(4, appoinment.getTreatmentId());

        pst.setString(5, appoinment.getAppointmentDate());

        pst.setString(6, appoinment.getAppointmentTime());

        pst.setString(7, appoinment.getStetus().name());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean updateAppoinment(Appointment Appoinment) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cancelAppoinment(Appointment Appoinment) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Appointment findByAppoinmentNum(int appoinmentNum) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Appointment> getAllAppoinment() throws SQLException {
        ResultSet rs;

        java.sql.Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM Appoinment";
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String appointment_number = rs.getString(2);
            int patient_id = rs.getInt(3);
            int dentist_id = rs.getInt(4);
            int treatment_id = rs.getInt(5);
            String appointment_date = rs.getString(6);
            String appointment_time = rs.getString(7);
            AppointmentStetus status = AppointmentStetus.valueOf(rs.getString(8));

            Appointment appointment = new Appointment(id, appointment_number, patient_id, dentist_id, treatment_id,appointment_date,appointment_time,status);
            appointmentList.add(appointment);
        }
        con.close();
        return appointmentList;
    }

}

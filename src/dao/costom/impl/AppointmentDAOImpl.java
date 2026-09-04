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
import dto.AppoinmentDTO;
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

        String sql = "INSERT INTO appointments(appointment_number,patient_id,dentist_id,treatment_id,appointment_date,appointment_time,status) VALUES(?,?,?,?,?,?,?)";

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
    public boolean updateAppoinment(Appointment appoinment) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE appointments SET appointment_number=?,patient_id=?,dentist_id=?,treatment_id=?,appointment_date=?,appointment_time=?,status=? WHERE id=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, appoinment.getAppointment_number());

        pst.setInt(2, appoinment.getPatientId());

        pst.setInt(3, appoinment.getDentistId());

        pst.setInt(4, appoinment.getTreatmentId());

        pst.setString(5, appoinment.getAppointmentDate());

        pst.setString(6, appoinment.getAppointmentTime());

        pst.setString(7, appoinment.getStetus().name());

        pst.setInt(8, appoinment.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean cancelAppoinment(String appoinmentNumber) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE appointments SET status=? WHERE appointment_number=?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, AppointmentStetus.CANCELED.name());
        pst.setString(2, appoinmentNumber);
        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public Appointment findByAppoinmentNum(String appoinmentNum) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM appointments WHERE appointment_number=?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, appoinmentNum);
        ResultSet executeQuery = pst.executeQuery();

        Appointment appointment = null;

        if (executeQuery.next()) {
            String statusStr = executeQuery.getString(8);
            AppointmentStetus status = null;

            if (statusStr != null) {
                status = AppointmentStetus.valueOf(statusStr.toUpperCase());
            }

            appointment = new Appointment(executeQuery.getInt(1), executeQuery.getString(2), executeQuery.getInt(3), executeQuery.getInt(4), executeQuery.getInt(5), executeQuery.getString(6), executeQuery.getString(7), status);

        }
        con.close();
        return appointment;
    }

    @Override
    public List<Appointment> getAllAppoinment() throws SQLException {
        ResultSet rs;

        java.sql.Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM appointments";
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

            Appointment appointment = new Appointment(id, appointment_number, patient_id, dentist_id, treatment_id, appointment_date, appointment_time, status);
            appointmentList.add(appointment);
        }
        con.close();
        return appointmentList;
    }

    @Override
    public String generateNextAppointmentNumber() throws SQLException {
        String sql = "SELECT id FROM appointments ORDER BY id DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int lastId = rs.getInt(1);
                return String.format("APT-%03d", lastId + 1);
            } else {
                return "APT-001";
            }
        }
    }

    @Override
    public List<AppoinmentDTO> getAllAppointments() throws SQLException {
        List<AppoinmentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(new AppoinmentDTO(
                        rs.getInt("id"),
                        rs.getString("appointment_number"),
                        rs.getInt("patient_id"),
                        rs.getInt("dentist_id"),
                        rs.getInt("treatment_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        AppointmentStetus.valueOf(rs.getString("status"))
                ));
            }
        }
        return list;
    }

    @Override
    public boolean isDentistBooked(int dentistId, String date, String time) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE dentist_id = ? AND appointment_date = ? AND appointment_time = ? AND status != 'CANCELED'";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, dentistId);
            pst.setDate(2, java.sql.Date.valueOf(date));
            pst.setTime(3, java.sql.Time.valueOf(time));

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isDentistBookedExcludingCurrent(int dentistId, String date, String time, String appNum) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE dentist_id = ? AND appointment_date = ? AND appointment_time = ? AND appointment_number != ? AND status != 'CANCELED'";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, dentistId);
            pst.setString(2, date);
            pst.setString(3, time);
            pst.setString(4, appNum);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public Object[] getAppointmentDetailsForBilling(String appoinmentNumber) throws SQLException {
        String sql = "SELECT a.id, p.full_name AS patient_name, d.full_name AS dentist_name, t.treatment_cost "
                + "FROM appointments a "
                + "JOIN patients p ON a.patient_id = p.id "
                + "JOIN dentists d ON a.dentist_id = d.id "
                + "JOIN treatments t ON a.treatment_id = t.id "
                + "WHERE a.appointment_number = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, appoinmentNumber);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("dentist_name"),
                        rs.getDouble("treatment_cost")
                    };
                }
            }
        }
        return null;
    }
}

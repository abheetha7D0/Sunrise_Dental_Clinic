/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import dao.costom.PatientDAO;
import db.DBConnection;
import model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean addPatient(Patient patient) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO Patient(name,adress,contact_number) VALUES(?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, patient.getFullName());

        pst.setString(2, patient.getAddress());

        pst.setString(3, patient.getContactNumber());

        int executeUpdate = pst.executeUpdate();
        con.close();
        System.out.println("Dentist Added");
        return executeUpdate > 0;
    }

    @Override
    public boolean updatePatient(Patient patient) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "UPDATE Patient SET name=?,address=?,contact_number=? WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, patient.getFullName());

        pst.setString(2, patient.getAddress());

        pst.setString(3, patient.getContactNumber());

        pst.setInt(4, patient.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public Patient findByPatientName(String name)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deletePatient(int id)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Patient findByPatientId(int id) throws SQLException{
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from dentists where id=?");
        pst.setObject(1, id);

        ResultSet rst = pst.executeQuery();

        Patient patient = null;

        if (rst.next()) {

            patient = new Patient(rst.getString(2), rst.getString(3),rst.getString(4));
            patient.setId(id);
        }
        con.close();
        return patient;
    }

}

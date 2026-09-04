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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean addPatient(Patient patient) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO Patients(full_name,address,contact_number,email) VALUES(?,?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, patient.getFullName());

        pst.setString(2, patient.getAddress());

        pst.setString(3, patient.getContactNumber());

        pst.setString(4, patient.getEmail());

        int executeUpdate = pst.executeUpdate();
        con.close();
        System.out.println("Dentist Added");
        return executeUpdate > 0;
    }

    @Override
    public boolean updatePatient(Patient patient) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "UPDATE Patients SET full_name=?,address=?,contact_number=?,email=? WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, patient.getFullName());

        pst.setString(2, patient.getAddress());

        pst.setString(3, patient.getContactNumber());
        
        pst.setString(4, patient.getEmail());
        
        pst.setInt(5, patient.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public Patient findByPatientName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deletePatient(int id) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM Patients WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setInt(1, id);
        int executeUpdate = pst.executeUpdate();
        con.close();

        return executeUpdate > 0;
    }

    @Override
    public Patient findByPatientId(int id) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from Patients where id=?");
        pst.setObject(1, id);

        ResultSet rst = pst.executeQuery();

        Patient patient = null;

        if (rst.next()) {

            patient = new Patient(rst.getString(2), rst.getString(3), rst.getString(4));
            patient.setId(id);
        }
        con.close();
        return patient;
    }

    @Override
    public List<Patient> getALLPatients() throws SQLException {
        ResultSet rs = null;

        java.sql.Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM Patients";
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        List<Patient> dentistList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String number = rs.getString(4);
            String email = rs.getString(5);

            Patient dentiest = new Patient(id, name, address, number,email);
            dentistList.add(dentiest);
        }
        con.close();
        return dentistList;
    }

    @Override
    public String getPatientEmailById(int id) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("SELECT email FROM Patients WHERE id = ?");

        pst.setObject(1, id);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getString("email");
        }
        return null;
    }

}

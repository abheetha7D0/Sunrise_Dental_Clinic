/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dao.costom.DentistDAO;
import db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Dentist;

/**
 *
 * @author ASUS
 */
public class DentistDAOImpl implements DentistDAO {

    @Override
    public ResultSet getALLDentists() {
        ResultSet rs = null;

        try {

            java.sql.Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM dentists";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return rs;
    }

    @Override
    public boolean addDentist(Dentist dentist) throws SQLException {

        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO dentists(name,specialization,contact_number,status) VALUES(?,?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, dentist.getFullName());

        pst.setString(2, dentist.getSpecialization());

        pst.setString(3, dentist.getContactNumber());

        pst.setString(4, dentist.getStetus().name());

        int executeUpdate = pst.executeUpdate();
        con.close();
        System.out.println("Dentist Added");
        return executeUpdate > 0;

    }

    @Override
    public Dentist updateDentist(Dentist patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Dentist deleteDentist(Dentist patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Dentist findByDentistName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Dentist findBySpecialization(String specialization) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

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
    public Dentist addDentist(Dentist patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

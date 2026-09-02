/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import Enums.DentistStetus;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dao.costom.DentistDAO;
import db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import model.Dentist;

/**
 *
 * @author ASUS
 */
public class DentistDAOImpl implements DentistDAO {

    @Override
    public List<Dentist> getALLDentists() throws SQLException {

        ResultSet rs = null;

        java.sql.Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM dentists";
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        List<Dentist> dentistList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String specialization = rs.getString(3);
            String number = rs.getString(4);
            DentistStetus status = DentistStetus.valueOf(rs.getString(5));

            Dentist dentiest = new Dentist(id, name, specialization, number, status);
            dentistList.add(dentiest);
        }
        con.close();
        return dentistList;
    }

    @Override
    public boolean addDentist(Dentist dentist) throws SQLException {

        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO dentists(name,specialization,contact_number,email,status) VALUES(?,?,?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, dentist.getFullName());

        pst.setString(2, dentist.getSpecialization());

        pst.setString(3, dentist.getContactNumber());

        pst.setString(4, dentist.getEmail());

        pst.setString(5, dentist.getStetus().name());

        int executeUpdate = pst.executeUpdate();
        con.close();
        System.out.println("Dentist Added");
        return executeUpdate > 0;

    }

    @Override
    public boolean updateDentist(Dentist dentist) throws SQLException {

        Connection con = (Connection) DBConnection.getConnection();

        String sql = "UPDATE dentists SET name=?,specialization=?,contact_number=?,status=? WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, dentist.getFullName());

        pst.setString(2, dentist.getSpecialization());

        pst.setString(3, dentist.getContactNumber());

        pst.setString(4, dentist.getEmail());

        pst.setString(5, dentist.getStetus().name());

        pst.setInt(5, dentist.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;

    }

    @Override
    public boolean deleteDentist(int id) throws SQLException {

        Connection con = (Connection) DBConnection.getConnection();

        String sql = "DELETE FROM dentists WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setInt(1, id);
        int executeUpdate = pst.executeUpdate();
        con.close();

        return executeUpdate > 0;
    }

    @Override
    public Dentist findByDentistName(String name) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from dentists where name=?");
        pst.setObject(1, name);

        ResultSet rst = pst.executeQuery();

        Dentist dentist = null;

        if (rst.next()) {
            String statusStr = rst.getString(5);
            DentistStetus status = null;

            if (statusStr != null) {
                status = DentistStetus.valueOf(statusStr.toUpperCase());
            }
            dentist = new Dentist(rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getString(5), status);
            dentist.setId(rst.getInt(1));
        }
        con.close();
        return dentist;
    }

    @Override
    public Dentist findBySpecialization(String specialization) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from dentists where specialization=?");
        pst.setObject(1, specialization);

        ResultSet rst = pst.executeQuery();

        Dentist dentist = null;

        if (rst.next()) {
            String statusStr = rst.getString(5);
            DentistStetus status = null;

            if (statusStr != null) {
                status = DentistStetus.valueOf(statusStr.toUpperCase());
            }
            dentist = new Dentist(rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getString(5), status);
            dentist.setId(rst.getInt(1));
        }
        con.close();
        return dentist;
    }

    @Override
    public Dentist findByDentistId(int id) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from dentists where id=?");
        pst.setObject(1, id);

        ResultSet rst = pst.executeQuery();

        Dentist dentist = null;

        if (rst.next()) {
            String statusStr = rst.getString(5);
            DentistStetus status = null;

            if (statusStr != null) {
                status = DentistStetus.valueOf(statusStr.toUpperCase());
            }
            dentist = new Dentist(rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getString(5), status);
            dentist.setId(id);
        }
        con.close();
        return dentist;
    }

    @Override
    public String getDentistEmailById(int id) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("SELECT email FROM dentists WHERE id = ?");

        pst.setObject(1, id);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getString("email");
        }
        return null;

    }

}

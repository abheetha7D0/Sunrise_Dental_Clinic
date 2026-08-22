/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import dao.costom.TreatmentDAO;
import db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public class TreatmentDAOImpl implements TreatmentDAO {

    @Override
    public boolean createTreatment(Treatment treatment) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO treatment(price,name,description) VALUES(?,?,?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setDouble(1, treatment.getTreatmentCost());

        pst.setString(2, treatment.getTretmentName());

        pst.setString(3, treatment.getDescription());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean updateTreatment(Treatment treatment) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE treatment SET price=?,name=?,description=? WHERE id=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setDouble(1, treatment.getTreatmentCost());

        pst.setString(2, treatment.getTretmentName());

        pst.setString(3, treatment.getDescription());

        pst.setInt(4, treatment.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean deleteTreatment(int id) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM treatment WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setInt(1, id);
        int executeUpdate = pst.executeUpdate();
        con.close();

        return executeUpdate > 0;
    }

    @Override
    public Treatment findByTreatmentName(String treatment) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultSet getALLTreatments() throws SQLException {
        ResultSet rs = null;

        java.sql.Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM treatment";
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        rs = pst.executeQuery();

        return rs;
    }

    @Override
    public Treatment findByTreatmentId(int id) throws SQLException {
       Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from Treatment where id=?");
        pst.setObject(1, id);

        ResultSet rst = pst.executeQuery();

        Treatment treatment = null;

        if (rst.next()) {

            treatment = new Treatment(rst.getDouble(4), rst.getString(2), rst.getString(3));
            treatment.setId(id);
        }
        con.close();
        return treatment;
    }

}

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
import java.util.ArrayList;
import java.util.List;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public class TreatmentDAOImpl implements TreatmentDAO {

    @Override
    public boolean createTreatment(Treatment treatment) throws SQLException {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO treatments(treatment_cost,treatment_name,description) VALUES(?,?,?)";

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

        String sql = "UPDATE treatments SET treatment_cost=?,treatment_name=?,description=? WHERE id=?";

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

        String sql = "DELETE FROM treatments WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setInt(1, id);
        int executeUpdate = pst.executeUpdate();
        con.close();

        return executeUpdate > 0;
    }

    @Override
    public Treatment findByTreatmentName(String name) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from treatments where treatment_name=?");
        pst.setObject(1, name);

        ResultSet rst = pst.executeQuery();

        Treatment treatment = null;

        if (rst.next()) {

            treatment = new Treatment(rst.getDouble(4), rst.getString(2), rst.getString(3));
            treatment.setId(rst.getInt(4));
        }
        con.close();
        return treatment;
    }

    @Override
    public List<Treatment> getALLTreatments() throws SQLException {
        List<Treatment> treatmentList = new ArrayList<>();

        String sql = "SELECT id, treatment_name, treatment_cost, description FROM treatments";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("treatment_name");
                double cost = rs.getDouble("treatment_cost");
                String description = rs.getString("description");

                Treatment treatment = new Treatment(id, cost, name, description);
                treatmentList.add(treatment);
            }
        }

        return treatmentList;
    }

    @Override
    public Treatment findByTreatmentId(int id) throws SQLException {
        String sql = "SELECT id, treatment_cost, treatment_name, description FROM treatments WHERE id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    double cost = rst.getDouble("treatment_cost");
                    String name = rst.getString("treatment_name");
                    String description = rst.getString("description");
                    Treatment treatment = new Treatment(id, cost, name, description);
                    return treatment;
                }
            }
        }
        return null;
    }

}

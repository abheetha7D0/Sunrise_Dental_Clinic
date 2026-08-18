/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dao.costom.TreatmentDAO;
import db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public class TreatmentDAOImpl implements TreatmentDAO{

    @Override
    public boolean createTreatment(Treatment treatment) throws SQLException{
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO treatment(treatmentCost,tretmentName,description) VALUES(?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setDouble(1, treatment.getTreatmentCost());

        pst.setString(2, treatment.getTretmentName());

        pst.setString(3, treatment.getDescription());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean updateTreatment(Treatment treatment)  throws SQLException{
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "UPDATE treatment SET treatmentCost=?,tretmentName=?,description=? WHERE id=?";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setDouble(1, treatment.getTreatmentCost());

        pst.setString(2, treatment.getTretmentName());

        pst.setString(3, treatment.getDescription());

        pst.setInt(4, treatment.getId());

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public boolean deleteTreatment(Treatment treatment)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Treatment findByTreatmentName(String treatment)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultSet getALLTretments() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

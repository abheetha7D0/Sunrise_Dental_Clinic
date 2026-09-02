/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dao.costom.BillDAO;
import db.DBConnection;
import java.sql.SQLException;
import model.Bill;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class BillDAOImpl implements BillDAO {

    @Override
    public boolean saveBill(Bill bill) throws SQLException {
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO bills (bill_number, appointment_id, consultation_fee, treatment_fee, discount, total_fee) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, bill.getBillNumber());
        pst.setInt(2, bill.getAppointmentId());
        pst.setDouble(3, bill.getConsultationFee());
        pst.setDouble(4, bill.getTreatmentFee());
        pst.setDouble(5, bill.getDiscount());
        pst.setDouble(6, bill.getTotalFee());

        int executeUpdate = pst.executeUpdate();
        con.close();
        System.out.println("Dentist Added");
        return executeUpdate > 0;
    }

    @Override
    public String generateNextBillNumber() throws SQLException {
        String sql = "SELECT MAX(id) FROM bills";

        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int maxId = rs.getInt(1);

            if (rs.wasNull()) {
                con.close();
                return "BILL-001";
            }

            int nextId = maxId + 1;
            String formattedId = String.format("BILL-%03d", nextId);

            con.close();
            return formattedId;
        }

        con.close();
        return "BILL-001";
    }

}

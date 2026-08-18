/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.SQLException;
import model.Bill;

/**
 *
 * @author ASUS
 */
public interface BillDAO extends SuperDAO{
    Bill printBill(Bill bill)  throws SQLException;
}

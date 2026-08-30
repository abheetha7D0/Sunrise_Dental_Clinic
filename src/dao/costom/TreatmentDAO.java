/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.SQLException;
import java.util.List;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public interface TreatmentDAO extends SuperDAO{
    
    List<Treatment> getALLTreatments() throws SQLException;

    boolean createTreatment(Treatment treatment) throws SQLException;

    boolean updateTreatment(Treatment treatment)throws SQLException;
    
    boolean deleteTreatment(int id) throws SQLException;
    
    Treatment findByTreatmentId(int id) throws SQLException;

    Treatment findByTreatmentName(String name) throws SQLException;
}

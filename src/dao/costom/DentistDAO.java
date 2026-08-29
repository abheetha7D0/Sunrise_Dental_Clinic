/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.ResultSet;
import model.Dentist;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface DentistDAO extends SuperDAO {

    List<Dentist> getALLDentists() throws SQLException;
    
    boolean addDentist(Dentist dentist) throws SQLException ;

    boolean updateDentist(Dentist dentist) throws SQLException;

    boolean deleteDentist(int id) throws SQLException;

    Dentist findByDentistName(String name) throws SQLException;
    
    Dentist findByDentistId(int id) throws SQLException ;

    Dentist findBySpecialization(String specialization) throws SQLException;
}

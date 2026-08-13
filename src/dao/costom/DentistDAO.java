/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.ResultSet;
import model.Dentist;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public interface DentistDAO extends SuperDAO {

    ResultSet getALLDentists();
    
    boolean addDentist(Dentist dentist) throws SQLException ;

    Dentist updateDentist(Dentist dentist);

    Dentist deleteDentist(Dentist dentist);

    Dentist findByDentistName(String name);

    Dentist findBySpecialization(String specialization);
}

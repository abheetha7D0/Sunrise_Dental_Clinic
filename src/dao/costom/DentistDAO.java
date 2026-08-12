/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.awt.List;
import java.sql.ResultSet;
import model.Dentist;

/**
 *
 * @author ASUS
 */
public interface DentistDAO extends SuperDAO {

    ResultSet getALLDentists();
    
    Dentist addDentist(Dentist patient);

    Dentist updateDentist(Dentist patient);

    Dentist deleteDentist(Dentist patient);

    Dentist findByDentistName(String name);

    Dentist findBySpecialization(String specialization);
}

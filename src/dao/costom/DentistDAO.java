/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import model.Dentiest;

/**
 *
 * @author ASUS
 */
public interface DentistDAO extends SuperDAO{

    Dentiest addDentiest(Dentiest patient);

    Dentiest updateDentiest(Dentiest patient);
    
    Dentiest deleteDentiest(Dentiest patient);

    Dentiest findByDentiestName(String name);
    
    Dentiest findBySpecialization(String specialization);
}

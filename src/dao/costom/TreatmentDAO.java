/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public interface TreatmentDAO extends SuperDAO{

    Treatment createTreatment(Treatment treatment);

    Treatment updateTreatment(Treatment treatment);
    
    Treatment deleteTreatment(Treatment treatment);

    Treatment findByTreatmentName(String treatment);
}

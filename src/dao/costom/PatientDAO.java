/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import model.Patient;

/**
 *
 * @author ASUS
 */
public interface PatientDAO extends SuperDAO {

    boolean addPatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(int id);

    Patient findByPatientId(int id);

    Patient findByPatientName(String name);
}

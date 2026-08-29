/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Patient;

/**
 *
 * @author ASUS
 */
public interface PatientDAO extends SuperDAO {
    List<Patient> getALLPatients() throws SQLException;

    boolean addPatient(Patient patient) throws SQLException;

    boolean updatePatient(Patient patient)  throws SQLException;

    boolean deletePatient(int id)  throws SQLException;

    Patient findByPatientId(int id)  throws SQLException;

    Patient findByPatientName(String name)  throws SQLException;
}

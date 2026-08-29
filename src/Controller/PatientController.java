/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import dao.DAOFactory;
import dao.costom.impl.DentistDAOImpl;
import dao.costom.impl.PatientDAOImpl;
import dto.PatientDTO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Patient;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class PatientController {

    PatientDAOImpl dentistDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOType.PATIENT);
    private final DashbordForm dasbordForm;

    public PatientController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public boolean isValidFullName(String name) {
        String fullNameRegex = "^[a-zA-Z]+(['-][a-zA-Z]+)?(\\s+[a-zA-Z]+(['-][a-zA-Z]+)?)+$";
        return name != null && name.trim().matches(fullNameRegex);
    }

    public boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^0\\d{9}$";
        return phone != null && phone.trim().matches(phoneRegex);
    }

    public void save(PatientDTO patient) {
        if (!isValidFullName(patient.getFullName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(patient.getContactNumber())) {
            dasbordForm.showMessage("Please enter a valid 10-digit phone number (e.g., 0771111111)", "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean addPatient = dentistDAO.addPatient(new Patient(patient.getFullName(), patient.getAddress(), patient.getContactNumber()));
            if (addPatient) {
                dasbordForm.showMessage("Patient added successfully.");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Somthing wrong...");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.DentistDAOImpl;
import dao.costom.impl.PatientDAOImpl;
import dto.PatientDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Patient;
import static util.UserSession.getUserRole;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class PatientController {

    PatientDAOImpl patientDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOType.PATIENT);
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

    public List<PatientDTO> getAll() throws SQLException {
        Role userRole = getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return null;
        }
        List<Patient> allDentists = patientDAO.getALLPatients();
        List<PatientDTO> patientList = new ArrayList<>();

        for (Patient dentiest : allDentists) {

            patientList.add(new PatientDTO(
                    dentiest.getId(),
                    dentiest.getFullName(),
                    dentiest.getAddress(),
                    dentiest.getContactNumber()
            ));
        }
        return patientList;
    }

    public void save(PatientDTO patient) {
        Role userRole = getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        if (!isValidFullName(patient.getFullName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(patient.getContactNumber())) {
            dasbordForm.showMessage("Please enter a valid 10-digit phone number (e.g., 0771111111)", "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean addPatient = patientDAO.addPatient(new Patient(patient.getFullName(), patient.getAddress(), patient.getContactNumber()));
            if (addPatient) {
                dasbordForm.showMessage("Patient added successfully.");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Somthing wrong...");
        }
    }

    public void update(PatientDTO patient) {
        Role userRole = getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        if (!isValidFullName(patient.getFullName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(patient.getContactNumber())) {
            dasbordForm.showMessage("Please enter a valid 10-digit phone number (e.g., 0771111111)", "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Patient patientOb = patientDAO.findByPatientId(patient.getId());
            patientOb.setFullName(patient.getFullName());
            patientOb.setContactNumber(patient.getContactNumber());
            patientOb.setAddress(patient.getAddress());
            boolean updatePatient = patientDAO.updatePatient(patientOb);

            if (updatePatient) {
                dasbordForm.showMessage("Patient Updated successfully.");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Somthing wrong...");
        }
    }

    public void delete(int id) {
        Role userRole = getUserRole();
        if (!Role.ADMIN.equals(userRole) ) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        try {
            boolean deletePatient = patientDAO.deletePatient(id);
            if (deletePatient) {
                dasbordForm.showMessage("Patient Delete successfully.");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Somthing wrong...");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

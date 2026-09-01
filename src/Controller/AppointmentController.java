/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.AppointmentDAOImpl;
import dao.costom.impl.DentistDAOImpl;
import dao.costom.impl.PatientDAOImpl;
import dto.AppoinmentDTO;
import java.sql.SQLException;
import model.Appointment;
import util.EmailUtility;
import util.UserSession;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class AppointmentController {

    DentistDAOImpl dentistDAO = (DentistDAOImpl) DAOFactory.getInstance().getDAO(DAOType.DENTIST);
    PatientDAOImpl patientDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOType.PATIENT);
    AppointmentDAOImpl appointmentDAO = (AppointmentDAOImpl) DAOFactory.getInstance().getDAO(DAOType.APPOINMENT);
    private final DashbordForm dasbordForm;

    public AppointmentController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public void save(AppoinmentDTO appointmentDTO) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }

        try {
            if (appointmentDAO.findByAppoinmentNum(appointmentDTO.getAppointment_number()) != null) {
                dasbordForm.showMessage("Appoinment number Allready Exits");
            }

            boolean createAppoinment = appointmentDAO.createAppoinment(new Appointment(appointmentDTO.getAppointment_number(), appointmentDTO.getPatientId(), appointmentDTO.getDentistId(), appointmentDTO.getTreatmentId(), appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(), appointmentDTO.getStetus()));
            if (createAppoinment) {
                dasbordForm.showMessage("Appoinment added successfully");
            }
            try {

                String patientEmail = patientDAO.getPatientEmailById(appointmentDTO.getPatientId());
                String dentistEmail = dentistDAO.getDentistEmailById(appointmentDTO.getDentistId());

                String subject = "Appointment Confirmation: " + appointmentDTO.getAppointment_number();

                String patientBody = "<h2>Appointment Confirmed!</h2>"
                        + "<p>Dear Patient,</p>"
                        + "<p>Your appointment has been registered with our clinic.</p>"
                        + "<ul>"
                        + "<li><b>Date:</b> " + appointmentDTO.getAppointmentDate() + "</li>"
                        + "<li><b>Time:</b> " + appointmentDTO.getAppointmentTime() + "</li>"
                        + "</ul>"
                        + "<p>Thank you.</p>";

                String dentistBody = "<h2>New Scheduled Appointment</h2>"
                        + "<p>Dear Doctor,</p>"
                        + "<p>A new appointment has been booked for you in the system.</p>"
                        + "<ul>"
                        + "<li><b>Date:</b> " + appointmentDTO.getAppointmentDate() + "</li>"
                        + "<li><b>Time:</b> " + appointmentDTO.getAppointmentTime() + "</li>"
                        + "</ul>";

                if (patientEmail != null && !patientEmail.trim().isEmpty()) {
                    EmailUtility.sendEmail(patientEmail, subject, patientBody);
                }
                if (dentistEmail != null && !dentistEmail.trim().isEmpty()) {
                    EmailUtility.sendEmail(dentistEmail, subject, dentistBody);
                }

            } catch (Exception ex) {

                System.err.println("Database succeeded, but notification dispatch failed: " + ex.getMessage());
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Appoinment added unsuccessfully");
        }
    }

    public void update(AppoinmentDTO appointmentDTO) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }

        try {
            Appointment appoinment = appointmentDAO.findByAppoinmentNum(appointmentDTO.getAppointment_number());

            appoinment.setPatientId(appointmentDTO.getPatientId());
            appoinment.setDentistId(appointmentDTO.getDentistId());
            appoinment.setTreatmentId(appointmentDTO.getTreatmentId());
            appoinment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appoinment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appoinment.setStetus(appointmentDTO.getStetus());
            boolean updateDentist = appointmentDAO.updateAppoinment(appoinment);

            if (updateDentist) {
                dasbordForm.showMessage("Appoinment Updated successfully");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Appoinment Updated unsuccessfully");
        }
    }

    public void cancelAppoinment(String appoinmentNumber) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }

        try {
            /*if (appointmentDAO.findByAppoinmentNum(appoinmentNumber) == null) {
            dasbordForm.showMessage("Check Appoinment number");
            }*/
            boolean cancelAppoinment = appointmentDAO.cancelAppoinment(appoinmentNumber);

            if (cancelAppoinment) {
                dasbordForm.showMessage("Check Appoinment number");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Check Appoinment number");
        }
    }
}

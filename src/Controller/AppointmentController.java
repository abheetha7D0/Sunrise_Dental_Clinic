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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
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

    public List<AppoinmentDTO> getAll() {
        try {
            return appointmentDAO.getAllAppointments();
        } catch (SQLException ex) {
            System.err.println("Error fetching appointments: " + ex.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public String formatToSqlTime(String rawTime) {
        if (rawTime == null || rawTime.trim().isEmpty()) {
            return null;
        }

        String input = rawTime.trim().toUpperCase();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("[H:m:s]"
                        + "[H:m]"
                        + "[h:m:s a]"
                        + "[h:m a]"
                        + "[H]")
                .toFormatter(Locale.ENGLISH);

        try {
            LocalTime time = LocalTime.parse(input, formatter);
            return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return null; 
        }
    }

    public void save(AppoinmentDTO appointmentDTO) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }

        try {
            if (appointmentDAO.findByAppoinmentNum(appointmentDTO.getAppointment_number()) != null) {
                dasbordForm.showMessage("Appointment number already exists!");
                return; // Halt execution
            }

            boolean isBooked = appointmentDAO.isDentistBooked(
                    appointmentDTO.getDentistId(),
                    appointmentDTO.getAppointmentDate(),
                    appointmentDTO.getAppointmentTime()
            );

            if (isBooked) {
                dasbordForm.showMessage("The selected dentist already has an appointment scheduled at this date and time!");
                return;
            }

            Appointment appointment = new Appointment(
                    appointmentDTO.getAppointment_number(),
                    appointmentDTO.getPatientId(),
                    appointmentDTO.getDentistId(),
                    appointmentDTO.getTreatmentId(),
                    appointmentDTO.getAppointmentDate(),
                    appointmentDTO.getAppointmentTime(),
                    appointmentDTO.getStetus()
            );

            boolean createAppoinment = appointmentDAO.createAppoinment(appointment);

            if (createAppoinment) {
                dasbordForm.showMessage("Appointment added successfully");
            } else {
                dasbordForm.showMessage("Failed to create appointment.");
                return;
            }

            try {
                String patientEmail = patientDAO.getPatientEmailById(appointmentDTO.getPatientId());
                String subject = "Appointment Confirmation: " + appointmentDTO.getAppointment_number();

                String patientBody = "<h2>Appointment Confirmed!</h2>"
                        + "<p>Dear Patient,</p>"
                        + "<p>Your appointment has been registered with our clinic.</p>"
                        + "<ul>"
                        + "<li><b>Date:</b> " + appointmentDTO.getAppointmentDate() + "</li>"
                        + "<li><b>Time:</b> " + appointmentDTO.getAppointmentTime() + "</li>"
                        + "</ul>"
                        + "<p>Thank you.</p>";

                if (patientEmail != null && !patientEmail.trim().isEmpty()) {
                    EmailUtility.sendEmail(patientEmail, subject, patientBody);
                }
            } catch (Exception ex) {
                System.err.println("Database succeeded, but notification dispatch failed: " + ex.getMessage());
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Appointment added unsuccessfully");
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

            if (appoinment == null) {
                dasbordForm.showMessage("Appointment not found!");
                return;
            }

            boolean isBooked = appointmentDAO.isDentistBookedExcludingCurrent(
                    appointmentDTO.getDentistId(),
                    appointmentDTO.getAppointmentDate(),
                    appointmentDTO.getAppointmentTime(),
                    appointmentDTO.getAppointment_number()
            );

            if (isBooked) {
                dasbordForm.showMessage("The selected dentist already has another appointment scheduled at this date and time!");
                return;
            }

            appoinment.setPatientId(appointmentDTO.getPatientId());
            appoinment.setDentistId(appointmentDTO.getDentistId());
            appoinment.setTreatmentId(appointmentDTO.getTreatmentId());
            appoinment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appoinment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appoinment.setStetus(appointmentDTO.getStetus());

            boolean updateDentist = appointmentDAO.updateAppoinment(appoinment);

            if (updateDentist) {
                dasbordForm.showMessage("Appointment Updated successfully");
            } else {
                dasbordForm.showMessage("Failed to update appointment.");
                return;
            }

            try {
                String patientEmail = patientDAO.getPatientEmailById(appointmentDTO.getPatientId());
                String subject = "Appointment Updated: " + appointmentDTO.getAppointment_number();

                String patientBody = "<h2>Appointment Updated!</h2>"
                        + "<p>Dear Patient,</p>"
                        + "<p>Your appointment details have been updated as follows:</p>"
                        + "<ul>"
                        + "<li><b>Date:</b> " + appointmentDTO.getAppointmentDate() + "</li>"
                        + "<li><b>Time:</b> " + appointmentDTO.getAppointmentTime() + "</li>"
                        + "<li><b>Status:</b> " + appointmentDTO.getStetus() + "</li>"
                        + "</ul>"
                        + "<p>Thank you.</p>";

                if (patientEmail != null && !patientEmail.trim().isEmpty()) {
                    EmailUtility.sendEmail(patientEmail, subject, patientBody);
                }
            } catch (Exception ex) {
                System.err.println("Database succeeded, but notification dispatch failed: " + ex.getMessage());
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Appointment Updated unsuccessfully");
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

    public String getNextAppointmentNumber() {
        try {
            return appointmentDAO.generateNextAppointmentNumber();
        } catch (SQLException ex) {
            System.err.println("Error generating appointment number: " + ex.getMessage());
            return "APT-001";
        }
    }

    public Object[] getAppointmentDetailsForBilling(String appNum) {
        try {
            return appointmentDAO.getAppointmentDetailsForBilling(appNum);
        } catch (SQLException ex) {
            System.err.println("Error fetching appointment billing details: " + ex.getMessage());
            return null;
        }
    }
}

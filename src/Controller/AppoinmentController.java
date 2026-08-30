/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.AppointmentDAOImpl;
import dto.AppoinmentDTO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Appointment;
import model.Dentist;
import util.UserSession;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class AppoinmentController {

    AppointmentDAOImpl appointmentDAO = (AppointmentDAOImpl) DAOFactory.getInstance().getDAO(DAOType.APPOINMENT);
    private final DashbordForm dasbordForm;

    public AppoinmentController(DashbordForm dasbordForm) {
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

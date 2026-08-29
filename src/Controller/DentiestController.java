/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.DentistDAOImpl;
import dto.DentiestDTO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Dentist;
import view.DashbordForm;
import java.util.*;
import util.UserSession;

/**
 *
 * @author ASUS
 */
public class DentiestController {

    DentistDAOImpl dentistDAO = (DentistDAOImpl) DAOFactory.getInstance().getDAO(DAOType.DENTIST);
    private final DashbordForm dasbordForm;

    public DentiestController(DashbordForm dasbordForm) {
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

    public List<DentiestDTO> getAll() throws SQLException {
        String userName = UserSession.getUserName();
        Role userRole = UserSession.getUserRole();
        System.out.println(userRole);
        System.out.println(userName);
        System.out.println(!Role.ADMIN.equals(userRole) && !Role.DENTIST.equals(userRole));

        if (!Role.ADMIN.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return null;
        }

        List<Dentist> allDentists = dentistDAO.getALLDentists();
        List<DentiestDTO> dentistList = new ArrayList<>();

        for (Dentist dentiest : allDentists) {

            dentistList.add(new DentiestDTO(
                    dentiest.getId(),
                    dentiest.getFullName(),
                    dentiest.getSpecialization(),
                    dentiest.getContactNumber(),
                    dentiest.getStetus()
            ));
        }
        return dentistList;
    }

    public void save(DentiestDTO dentiestDto) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }

        if (!isValidFullName(dentiestDto.getFullName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(dentiestDto.getContactNumber())) {
            dasbordForm.showMessage("Please enter a valid 10-digit phone number (e.g., 0771111111)", "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean addDentist = dentistDAO.addDentist(new Dentist(dentiestDto.getFullName(), dentiestDto.getSpecialization(), dentiestDto.getContactNumber(), dentiestDto.getStetus()));
            if (addDentist) {
                dasbordForm.showMessage("Dentist added successfully.");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Somthing wrong...");
        }
    }

    public void update(DentiestDTO dentiestDto) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        if (!isValidFullName(dentiestDto.getFullName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(dentiestDto.getContactNumber())) {
            dasbordForm.showMessage("Please enter a valid 10-digit phone number (e.g., 0771111111)", "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Dentist dentistOb = dentistDAO.findByDentistId(dentiestDto.getId());

            dentistOb.setFullName(dentiestDto.getFullName());
            dentistOb.setContactNumber(dentiestDto.getContactNumber());
            dentistOb.setSpecialization(dentiestDto.getSpecialization());
            dentistOb.setStetus(dentiestDto.getStetus());
            boolean updateDentist = dentistDAO.updateDentist(dentistOb);

            if (updateDentist) {
                dasbordForm.showMessage("Dentist Updated successfully.");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Somthing wrong...");
        }
    }

    public void delete(int id) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        try {
            boolean deleteDentist = dentistDAO.deleteDentist(id);
            if (deleteDentist) {
                dasbordForm.showMessage("Dentist Delete successfully.");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Somthing wrong...");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

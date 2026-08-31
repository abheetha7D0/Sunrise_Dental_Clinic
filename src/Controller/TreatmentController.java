/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.TreatmentDAOImpl;
import dto.TreatmentDTO;
import java.sql.SQLException;
import java.util.*;
import model.Treatment;
import util.UserSession;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class TreatmentController {

    TreatmentDAOImpl treatmentDAO = (TreatmentDAOImpl) DAOFactory.getInstance().getDAO(DAOType.TREATMENT);
    private final DashbordForm dasbordForm;

    public TreatmentController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public void save(TreatmentDTO treatment) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        try {
            boolean addPatient = treatmentDAO.createTreatment(new Treatment(treatment.getTreatmentCost(), treatment.getTretmentName(), treatment.getDescription()));
            if (addPatient) {
                dasbordForm.showMessage("Treatment added successfully");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Treatment added unsuccessfully");
        }
    }

    public List<TreatmentDTO> getAll() {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return null;
        }
        List<Treatment> allTreatments = null;
        try {
            allTreatments = treatmentDAO.getALLTreatments();
        } catch (SQLException ex) {
            dasbordForm.showMessage("Get All Treatment Null");
        }

        List<TreatmentDTO> dentistList = new ArrayList<>();

        for (Treatment treatment : allTreatments) {

            dentistList.add(new TreatmentDTO(
                    treatment.getId(),
                    treatment.getTreatmentCost(),
                    treatment.getTretmentName(),
                    treatment.getDescription()
            ));
        }

        return dentistList;
    }

    public Treatment getTreatmentById(int id) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return null;
        }
        try {
            return treatmentDAO.findByTreatmentId(id);
        } catch (SQLException ex) {
            dasbordForm.showMessage("Cant find Treatment by id");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }

    public void update(TreatmentDTO treatment) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        try {
            Treatment treatmentOb = treatmentDAO.findByTreatmentId(treatment.getId());
            treatmentOb.setTretmentName(treatment.getTretmentName());
            treatmentOb.setDescription(treatment.getDescription());
            treatmentOb.setTreatmentCost(treatment.getTreatmentCost());
            boolean updateTreatment = treatmentDAO.updateTreatment(treatmentOb);

            if (updateTreatment) {
                dasbordForm.showMessage("Treatment Updated successfully");
            }

        } catch (SQLException ex) {
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            dasbordForm.showMessage("Treatment Updated unsuccessfully");
        }
    }

    public void delete(int id) {
        Role userRole = UserSession.getUserRole();
        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole) && !Role.DENTIST.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        try {
            boolean deleteTreatment = treatmentDAO.deleteTreatment(id);
            if (deleteTreatment) {
                dasbordForm.showMessage("Treatment Delete successfully");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Treatment Delete unsuccessfully");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Enums.DAOType;
import dao.costom.impl.AppointmentDAOImpl;
import dao.costom.impl.BillDAOImpl;
import dao.costom.impl.DentistDAOImpl;
import dao.costom.impl.PatientDAOImpl;
import dao.costom.impl.TreatmentDAOImpl;
import dao.costom.impl.UserDAOImpl;

/**
 *
 * @author ASUS
 */
public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (null == daoFactory) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case APPOINMENT:
                return new AppointmentDAOImpl();
            case BILL:
                return new BillDAOImpl();
            case DENTIST:
                return new DentistDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            case TRETMENT:
                return new TreatmentDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}

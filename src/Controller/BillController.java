/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import dao.DAOFactory;
import dao.costom.impl.BillDAOImpl;
import dto.BillDTO;
import java.sql.SQLException;
import model.Bill;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class BillController {

    BillDAOImpl billDAO = (BillDAOImpl) DAOFactory.getInstance().getDAO(DAOType.BILL);
    private DashbordForm dasbordForm;

    public BillController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public void save(BillDTO billDTO) {
        try {
            String automaticBillNumber = billDAO.generateNextBillNumber();

            double consultation = billDTO.getConsultationFee();
            double treatment = billDTO.getTreatmentCost();
            double discount = billDTO.getDiscount();
            double calculatedTotal = (consultation + treatment) - discount;

            if (calculatedTotal < 0) {
                dasbordForm.showMessage("Error: Discount cannot be greater than the charges.");
                return;
            }

            Bill bill = new Bill(
                    billDTO.getAppointmentId(),
                    automaticBillNumber,
                    consultation,
                    treatment,
                    discount
            );

            bill.setTotalFee(calculatedTotal);

            boolean isSaved = billDAO.saveBill(bill);

            if (isSaved) {
                dasbordForm.showMessage("Receipt " + automaticBillNumber + " generated successfully!");
            } else {
                dasbordForm.showMessage("Transaction failed: Could not record transaction properties.");
            }

        } catch (SQLException ex) {
            System.err.println("Fatal execution crash in bill processing: " + ex.getMessage());
            dasbordForm.showMessage("Database Transaction Error: Failed to finalize billing record.");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import dao.DAOFactory;
import dao.costom.impl.BillDAOImpl;
import dao.costom.impl.PatientDAOImpl;
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
    PatientDAOImpl patientDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOType.PATIENT);
    private DashbordForm dasbordForm;

    public BillController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public void save(BillDTO billDTO, String patientName, String dentistName) {
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
                String currentDate = java.time.LocalDate.now().toString();

                // 1. Generate Final Receipt HTML Content
                String receiptHtml = util.ReceiptGenerator.buildHtmlReceipt(
                        automaticBillNumber,
                        billDTO,
                        calculatedTotal,
                        currentDate,
                        patientName,
                        dentistName
                );

                // 2. Update GUI Live Preview
                dasbordForm.setReceiptPreview(receiptHtml);
                dasbordForm.showMessage("Receipt " + automaticBillNumber + " generated successfully!");

                // 3. Dispatch Email to Patient
                try {
                    // Fetch patient email using appointment ID or DAO
                    String patientEmail = patientDAO.getPatientEmailByAppointmentId(billDTO.getAppointmentId());

                    if (patientEmail != null && !patientEmail.trim().isEmpty()) {
                        String subject = "Invoice Statement - " + automaticBillNumber + " | Sunrise Dental";
                        util.EmailUtility.sendEmail(patientEmail, subject, receiptHtml);
                        System.out.println("Receipt email sent successfully to: " + patientEmail);
                    } else {
                        System.err.println("Bill saved, but patient email is missing.");
                    }
                } catch (Exception ex) {
                    System.err.println("Bill saved, but email dispatch failed: " + ex.getMessage());
                }

            } else {
                dasbordForm.showMessage("Transaction failed: Could not record transaction properties.");
            }

        } catch (SQLException ex) {
            System.err.println("Fatal execution crash in bill processing: " + ex.getMessage());
            dasbordForm.showMessage("Database Transaction Error: Failed to finalize billing record.");
        }
    }
}

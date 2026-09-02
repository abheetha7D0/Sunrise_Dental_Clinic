/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dto.BillDTO;

/**
 *
 * @author ASUS
 */
public class ReceiptGenerator {

    public static String buildHtmlReceipt(String billNumber, BillDTO dto, double totalFee, String date) {
        return "<html>"
                + "<body style='font-family: Arial, sans-serif; padding: 20px; color: #333;'>"
                + "  <div style='text-align: center; border-bottom: 2px solid #007bff; padding-bottom: 10px;'>"
                + "    <h2 style='margin: 0; color: #007bff;'>SUNRISE DENTAL CLINIC</h2>"
                + "    <p style='margin: 5px 0; font-size: 12px;'>Invoice Statement</p>"
                + "  </div>"
                + "  <div style='margin: 20px 0; font-size: 14px;'>"
                + "    <b>Invoice No:</b> " + billNumber + "<br>"
                + "    <b>Date:</b> " + date + "<br>"
                + "    <b>Appointment ID:</b> " + dto.getAppointmentId() + "<br>"
                + "  </div>"
                + "  <table style='width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 14px;'>"
                + "    <tr style='background-color: #f4f4f4;'>"
                + "      <th style='text-align: left; padding: 8px; border: 1px solid #ddd;'>Description</th>"
                + "      <th style='text-align: right; padding: 8px; border: 1px solid #ddd;'>Amount</th>"
                + "    </tr>"
                + "    <tr>"
                + "      <td style='padding: 8px; border: 1px solid #ddd;'>Consultation Fee</td>"
                + "      <td style='text-align: right; padding: 8px; border: 1px solid #ddd;'>" + String.format("%.2f", dto.getConsultationFee()) + "</td>"
                + "    </tr>"
                + "    <tr>"
                + "      <td style='padding: 8px; border: 1px solid #ddd;'>Treatment Fee</td>"
                + "      <td style='text-align: right; padding: 8px; border: 1px solid #ddd;'>" + String.format("%.2f", dto.getTreatmentCost()) + "</td>"
                + "    </tr>"
                + "    <tr>"
                + "      <td style='padding: 8px; border: 1px solid #ddd; color: #d9534f;'>Discount Deduction</td>"
                + "      <td style='text-align: right; padding: 8px; border: 1px solid #ddd; color: #d9534f;'>-" + String.format("%.2f", dto.getDiscount()) + "</td>"
                + "    </tr>"
                + "    <tr style='font-weight: bold; background-color: #f9f9f9;'>"
                + "      <td style='padding: 8px; border: 1px solid #ddd; font-size: 16px;'>Total Payable Balance</td>"
                + "      <td style='text-align: right; padding: 8px; border: 1px solid #ddd; font-size: 16px; color: #28a745;'>" + String.format("%.2f", totalFee) + "</td>"
                + "    </tr>"
                + "  </table>"
                + "  <br><div style='text-align: center; font-size: 12px; color: #777; margin-top: 30px;'>Thank you for choosing Sunrise Dental!</div>"
                + "</body>"
                + "</html>";
    }
}

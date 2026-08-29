/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.DentiestController;
import Controller.PatientController;
import Controller.TreatmentController;
import Controller.UserController;
import Enums.DAOType;
import Enums.DentistStetus;
import dao.DAOFactory;
import dao.costom.impl.DentistDAOImpl;
import dao.costom.impl.PatientDAOImpl;
import dao.costom.impl.TreatmentDAOImpl;
import dao.costom.impl.UserDAOImpl;
import dto.DentiestDTO;
import dto.PatientDTO;
import dto.TreatmentDTO;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import model.Dentist;
import model.Patient;
import model.Treatment;

/**
 *
 * @author ASUS
 */
public class DashbordForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashbordForm.class.getName());
    private final DentiestController dentiestController;
    private final PatientController patientController;
    private final TreatmentController treatmentController;

    final void showDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        jLblDate.setText(simpleDateFormat.format(date));
    }

    final void showTime() {
        new Timer(0, (ActionEvent e) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm a");
            Date date = new Date();
            jLblTime.setText(simpleDateFormat.format(date));
        }).start();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public boolean isValidFullName(String name) {
        String fullNameRegex = "^[a-zA-Z]+(['-][a-zA-Z]+)?(\\s+[a-zA-Z]+(['-][a-zA-Z]+)?)+$";
        return name != null && name.trim().matches(fullNameRegex);
    }

    public boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^0\\d{9}$";
        return phone != null && phone.trim().matches(phoneRegex);
    }
    
    
    private void clearInputs() {
        jTxtPatientName.setText("");
        jTxtPatientAddress.setText("");
        jTxtPatientContactNum.setText("");

        jTxtDentistName.setText("");
        jTxtDentistSpec.setText("");
        jTxtDentistContactNum.setText("");

        jTxtTreatmentName.setText("");
        jTxtTreatmentDescription.setText("");
        jTxtTreatmentPrice.setText("");

        if (jCmbDentistStetus.getItemCount() > 0) {
            jCmbDentistStetus.setSelectedIndex(0);
        }

        jTblDentist.clearSelection();

        jTblPatient.clearSelection();

        jTblTreatment.clearSelection();

    }

    private void checkInputs() {
        String name = jTxtDentistName.getText().trim();
        String spec = jTxtDentistSpec.getText().trim();
        String phone = jTxtDentistContactNum.getText().trim();

        boolean isDataEnterd = !name.isEmpty()
                || !spec.isEmpty()
                || !phone.isEmpty()
                || phone.length() == 10;

        boolean isAllDataEnterd = !name.isEmpty()
                && !spec.isEmpty()
                && !phone.isEmpty()
                && phone.length() == 10;

        jBtnDentistSave.setEnabled(isAllDataEnterd);
        jBtnDentistUpdate.setEnabled(isAllDataEnterd);
        jBtnDentistDelete.setEnabled(isAllDataEnterd);
        jBtnDentistCancel.setEnabled(isDataEnterd);
        if (jTblDentist.getSelectedRow() > 0) {
            jBtnDentistSave.setEnabled(false);
        }

        String patient_name = jTxtPatientName.getText().trim();
        String patient_adress = jTxtPatientAddress.getText().trim();
        String patient_contact_number = jTxtPatientContactNum.getText().trim();

        boolean isPatientDataEnterd = !patient_name.isEmpty()
                || !patient_adress.isEmpty()
                || !patient_contact_number.isEmpty()
                || patient_contact_number.length() == 10;

        boolean isPatientAllDataEnterd = !patient_name.isEmpty()
                && !patient_adress.isEmpty()
                && !patient_contact_number.isEmpty()
                && patient_contact_number.length() == 10;

        jBtnPatientSave.setEnabled(isPatientAllDataEnterd);
        jBtnPatientUpdate.setEnabled(isPatientAllDataEnterd);
        jBtnPatientDelete.setEnabled(isPatientAllDataEnterd);
        jBtnPatientCancel.setEnabled(isPatientDataEnterd);

        if (jTblPatient.getSelectedRow() > 0) {
            jBtnPatientSave.setEnabled(false);
        }

        String treatmentName = jTxtTreatmentName.getText().trim();
        String treatmentDescription = jTxtTreatmentDescription.getText().trim();
        String treatmentPrice = jTxtTreatmentPrice.getText().trim();

        boolean isTreatmentDataEnterd = !treatmentName.isEmpty()
                || !treatmentDescription.isEmpty()
                || !treatmentPrice.isEmpty();

        boolean isTreatmentAllDataEnterd = !treatmentName.isEmpty()
                && !treatmentDescription.isEmpty()
                && !treatmentPrice.isEmpty();

        jBtnTreatmentSave.setEnabled(isTreatmentAllDataEnterd);
        jBtnTreatmentUpdate.setEnabled(isTreatmentAllDataEnterd);
        jBtnTreatmentDelete.setEnabled(isTreatmentAllDataEnterd);
        jBtnTreatmentCancel.setEnabled(isTreatmentDataEnterd);

        if (jTblTreatment.getSelectedRow() > 0) {
            jBtnTreatmentSave.setEnabled(false);
        }
    }

    /**
     * Creates new form DashbordForm
     */
    public DashbordForm() {
        initComponents();
        
        dentiestController = new DentiestController(this);
        patientController = new PatientController(this);
        treatmentController = new TreatmentController(this);
        showDate();
        showTime();
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        viewAllDentist();
        viewAllPatient();
        viewAllTreatment();

    }

    final void viewAllDentist() {
        try {
            List<DentiestDTO> allDentists = dentiestController.getAll();

            DefaultTableModel model = (DefaultTableModel) jTblDentist.getModel();
            model.setRowCount(0);

            for (DentiestDTO dentiest : allDentists) {

                model.addRow(new Object[]{
                    dentiest.getId(),
                    dentiest.getFullName(),
                    dentiest.getSpecialization(),
                    dentiest.getContactNumber(),
                    dentiest.getStetus()
                });
            }

        } catch (SQLException ex) {
            System.out.println("somthing wrong viewAllDentist");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    final void viewAllPatient() {
        try {
            List<PatientDTO> all = patientController.getAll();
            DefaultTableModel model = (DefaultTableModel) jTblPatient.getModel();
            model.setRowCount(0);

            for (PatientDTO dentiest : all) {

                model.addRow(new Object[]{
                    dentiest.getId(),
                    dentiest.getFullName(),
                    dentiest.getAddress(),
                    dentiest.getContactNumber()
                });
            }
        } catch (SQLException ex) {

            System.out.println("somthing wrong viewAllPatient");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    final void viewAllTreatment() {
        try {
            List<TreatmentDTO> all = treatmentController.getAll();

            DefaultTableModel model = (DefaultTableModel) jTblTreatment.getModel();
            model.setRowCount(0);

            for (TreatmentDTO treatment : all) {

                model.addRow(new Object[]{
                    treatment.getId(),
                    treatment.getTretmentName(),
                    treatment.getDescription(),
                    treatment.getTreatmentCost()
                });
            }
        } catch (SQLException ex) {
            System.out.println("somthing wrong viewAllTreatment");
            System.getLogger(DashbordForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMenuContext = new javax.swing.JPanel();
        jBtnPatient = new javax.swing.JButton();
        jBtnTretment = new javax.swing.JButton();
        jBtnBill = new javax.swing.JButton();
        jBtnSetting = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBtnAppoinmenet = new javax.swing.JButton();
        jBtnDentist = new javax.swing.JButton();
        jPanelMainContext = new javax.swing.JPanel();
        jPanelAppoinmentContext = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTxtDentistName3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jBtnDentistSave3 = new javax.swing.JButton();
        jBtnDentistUpdate3 = new javax.swing.JButton();
        jBtnDentistDelete3 = new javax.swing.JButton();
        jBtnDentistCancel3 = new javax.swing.JButton();
        jCmbPatient = new javax.swing.JComboBox<>();
        jCmbDentist = new javax.swing.JComboBox<>();
        jCmbDentist1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTxtDentistName4 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTxtDentistName5 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblAppoinment = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jCmbAppoinmentStetus = new javax.swing.JComboBox<>();
        jPanelDentistContext = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTxtDentistName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTxtDentistSpec = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jCmbDentistStetus = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jBtnDentistSave = new javax.swing.JButton();
        jBtnDentistUpdate = new javax.swing.JButton();
        jBtnDentistDelete = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblDentist = new javax.swing.JTable();
        jBtnDentistCancel = new javax.swing.JButton();
        jTxtDentistContactNum = new javax.swing.JFormattedTextField();
        jPanelPatientContext = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTxtPatientName = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTxtPatientAddress = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTxtPatientContactNum = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jBtnPatientSave = new javax.swing.JButton();
        jBtnPatientUpdate = new javax.swing.JButton();
        jBtnPatientDelete = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTblPatient = new javax.swing.JTable();
        jBtnPatientCancel = new javax.swing.JButton();
        jPanelTreatmentContext = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jTxtTreatmentName = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTxtTreatmentDescription = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jBtnTreatmentSave = new javax.swing.JButton();
        jBtnTreatmentUpdate = new javax.swing.JButton();
        jBtnTreatmentDelete = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTblTreatment = new javax.swing.JTable();
        jBtnTreatmentCancel = new javax.swing.JButton();
        jTxtTreatmentPrice = new javax.swing.JFormattedTextField();
        jPanelBillsContext = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jTxtDentistName6 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTxtDentistSpec6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTxtDentistContactNum6 = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jBtnDentistSave6 = new javax.swing.JButton();
        jBtnDentistUpdate6 = new javax.swing.JButton();
        jBtnDentistDelete6 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTblTreatment1 = new javax.swing.JTable();
        jBtnDentistCancel6 = new javax.swing.JButton();
        jPanelSettingContext = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTxtDentistName7 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTxtDentistSpec7 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jBtnDentistUpdate7 = new javax.swing.JButton();
        jBtnDentistCancel7 = new javax.swing.JButton();
        jBtnChangePassword = new javax.swing.JLabel();
        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLblDate = new javax.swing.JLabel();
        jLblTime = new javax.swing.JLabel();
        jBtnLogOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1085, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1085, 730));

        jPanelMenuContext.setBackground(new java.awt.Color(0, 153, 204));
        jPanelMenuContext.setForeground(new java.awt.Color(255, 255, 255));

        jBtnPatient.setBackground(new java.awt.Color(0, 102, 204));
        jBtnPatient.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnPatient.setForeground(new java.awt.Color(204, 255, 255));
        jBtnPatient.setText("Patient");
        jBtnPatient.addActionListener(this::jBtnPatientActionPerformed);

        jBtnTretment.setBackground(new java.awt.Color(0, 102, 204));
        jBtnTretment.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnTretment.setForeground(new java.awt.Color(204, 255, 255));
        jBtnTretment.setText("Treatment");
        jBtnTretment.addActionListener(this::jBtnTretmentActionPerformed);

        jBtnBill.setBackground(new java.awt.Color(0, 102, 204));
        jBtnBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnBill.setForeground(new java.awt.Color(204, 255, 255));
        jBtnBill.setText("Bills");
        jBtnBill.addActionListener(this::jBtnBillActionPerformed);

        jBtnSetting.setBackground(new java.awt.Color(0, 102, 204));
        jBtnSetting.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnSetting.setForeground(new java.awt.Color(204, 255, 255));
        jBtnSetting.setText("Setting");
        jBtnSetting.addActionListener(this::jBtnSettingActionPerformed);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/src/icons8-dentist-100.png"))); // NOI18N

        jBtnAppoinmenet.setBackground(new java.awt.Color(0, 102, 204));
        jBtnAppoinmenet.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnAppoinmenet.setForeground(new java.awt.Color(204, 255, 255));
        jBtnAppoinmenet.setText("Appoinment ");
        jBtnAppoinmenet.addActionListener(this::jBtnAppoinmenetActionPerformed);

        jBtnDentist.setBackground(new java.awt.Color(0, 102, 204));
        jBtnDentist.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentist.setForeground(new java.awt.Color(204, 255, 255));
        jBtnDentist.setText("Dentist");
        jBtnDentist.addActionListener(this::jBtnDentistActionPerformed);

        javax.swing.GroupLayout jPanelMenuContextLayout = new javax.swing.GroupLayout(jPanelMenuContext);
        jPanelMenuContext.setLayout(jPanelMenuContextLayout);
        jPanelMenuContextLayout.setHorizontalGroup(
            jPanelMenuContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuContextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMenuContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnTretment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnAppoinmenet, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(jBtnDentist, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanelMenuContextLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMenuContextLayout.setVerticalGroup(
            jPanelMenuContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuContextLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jBtnDentist, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnAppoinmenet, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnTretment, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jPanelMainContext.setBackground(new java.awt.Color(255, 255, 255));

        jPanelAppoinmentContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Appointment Number");

        jTxtDentistName3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistName3.setEnabled(false);
        jTxtDentistName3.addActionListener(this::jTxtDentistName3ActionPerformed);

        jLabel19.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 255));
        jLabel19.setText("Manage Appoinment");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Select Patient ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Select Dentist");

        jBtnDentistSave3.setBackground(new java.awt.Color(0, 102, 255));
        jBtnDentistSave3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistSave3.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistSave3.setText("Save");
        jBtnDentistSave3.addActionListener(this::jBtnDentistSave3ActionPerformed);

        jBtnDentistUpdate3.setBackground(new java.awt.Color(255, 102, 51));
        jBtnDentistUpdate3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistUpdate3.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistUpdate3.setText("Update");
        jBtnDentistUpdate3.addActionListener(this::jBtnDentistUpdate3ActionPerformed);

        jBtnDentistDelete3.setBackground(new java.awt.Color(255, 0, 0));
        jBtnDentistDelete3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistDelete3.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistDelete3.setText("Delete");
        jBtnDentistDelete3.addActionListener(this::jBtnDentistDelete3ActionPerformed);

        jBtnDentistCancel3.setBackground(new java.awt.Color(255, 51, 0));
        jBtnDentistCancel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistCancel3.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistCancel3.setText("Cancel");
        jBtnDentistCancel3.addActionListener(this::jBtnDentistCancel3ActionPerformed);

        jCmbPatient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCmbDentist.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbDentist.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCmbDentist1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbDentist1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("Select Treatment");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel38.setText("Appoinment date");

        jTxtDentistName4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistName4.setEnabled(false);
        jTxtDentistName4.addActionListener(this::jTxtDentistName4ActionPerformed);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setText("Appoinment Time");

        jTxtDentistName5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistName5.setEnabled(false);
        jTxtDentistName5.addActionListener(this::jTxtDentistName5ActionPerformed);

        jTblAppoinment.setBackground(new java.awt.Color(0, 153, 204));
        jTblAppoinment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblAppoinment.setForeground(new java.awt.Color(255, 255, 255));
        jTblAppoinment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Number", "Patient", "Dentiest", "Treatment", "Date", "Tme", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblAppoinment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTblAppoinmentMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTblAppoinment);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel40.setText("Stetus");

        jCmbAppoinmentStetus.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jCmbAppoinmentStetus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AVAILABLE", "UNAVAILABLE", "DEACTIVATED" }));
        jCmbAppoinmentStetus.setToolTipText("Select");

        javax.swing.GroupLayout jPanelAppoinmentContextLayout = new javax.swing.GroupLayout(jPanelAppoinmentContext);
        jPanelAppoinmentContext.setLayout(jPanelAppoinmentContextLayout);
        jPanelAppoinmentContextLayout.setHorizontalGroup(
            jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel19)
                .addContainerGap(246, Short.MAX_VALUE))
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jCmbAppoinmentStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jCmbDentist1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jCmbPatient, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTxtDentistName3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTxtDentistName4, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel39)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jCmbDentist, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtDentistName5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAppoinmentContextLayout.createSequentialGroup()
                                .addComponent(jBtnDentistSave3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistUpdate3)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistDelete3)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistCancel3))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51))))
        );
        jPanelAppoinmentContextLayout.setVerticalGroup(
            jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDentistName4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbDentist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbDentist1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDentistName3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDentistName5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnDentistSave3)
                            .addComponent(jBtnDentistUpdate3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnDentistDelete3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnDentistCancel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmbAppoinmentStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanelDentistContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Name");

        jTxtDentistName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtDentistNameKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 255));
        jLabel14.setText("Manage Dentist");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Specialization");

        jTxtDentistSpec.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistSpec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtDentistSpecKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Contact Number");

        jCmbDentistStetus.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jCmbDentistStetus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AVAILABLE", "UNAVAILABLE", "DEACTIVATED" }));
        jCmbDentistStetus.setToolTipText("Select");
        jCmbDentistStetus.addActionListener(this::jCmbDentistStetusActionPerformed);
        jCmbDentistStetus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCmbDentistStetusKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Stetus");

        jBtnDentistSave.setBackground(new java.awt.Color(0, 102, 255));
        jBtnDentistSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistSave.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistSave.setText("Save");
        jBtnDentistSave.setEnabled(false);
        jBtnDentistSave.addActionListener(this::jBtnDentistSaveActionPerformed);

        jBtnDentistUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnDentistUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistUpdate.setText("Update");
        jBtnDentistUpdate.setEnabled(false);
        jBtnDentistUpdate.addActionListener(this::jBtnDentistUpdateActionPerformed);

        jBtnDentistDelete.setBackground(new java.awt.Color(255, 0, 0));
        jBtnDentistDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistDelete.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistDelete.setText("Delete");
        jBtnDentistDelete.setEnabled(false);
        jBtnDentistDelete.addActionListener(this::jBtnDentistDeleteActionPerformed);

        jTblDentist.setBackground(new java.awt.Color(0, 153, 204));
        jTblDentist.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblDentist.setForeground(new java.awt.Color(255, 255, 255));
        jTblDentist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Specialization", "Contact Number", "Stetus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblDentist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTblDentistMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTblDentist);

        jBtnDentistCancel.setBackground(new java.awt.Color(255, 51, 0));
        jBtnDentistCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistCancel.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistCancel.setText("Cancel");
        jBtnDentistCancel.setEnabled(false);
        jBtnDentistCancel.addActionListener(this::jBtnDentistCancelActionPerformed);

        try {
            jTxtDentistContactNum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("0#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTxtDentistContactNum.addPropertyChangeListener(this::jTxtDentistContactNumPropertyChange);
        jTxtDentistContactNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtDentistContactNumKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelDentistContextLayout = new javax.swing.GroupLayout(jPanelDentistContext);
        jPanelDentistContext.setLayout(jPanelDentistContextLayout);
        jPanelDentistContextLayout.setHorizontalGroup(
            jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3))
                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                .addGap(295, 295, 295)
                                .addComponent(jBtnDentistSave, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistDelete)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistCancel))
                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                        .addGap(213, 213, 213)
                                        .addComponent(jLabel14))
                                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jTxtDentistName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jTxtDentistSpec, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jTxtDentistContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(38, 38, 38)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jCmbDentistStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDentistContextLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanelDentistContextLayout.setVerticalGroup(
            jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jCmbDentistStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDentistContextLayout.createSequentialGroup()
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDentistContextLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18))
                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(21, 21, 21)))
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtDentistContactNum)
                            .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTxtDentistSpec)
                                .addComponent(jTxtDentistName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(22, 22, 22)
                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnDentistSave)
                    .addComponent(jBtnDentistUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1409, Short.MAX_VALUE))
        );

        jPanelPatientContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Name");

        jTxtPatientName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtPatientName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPatientNameKeyReleased(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 255));
        jLabel24.setText("Manage Patient");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("Address");

        jTxtPatientAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtPatientAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPatientAddressKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Contact Number");

        jTxtPatientContactNum.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtPatientContactNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPatientContactNumKeyReleased(evt);
            }
        });

        jBtnPatientSave.setBackground(new java.awt.Color(0, 102, 255));
        jBtnPatientSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnPatientSave.setForeground(new java.awt.Color(255, 255, 255));
        jBtnPatientSave.setText("Save");
        jBtnPatientSave.addActionListener(this::jBtnPatientSaveActionPerformed);

        jBtnPatientUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnPatientUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnPatientUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnPatientUpdate.setText("Update");
        jBtnPatientUpdate.addActionListener(this::jBtnPatientUpdateActionPerformed);

        jBtnPatientDelete.setBackground(new java.awt.Color(255, 0, 0));
        jBtnPatientDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnPatientDelete.setForeground(new java.awt.Color(255, 255, 255));
        jBtnPatientDelete.setText("Delete");
        jBtnPatientDelete.addActionListener(this::jBtnPatientDeleteActionPerformed);

        jTblPatient.setBackground(new java.awt.Color(0, 153, 204));
        jTblPatient.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblPatient.setForeground(new java.awt.Color(255, 255, 255));
        jTblPatient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Address", "Contact Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblPatientMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTblPatient);

        jBtnPatientCancel.setBackground(new java.awt.Color(255, 51, 0));
        jBtnPatientCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnPatientCancel.setForeground(new java.awt.Color(255, 255, 255));
        jBtnPatientCancel.setText("Cancel");
        jBtnPatientCancel.addActionListener(this::jBtnPatientCancelActionPerformed);

        javax.swing.GroupLayout jPanelPatientContextLayout = new javax.swing.GroupLayout(jPanelPatientContext);
        jPanelPatientContext.setLayout(jPanelPatientContextLayout);
        jPanelPatientContextLayout.setHorizontalGroup(
            jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jBtnPatientSave, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jBtnPatientUpdate)
                        .addGap(38, 38, 38)
                        .addComponent(jBtnPatientDelete)
                        .addGap(41, 41, 41)
                        .addComponent(jBtnPatientCancel))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel24))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jTxtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel25))
                            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jTxtPatientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jTxtPatientContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(175, Short.MAX_VALUE))
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator5)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPatientContextLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanelPatientContextLayout.setVerticalGroup(
            jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPatientContextLayout.createSequentialGroup()
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtPatientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtPatientContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnPatientSave)
                    .addComponent(jBtnPatientUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPatientDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPatientCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanelTreatmentContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Name");

        jTxtTreatmentName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtTreatmentName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtTreatmentNameKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 255));
        jLabel28.setText("Manage Treatment");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Description");

        jTxtTreatmentDescription.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtTreatmentDescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtTreatmentDescriptionKeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setText("Price");

        jBtnTreatmentSave.setBackground(new java.awt.Color(0, 102, 255));
        jBtnTreatmentSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnTreatmentSave.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTreatmentSave.setText("Save");
        jBtnTreatmentSave.addActionListener(this::jBtnTreatmentSaveActionPerformed);

        jBtnTreatmentUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnTreatmentUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnTreatmentUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTreatmentUpdate.setText("Update");
        jBtnTreatmentUpdate.addActionListener(this::jBtnTreatmentUpdateActionPerformed);

        jBtnTreatmentDelete.setBackground(new java.awt.Color(255, 0, 0));
        jBtnTreatmentDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnTreatmentDelete.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTreatmentDelete.setText("Delete");
        jBtnTreatmentDelete.addActionListener(this::jBtnTreatmentDeleteActionPerformed);

        jTblTreatment.setBackground(new java.awt.Color(0, 153, 204));
        jTblTreatment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblTreatment.setForeground(new java.awt.Color(255, 255, 255));
        jTblTreatment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Description", "Price"
            }
        ));
        jTblTreatment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblTreatmentMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTblTreatment);

        jBtnTreatmentCancel.setBackground(new java.awt.Color(255, 51, 0));
        jBtnTreatmentCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnTreatmentCancel.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTreatmentCancel.setText("Cancel");
        jBtnTreatmentCancel.addActionListener(this::jBtnTreatmentCancelActionPerformed);

        jTxtTreatmentPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jTxtTreatmentPrice.addActionListener(this::jTxtTreatmentPriceActionPerformed);
        jTxtTreatmentPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTreatmentPriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtTreatmentPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelTreatmentContextLayout = new javax.swing.GroupLayout(jPanelTreatmentContext);
        jPanelTreatmentContext.setLayout(jPanelTreatmentContextLayout);
        jPanelTreatmentContextLayout.setHorizontalGroup(
            jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator6)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTreatmentContextLayout.createSequentialGroup()
                .addGap(0, 75, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jBtnTreatmentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jBtnTreatmentUpdate)
                                .addGap(38, 38, 38)
                                .addComponent(jBtnTreatmentDelete)
                                .addGap(41, 41, 41)
                                .addComponent(jBtnTreatmentCancel))
                            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jTxtTreatmentName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(jLabel29))
                                    .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jTxtTreatmentDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(62, 62, 62)
                                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jTxtTreatmentPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jLabel28)))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jPanelTreatmentContextLayout.setVerticalGroup(
            jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTreatmentContextLayout.createSequentialGroup()
                        .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtTreatmentDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                                .addComponent(jTxtTreatmentPrice)
                                .addGap(2, 2, 2))))
                    .addGroup(jPanelTreatmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtTreatmentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanelTreatmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnTreatmentSave)
                    .addComponent(jBtnTreatmentUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnTreatmentDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnTreatmentCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanelBillsContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setText("Name");

        jTxtDentistName6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 102, 255));
        jLabel32.setText("Manage Bills");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Description");

        jTxtDentistSpec6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("Price");

        jTxtDentistContactNum6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jBtnDentistSave6.setBackground(new java.awt.Color(0, 102, 255));
        jBtnDentistSave6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistSave6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistSave6.setText("Save");
        jBtnDentistSave6.addActionListener(this::jBtnDentistSave6ActionPerformed);

        jBtnDentistUpdate6.setBackground(new java.awt.Color(255, 102, 51));
        jBtnDentistUpdate6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistUpdate6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistUpdate6.setText("Update");
        jBtnDentistUpdate6.addActionListener(this::jBtnDentistUpdate6ActionPerformed);

        jBtnDentistDelete6.setBackground(new java.awt.Color(255, 0, 0));
        jBtnDentistDelete6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistDelete6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistDelete6.setText("Delete");
        jBtnDentistDelete6.addActionListener(this::jBtnDentistDelete6ActionPerformed);

        jTblTreatment1.setBackground(new java.awt.Color(0, 153, 204));
        jTblTreatment1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblTreatment1.setForeground(new java.awt.Color(0, 102, 255));
        jTblTreatment1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Description", "Price"
            }
        ));
        jScrollPane7.setViewportView(jTblTreatment1);

        jBtnDentistCancel6.setBackground(new java.awt.Color(255, 51, 0));
        jBtnDentistCancel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistCancel6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistCancel6.setText("Cancel");
        jBtnDentistCancel6.addActionListener(this::jBtnDentistCancel6ActionPerformed);

        javax.swing.GroupLayout jPanelBillsContextLayout = new javax.swing.GroupLayout(jPanelBillsContext);
        jPanelBillsContext.setLayout(jPanelBillsContextLayout);
        jPanelBillsContextLayout.setHorizontalGroup(
            jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jBtnDentistSave6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jBtnDentistUpdate6)
                        .addGap(38, 38, 38)
                        .addComponent(jBtnDentistDelete6)
                        .addGap(41, 41, 41)
                        .addComponent(jBtnDentistCancel6))
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jTxtDentistName6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(jLabel33))
                                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jTxtDentistSpec6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(62, 62, 62)
                                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jTxtDentistContactNum6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel32)))))
                .addContainerGap(175, Short.MAX_VALUE))
            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator7)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillsContextLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanelBillsContextLayout.setVerticalGroup(
            jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillsContextLayout.createSequentialGroup()
                        .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtDentistSpec6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtDentistContactNum6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtDentistName6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnDentistSave6)
                    .addComponent(jBtnDentistUpdate6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistDelete6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistCancel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanelSettingContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setText("User Name");

        jTxtDentistName7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 102, 255));
        jLabel36.setText("Setting");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setText("Full Name");

        jTxtDentistSpec7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jBtnDentistUpdate7.setBackground(new java.awt.Color(255, 102, 51));
        jBtnDentistUpdate7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistUpdate7.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistUpdate7.setText("Update");
        jBtnDentistUpdate7.addActionListener(this::jBtnDentistUpdate7ActionPerformed);

        jBtnDentistCancel7.setBackground(new java.awt.Color(255, 51, 0));
        jBtnDentistCancel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistCancel7.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistCancel7.setText("Cancel");
        jBtnDentistCancel7.addActionListener(this::jBtnDentistCancel7ActionPerformed);

        jBtnChangePassword.setText("Change Password");

        javax.swing.GroupLayout jPanelSettingContextLayout = new javax.swing.GroupLayout(jPanelSettingContext);
        jPanelSettingContext.setLayout(jPanelSettingContextLayout);
        jPanelSettingContextLayout.setHorizontalGroup(
            jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(390, 390, 390))
            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnChangePassword)
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jTxtDentistName7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel37))
                            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jTxtDentistSpec7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(jBtnDentistUpdate7)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDentistCancel7)))))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanelSettingContextLayout.setVerticalGroup(
            jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtDentistSpec7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtDentistName7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnDentistUpdate7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnDentistCancel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jBtnChangePassword)
                .addGap(41, 41, 41)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(410, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainContextLayout = new javax.swing.GroupLayout(jPanelMainContext);
        jPanelMainContext.setLayout(jPanelMainContextLayout);
        jPanelMainContextLayout.setHorizontalGroup(
            jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDentistContext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jPanelAppoinmentContext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(12, 12, 12)))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addComponent(jPanelPatientContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelTreatmentContext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanelBillsContext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanelSettingContext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        jPanelMainContextLayout.setVerticalGroup(
            jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDentistContext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jPanelAppoinmentContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1400, Short.MAX_VALUE)))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addComponent(jPanelPatientContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 1367, Short.MAX_VALUE)))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainContextLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTreatmentContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBillsContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSettingContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanel.setBackground(new java.awt.Color(0, 102, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sunrise Dental Clinic");

        jLblDate.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLblDate.setForeground(new java.awt.Color(204, 204, 204));
        jLblDate.setText("2025/11/15");

        jLblTime.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLblTime.setForeground(new java.awt.Color(204, 204, 204));
        jLblTime.setText("10.53 am");

        jBtnLogOut.setBackground(new java.awt.Color(255, 0, 0));
        jBtnLogOut.setForeground(new java.awt.Color(0, 102, 204));
        jBtnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/src/icons8-logout-48 (3).png"))); // NOI18N
        jBtnLogOut.addActionListener(this::jBtnLogOutActionPerformed);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblDate)
                    .addComponent(jLblTime))
                .addGap(253, 253, 253)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jLblDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblTime))
                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jBtnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMenuContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMainContext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelMainContext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelMenuContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLogOutActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jBtnLogOutActionPerformed

    private void jBtnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSettingActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(true);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnSettingActionPerformed

    private void jBtnDentistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(true);

        viewAllDentist();
    }//GEN-LAST:event_jBtnDentistActionPerformed

    private void jBtnPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(true);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnPatientActionPerformed

    private void jBtnDentistSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistSaveActionPerformed
        // TODO add your handling code here:

        String name = jTxtDentistName.getText().trim();
        String Specialization = jTxtDentistSpec.getText().trim();
        String contact_number = jTxtDentistContactNum.getText().trim();
        DentistStetus status = DentistStetus.valueOf(jCmbDentistStetus.getSelectedItem().toString());

        dentiestController.save(new DentiestDTO(name, Specialization, contact_number, status));
        clearInputs();
        viewAllDentist();
    }//GEN-LAST:event_jBtnDentistSaveActionPerformed

    private void jBtnDentistUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistUpdateActionPerformed
        // TODO add your handling code here:
        int row = jTblDentist.getSelectedRow();
        int id
                = Integer.parseInt(
                        jTblDentist.getValueAt(row, 0).toString()
                );

        String name = jTxtDentistName.getText().trim();
        String Specialization = jTxtDentistSpec.getText().trim();
        String contact_number = jTxtDentistContactNum.getText().trim();
        DentistStetus status = DentistStetus.valueOf(jCmbDentistStetus.getSelectedItem().toString());

        dentiestController.update(new DentiestDTO(id, name, Specialization, contact_number, status));
        clearInputs();

        viewAllDentist();
    }//GEN-LAST:event_jBtnDentistUpdateActionPerformed

    private void jBtnDentistDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistDeleteActionPerformed
        // TODO add your handling code here:
        int row = jTblDentist.getSelectedRow();
        int id = Integer.parseInt(jTblDentist.getValueAt(row, 0).toString());

        dentiestController.delete(id);

        clearInputs();
        viewAllDentist();
    }//GEN-LAST:event_jBtnDentistDeleteActionPerformed

    private void jBtnDentistCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistCancelActionPerformed
        // TODO add your handling code here:
        clearInputs();
    }//GEN-LAST:event_jBtnDentistCancelActionPerformed

    private void jBtnDentistSave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistSave3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistSave3ActionPerformed

    private void jBtnDentistUpdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistUpdate3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistUpdate3ActionPerformed

    private void jBtnDentistDelete3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistDelete3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistDelete3ActionPerformed

    private void jBtnDentistCancel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistCancel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistCancel3ActionPerformed

    private void jBtnPatientSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientSaveActionPerformed
        // TODO add your handling code here:
        String name = jTxtPatientName.getText().trim();
        String address = jTxtPatientAddress.getText().trim();
        String contact_number = jTxtPatientContactNum.getText().trim();

        patientController.save(new PatientDTO(name, address, contact_number));

        clearInputs();
        viewAllPatient();
    }//GEN-LAST:event_jBtnPatientSaveActionPerformed

    private void jBtnPatientUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientUpdateActionPerformed
        // TODO add your handling code here:
        int row = jTblPatient.getSelectedRow();
        int id = Integer.parseInt(jTblPatient.getValueAt(row, 0).toString());

        String name = jTxtPatientName.getText().trim();
        String address = jTxtPatientAddress.getText().trim();
        String contact_number = jTxtPatientContactNum.getText().trim();

        patientController.update(new PatientDTO(id, name, address, contact_number));
        clearInputs();

        viewAllPatient();
    }//GEN-LAST:event_jBtnPatientUpdateActionPerformed

    private void jBtnPatientDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientDeleteActionPerformed
        // TODO add your handling code here:
        int row = jTblPatient.getSelectedRow();
        int id = Integer.parseInt(jTblPatient.getValueAt(row, 0).toString());

        patientController.delete(id);
        clearInputs();
        viewAllPatient();
    }//GEN-LAST:event_jBtnPatientDeleteActionPerformed

    private void jBtnPatientCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientCancelActionPerformed
        // TODO add your handling code here:
        clearInputs();
        checkInputs();
    }//GEN-LAST:event_jBtnPatientCancelActionPerformed

    private void jBtnTreatmentSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTreatmentSaveActionPerformed
        String name = jTxtTreatmentName.getText().trim();
        String description = jTxtTreatmentDescription.getText().trim();
        double price = Double.parseDouble(jTxtTreatmentPrice.getText());

        treatmentController.save(new TreatmentDTO(price, name, description));

        clearInputs();
        viewAllTreatment();
    }//GEN-LAST:event_jBtnTreatmentSaveActionPerformed

    private void jBtnTreatmentUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTreatmentUpdateActionPerformed
        // TODO add your handling code here:
        int row = jTblTreatment.getSelectedRow();
        int id = Integer.parseInt(jTblTreatment.getValueAt(row, 0).toString());

        String name = jTxtTreatmentName.getText().trim();
        String description = jTxtTreatmentDescription.getText().trim();
        double price = Double.parseDouble(jTxtTreatmentPrice.getText());

        treatmentController.update(new TreatmentDTO(id, price, name, description));
        clearInputs();

        viewAllTreatment();
    }//GEN-LAST:event_jBtnTreatmentUpdateActionPerformed

    private void jBtnTreatmentDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTreatmentDeleteActionPerformed
        // TODO add your handling code here:
        int row = jTblTreatment.getSelectedRow();

        int id = Integer.parseInt(jTblTreatment.getValueAt(row, 0).toString());
        treatmentController.delete(id);
        clearInputs();

        viewAllTreatment();
    }//GEN-LAST:event_jBtnTreatmentDeleteActionPerformed

    private void jBtnTreatmentCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTreatmentCancelActionPerformed
        // TODO add your handling code here:
        clearInputs();
        checkInputs();
    }//GEN-LAST:event_jBtnTreatmentCancelActionPerformed

    private void jBtnDentistSave6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistSave6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistSave6ActionPerformed

    private void jBtnDentistUpdate6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistUpdate6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistUpdate6ActionPerformed

    private void jBtnDentistDelete6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistDelete6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistDelete6ActionPerformed

    private void jBtnDentistCancel6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistCancel6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistCancel6ActionPerformed

    private void jBtnDentistUpdate7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistUpdate7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistUpdate7ActionPerformed

    private void jBtnDentistCancel7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistCancel7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistCancel7ActionPerformed

    private void jTxtDentistName3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDentistName3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDentistName3ActionPerformed

    private void jCmbDentistStetusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDentistStetusKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jCmbDentistStetusKeyReleased

    private void jTxtDentistContactNumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistContactNumKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtDentistContactNumKeyReleased

    private void jTxtDentistSpecKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistSpecKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtDentistSpecKeyReleased

    private void jTxtDentistNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistNameKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtDentistNameKeyReleased

    private void jCmbDentistStetusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDentistStetusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbDentistStetusActionPerformed

    private void jTblDentistMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblDentistMousePressed
        // TODO add your handling code here:
        int row = jTblDentist.getSelectedRow();

        String name = jTblDentist.getValueAt(row, 1).toString();
        String spec = jTblDentist.getValueAt(row, 2).toString();
        String contact_number = jTblDentist.getValueAt(row, 3).toString();
        Object stetusOb = jTblDentist.getValueAt(row, 4);

        jTxtDentistName.setText(name);
        jTxtDentistSpec.setText(spec);
        jTxtDentistContactNum.setText(contact_number);

        if (stetusOb != null) {
            jCmbDentistStetus.setSelectedItem(stetusOb.toString());
        }
        jBtnDentistSave.setEnabled(false);
    }//GEN-LAST:event_jTblDentistMousePressed

    private void jTxtDentistContactNumPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTxtDentistContactNumPropertyChange
        // TODO add your handling code here:
        checkInputs();

    }//GEN-LAST:event_jTxtDentistContactNumPropertyChange

    private void jBtnAppoinmenetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAppoinmenetActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(true);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnAppoinmenetActionPerformed

    private void jBtnTretmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTretmentActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(true);
        jPanelDentistContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnTretmentActionPerformed

    private void jBtnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBillActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(true);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnBillActionPerformed

    private void jTxtPatientNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPatientNameKeyReleased
        // TODO add your handling code here:
        checkInputs();

    }//GEN-LAST:event_jTxtPatientNameKeyReleased

    private void jTxtPatientAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPatientAddressKeyReleased
        // TODO add your handling code here:
        checkInputs();

    }//GEN-LAST:event_jTxtPatientAddressKeyReleased

    private void jTxtPatientContactNumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPatientContactNumKeyReleased
        // TODO add your handling code here:
        checkInputs();

    }//GEN-LAST:event_jTxtPatientContactNumKeyReleased

    private void jTblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblPatientMouseClicked
        // TODO add your handling code here:
        int row = jTblPatient.getSelectedRow();

        String name = jTblPatient.getValueAt(row, 1).toString();
        String address = jTblPatient.getValueAt(row, 2).toString();
        String contact_number = jTblPatient.getValueAt(row, 3).toString();

        jTxtPatientName.setText(name);
        jTxtPatientAddress.setText(address);
        jTxtPatientContactNum.setText(contact_number);
        checkInputs();
        jBtnPatientSave.setEnabled(false);

    }//GEN-LAST:event_jTblPatientMouseClicked

    private void jTblTreatmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblTreatmentMouseClicked
        // TODO add your handling code here:
        int row = jTblTreatment.getSelectedRow();

        String name = jTblTreatment.getValueAt(row, 1).toString();
        String desc = jTblTreatment.getValueAt(row, 2).toString();
        String price = jTblTreatment.getValueAt(row, 3).toString();

        jTxtTreatmentName.setText(name);
        jTxtTreatmentDescription.setText(desc);
        jTxtTreatmentPrice.setText(price);

        checkInputs();
        jBtnTreatmentSave.setEnabled(false);
    }//GEN-LAST:event_jTblTreatmentMouseClicked

    private void jTxtTreatmentPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTreatmentPriceKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTxtTreatmentPriceKeyPressed

    private void jTxtTreatmentPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtTreatmentPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtTreatmentPriceActionPerformed

    private void jTxtTreatmentNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTreatmentNameKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtTreatmentNameKeyReleased

    private void jTxtTreatmentDescriptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTreatmentDescriptionKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtTreatmentDescriptionKeyReleased

    private void jTxtTreatmentPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTreatmentPriceKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtTreatmentPriceKeyReleased

    private void jTxtDentistName4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDentistName4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDentistName4ActionPerformed

    private void jTxtDentistName5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDentistName5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDentistName5ActionPerformed

    private void jTblAppoinmentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblAppoinmentMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTblAppoinmentMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DashbordForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAppoinmenet;
    private javax.swing.JButton jBtnBill;
    private javax.swing.JLabel jBtnChangePassword;
    private javax.swing.JButton jBtnDentist;
    private javax.swing.JButton jBtnDentistCancel;
    private javax.swing.JButton jBtnDentistCancel3;
    private javax.swing.JButton jBtnDentistCancel6;
    private javax.swing.JButton jBtnDentistCancel7;
    private javax.swing.JButton jBtnDentistDelete;
    private javax.swing.JButton jBtnDentistDelete3;
    private javax.swing.JButton jBtnDentistDelete6;
    private javax.swing.JButton jBtnDentistSave;
    private javax.swing.JButton jBtnDentistSave3;
    private javax.swing.JButton jBtnDentistSave6;
    private javax.swing.JButton jBtnDentistUpdate;
    private javax.swing.JButton jBtnDentistUpdate3;
    private javax.swing.JButton jBtnDentistUpdate6;
    private javax.swing.JButton jBtnDentistUpdate7;
    private javax.swing.JButton jBtnLogOut;
    private javax.swing.JButton jBtnPatient;
    private javax.swing.JButton jBtnPatientCancel;
    private javax.swing.JButton jBtnPatientDelete;
    private javax.swing.JButton jBtnPatientSave;
    private javax.swing.JButton jBtnPatientUpdate;
    private javax.swing.JButton jBtnSetting;
    private javax.swing.JButton jBtnTreatmentCancel;
    private javax.swing.JButton jBtnTreatmentDelete;
    private javax.swing.JButton jBtnTreatmentSave;
    private javax.swing.JButton jBtnTreatmentUpdate;
    private javax.swing.JButton jBtnTretment;
    private javax.swing.JComboBox<String> jCmbAppoinmentStetus;
    private javax.swing.JComboBox<String> jCmbDentist;
    private javax.swing.JComboBox<String> jCmbDentist1;
    private javax.swing.JComboBox<String> jCmbDentistStetus;
    private javax.swing.JComboBox<String> jCmbPatient;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLblDate;
    private javax.swing.JLabel jLblTime;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanelAppoinmentContext;
    private javax.swing.JPanel jPanelBillsContext;
    private javax.swing.JPanel jPanelDentistContext;
    private javax.swing.JPanel jPanelMainContext;
    private javax.swing.JPanel jPanelMenuContext;
    private javax.swing.JPanel jPanelPatientContext;
    private javax.swing.JPanel jPanelSettingContext;
    private javax.swing.JPanel jPanelTreatmentContext;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTable jTblAppoinment;
    private javax.swing.JTable jTblDentist;
    private javax.swing.JTable jTblPatient;
    private javax.swing.JTable jTblTreatment;
    private javax.swing.JTable jTblTreatment1;
    private javax.swing.JFormattedTextField jTxtDentistContactNum;
    private javax.swing.JTextField jTxtDentistContactNum6;
    private javax.swing.JTextField jTxtDentistName;
    private javax.swing.JTextField jTxtDentistName3;
    private javax.swing.JTextField jTxtDentistName4;
    private javax.swing.JTextField jTxtDentistName5;
    private javax.swing.JTextField jTxtDentistName6;
    private javax.swing.JTextField jTxtDentistName7;
    private javax.swing.JTextField jTxtDentistSpec;
    private javax.swing.JTextField jTxtDentistSpec6;
    private javax.swing.JTextField jTxtDentistSpec7;
    private javax.swing.JTextField jTxtPatientAddress;
    private javax.swing.JTextField jTxtPatientContactNum;
    private javax.swing.JTextField jTxtPatientName;
    private javax.swing.JTextField jTxtTreatmentDescription;
    private javax.swing.JTextField jTxtTreatmentName;
    private javax.swing.JFormattedTextField jTxtTreatmentPrice;
    // End of variables declaration//GEN-END:variables
}

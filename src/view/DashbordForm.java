/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.AppointmentController;
import Controller.DentiestController;
import Controller.PatientController;
import Controller.TreatmentController;
import Controller.UserController;
import Enums.AppointmentStetus;
import Enums.DentistStetus;
import Enums.Role;
import Enums.UserStetus;
import dto.AddUserDTO;
import dto.AppoinmentDTO;
import dto.DentiestDTO;
import dto.PatientDTO;
import dto.TreatmentDTO;
import dto.UpdateUserDTO;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import model.Dentist;
import model.Patient;
import model.Treatment;
import static util.UserSession.clearSession;

/**
 *
 * @author ASUS
 */
public final class DashbordForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashbordForm.class.getName());
    private final DentiestController dentiestController;
    private final PatientController patientController;
    private final TreatmentController treatmentController;
    private final AppointmentController appointmentController;
    private final UserController userController;

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
        jTxtDentistEmail.setText("");
        jTxtDentistSpec.setText("");
        jTxtDentistContactNum.setText("");

        jTxtTreatmentName.setText("");
        jTxtTreatmentDescription.setText("");
        jTxtTreatmentPrice.setText("");

        jTxtUsersName.setText("");
        jTxtUsersEmail.setText("");
        
        if (jCmbDentistStetus.getItemCount() > 0) {
            jCmbDentistStetus.setSelectedIndex(0);
        }

        jTblDentist.clearSelection();

        jTblPatient.clearSelection();

        jTblTreatment.clearSelection();
        
        jTblUsers.clearSelection();

    }

    private void checkInputs() {
        String name = jTxtDentistName.getText().trim();
        String spec = jTxtDentistEmail.getText().trim();
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
        appointmentController = new AppointmentController(this);
        userController = new UserController(this);

        showDate();
        showTime();
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelUserContext.setVisible(false);
        viewAllDentist();
        viewAllPatient();
        viewAllTreatment();
        viewAllUsers();

    }

    final void viewAllDentist() {
        List<DentiestDTO> allDentists = dentiestController.getAll();

        DefaultTableModel model = (DefaultTableModel) jTblDentist.getModel();
        model.setRowCount(0);

        for (DentiestDTO dentiest : allDentists) {
            jCmbDentistId.addItem(
                    dentiest.getId()
            );
            model.addRow(new Object[]{
                dentiest.getId(),
                dentiest.getFullName(),
                dentiest.getSpecialization(),
                dentiest.getContactNumber(),
                dentiest.getEmail(),
                dentiest.getStetus()
            });
        }

    }

    final void viewAllUsers() {
        List<AddUserDTO> allusers = userController.getAll();

        DefaultTableModel model = (DefaultTableModel) jTblUsers.getModel();
        model.setRowCount(0);

        for (AddUserDTO user : allusers) {

            model.addRow(new Object[]{
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getRole()
            });
        }

    }

    final void viewAllPatient() {

        List<PatientDTO> all = patientController.getAll();
        DefaultTableModel model = (DefaultTableModel) jTblPatient.getModel();
        model.setRowCount(0);

        for (PatientDTO patient : all) {
            jCmbPatientId.addItem(
                    patient.getId()
            );
            model.addRow(new Object[]{
                patient.getId(),
                patient.getFullName(),
                patient.getAddress(),
                patient.getContactNumber(),
                patient.getEmail()
            });
        }
    }

    final void viewAllTreatment() {

        List<TreatmentDTO> all = treatmentController.getAll();

        DefaultTableModel model = (DefaultTableModel) jTblTreatment.getModel();
        model.setRowCount(0);

        for (TreatmentDTO treatment : all) {
            jCmbTreatmentId.addItem(
                    treatment.getId()
            );
            model.addRow(new Object[]{
                treatment.getId(),
                treatment.getTretmentName(),
                treatment.getDescription(),
                treatment.getTreatmentCost()
            });
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

        jTxtSettingReEnterNewPw = new javax.swing.JTextField();
        jPanelMenuContext = new javax.swing.JPanel();
        jBtnPatient = new javax.swing.JButton();
        jBtnTretment = new javax.swing.JButton();
        jBtnBill = new javax.swing.JButton();
        jBtnUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBtnAppoinmenet = new javax.swing.JButton();
        jBtnDentist = new javax.swing.JButton();
        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLblDate = new javax.swing.JLabel();
        jLblTime = new javax.swing.JLabel();
        jBtnLogOut = new javax.swing.JButton();
        jBtnSetting = new javax.swing.JLabel();
        jPanelMainContext = new javax.swing.JPanel();
        jPanelAppoinmentContext = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jBtnAppoinmentSave = new javax.swing.JButton();
        jBtnAppoinmentUpdate = new javax.swing.JButton();
        jBtnAppoinmentCancel = new javax.swing.JButton();
        jCmbPatientId = new javax.swing.JComboBox<>();
        jCmbDentistId = new javax.swing.JComboBox<>();
        jCmbTreatmentId = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblAppoinment = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jCmbAppoinmentStetus = new javax.swing.JComboBox<>();
        jTxtAppoinmentNumber = new javax.swing.JTextField();
        jTxtAppoinmentPatient = new javax.swing.JTextField();
        jTxtAppoinmentDentist = new javax.swing.JTextField();
        jTxtAppoinmentTime = new javax.swing.JTextField();
        jTxtAppoinmentTreatment = new javax.swing.JTextField();
        jTxtAppoinmentDate = new javax.swing.JTextField();
        jTxtAppoinmentSearch = new javax.swing.JTextField();
        jPanelDentistContext = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTxtDentistName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTxtDentistEmail = new javax.swing.JTextField();
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
        jLabel47 = new javax.swing.JLabel();
        jTxtDentistSpec = new javax.swing.JTextField();
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
        jTxtPatientEmail = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
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
        jTxtBillAppoinmentNumber = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTxtBillConsultationFee = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTxtBillDiscount = new javax.swing.JTextField();
        jBtnDentistSave6 = new javax.swing.JButton();
        jBtnDentistCancel6 = new javax.swing.JButton();
        jTxtBillTreatmentFee = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTxtBillTotalFee = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jPlBillPreview = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLivePreview = new javax.swing.JEditorPane();
        jPanelSettingContext = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTxtSettingUserName = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTxtSettingName = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jBtnSettingUpdate = new javax.swing.JButton();
        jBtnSettingClear = new javax.swing.JButton();
        jBtnSettingChangePassword = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTxtSettingOldPw = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTxtSettingNewPw = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanelUserContext = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jTxtUsersName = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jTxtUsersEmail = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jBtnUsersAdd = new javax.swing.JButton();
        jBtnUsersUpdate = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTblUsers = new javax.swing.JTable();
        jBtnUsersClear = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jCmbUserStatus = new javax.swing.JComboBox<>();
        jCmbUsersRole = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        jTxtUsersSearch = new javax.swing.JTextField();

        jTxtSettingReEnterNewPw.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

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

        jBtnUser.setBackground(new java.awt.Color(0, 102, 204));
        jBtnUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnUser.setForeground(new java.awt.Color(204, 255, 255));
        jBtnUser.setText("Users");
        jBtnUser.addActionListener(this::jBtnUserActionPerformed);

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
                    .addComponent(jBtnUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jBtnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap())
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
        jBtnLogOut.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jBtnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        jBtnLogOut.setText("Log Out");
        jBtnLogOut.addActionListener(this::jBtnLogOutActionPerformed);

        jBtnSetting.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSetting.setForeground(new java.awt.Color(255, 255, 255));
        jBtnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/src/icons8-settings-25.png"))); // NOI18N
        jBtnSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnSettingMouseClicked(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jBtnSetting)
                .addGap(18, 18, 18)
                .addComponent(jBtnLogOut)
                .addGap(59, 59, 59))
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
                    .addComponent(jLabel1))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnSetting)
                    .addComponent(jBtnLogOut))
                .addGap(30, 30, 30))
        );

        jPanelMainContext.setBackground(new java.awt.Color(255, 255, 255));

        jPanelAppoinmentContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Appointment Number");

        jLabel19.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 255));
        jLabel19.setText("Manage Appoinment");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Select Patient ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Select Dentist");

        jBtnAppoinmentSave.setBackground(new java.awt.Color(0, 102, 255));
        jBtnAppoinmentSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnAppoinmentSave.setForeground(new java.awt.Color(255, 255, 255));
        jBtnAppoinmentSave.setText("Save");
        jBtnAppoinmentSave.addActionListener(this::jBtnAppoinmentSaveActionPerformed);

        jBtnAppoinmentUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnAppoinmentUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnAppoinmentUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnAppoinmentUpdate.setText("Update");
        jBtnAppoinmentUpdate.addActionListener(this::jBtnAppoinmentUpdateActionPerformed);

        jBtnAppoinmentCancel.setBackground(new java.awt.Color(255, 51, 0));
        jBtnAppoinmentCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnAppoinmentCancel.setForeground(new java.awt.Color(255, 255, 255));
        jBtnAppoinmentCancel.setText("Clear");
        jBtnAppoinmentCancel.setToolTipText("");
        jBtnAppoinmentCancel.addActionListener(this::jBtnAppoinmentCancelActionPerformed);

        jCmbPatientId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbPatientId.addActionListener(this::jCmbPatientIdActionPerformed);

        jCmbDentistId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbDentistId.addActionListener(this::jCmbDentistIdActionPerformed);

        jCmbTreatmentId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCmbTreatmentId.addActionListener(this::jCmbTreatmentIdActionPerformed);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("Select Treatment");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel38.setText("Appoinment date");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setText("Appoinment Time");

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
        jCmbAppoinmentStetus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SHEDULED", "COMPLETE", "CANCELED" }));
        jCmbAppoinmentStetus.setToolTipText("Select");
        jCmbAppoinmentStetus.addActionListener(this::jCmbAppoinmentStetusActionPerformed);

        jTxtAppoinmentNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentPatient.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentDentist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentTreatment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTxtAppoinmentSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanelAppoinmentContextLayout = new javax.swing.GroupLayout(jPanelAppoinmentContext);
        jPanelAppoinmentContext.setLayout(jPanelAppoinmentContextLayout);
        jPanelAppoinmentContextLayout.setHorizontalGroup(
            jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel19)
                .addContainerGap(256, Short.MAX_VALUE))
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelAppoinmentContextLayout.createSequentialGroup()
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTxtAppoinmentNumber))
                                .addGap(36, 36, 36)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtAppoinmentPatient)
                                    .addComponent(jLabel20)
                                    .addComponent(jCmbPatientId, 0, 176, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel21)
                                    .addComponent(jCmbDentistId, 0, 176, Short.MAX_VALUE)
                                    .addComponent(jTxtAppoinmentDentist))
                                .addGap(35, 35, 35)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel22)
                                    .addComponent(jCmbTreatmentId, 0, 176, Short.MAX_VALUE)
                                    .addComponent(jTxtAppoinmentTreatment)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelAppoinmentContextLayout.createSequentialGroup()
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTxtAppoinmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(jTxtAppoinmentTime, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCmbAppoinmentStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAppoinmentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnAppoinmentUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnAppoinmentCancel)))
                        .addGap(68, 68, 68))
                    .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTxtAppoinmentSearch, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE))))
        );
        jPanelAppoinmentContextLayout.setVerticalGroup(
            jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(23, 23, 23)
                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                            .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addGap(32, 32, 32))
                                .addComponent(jCmbPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jTxtAppoinmentPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5))
                        .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                            .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCmbDentistId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                                    .addComponent(jLabel22)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCmbTreatmentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTxtAppoinmentDentist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTxtAppoinmentTreatment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtAppoinmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAppoinmentContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnAppoinmentSave)
                        .addComponent(jBtnAppoinmentUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnAppoinmentCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbAppoinmentStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTxtAppoinmentDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAppoinmentContextLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtAppoinmentTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addComponent(jTxtAppoinmentSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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

        jTxtDentistEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistEmail.addActionListener(this::jTxtDentistEmailActionPerformed);
        jTxtDentistEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtDentistEmailKeyReleased(evt);
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Specialization", "Contact Number", "Email", "Stetus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
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

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel47.setText("Email");

        jTxtDentistSpec.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtDentistSpec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtDentistSpecKeyReleased(evt);
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
                                .addGap(38, 38, 38)
                                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jTxtDentistName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jTxtDentistSpec, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jTxtDentistContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                                .addComponent(jLabel47)
                                                .addGap(143, 143, 143))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDentistContextLayout.createSequentialGroup()
                                                .addComponent(jTxtDentistEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jCmbDentistStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel14))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDentistContextLayout.setVerticalGroup(
            jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(14, 14, 14)
                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtDentistName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel47))
                        .addGap(66, 66, 66))
                    .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDentistContextLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDentistContextLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtDentistContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtDentistEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCmbDentistStetus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtDentistSpec))
                        .addGap(65, 65, 65)))
                .addGroup(jPanelDentistContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnDentistSave)
                    .addComponent(jBtnDentistUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDentistCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1283, Short.MAX_VALUE))
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
        jTxtPatientContactNum.addActionListener(this::jTxtPatientContactNumActionPerformed);
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Address", "Contact Number", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
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

        jTxtPatientEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtPatientEmail.addActionListener(this::jTxtPatientEmailActionPerformed);
        jTxtPatientEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPatientEmailKeyReleased(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setText("Email");

        javax.swing.GroupLayout jPanelPatientContextLayout = new javax.swing.GroupLayout(jPanelPatientContext);
        jPanelPatientContext.setLayout(jPanelPatientContextLayout);
        jPanelPatientContextLayout.setHorizontalGroup(
            jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator5)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPatientContextLayout.createSequentialGroup()
                .addGap(0, 75, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jBtnPatientSave, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jBtnPatientUpdate)
                                .addGap(38, 38, 38)
                                .addComponent(jBtnPatientDelete)
                                .addGap(41, 41, 41)
                                .addComponent(jBtnPatientCancel))))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jTxtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel25))
                            .addComponent(jTxtPatientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jTxtPatientContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jTxtPatientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPatientContextLayout.setVerticalGroup(
            jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPatientContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPatientContextLayout.createSequentialGroup()
                            .addComponent(jLabel26)
                            .addGap(18, 18, 18)
                            .addComponent(jTxtPatientContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(18, 18, 18)
                            .addComponent(jTxtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtPatientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPatientContextLayout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtPatientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        jLabel31.setText("Appoinment Number");

        jTxtBillAppoinmentNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 102, 255));
        jLabel32.setText("Manage Bills");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Consultation Fee");

        jTxtBillConsultationFee.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtBillConsultationFee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtBillConsultationFeeKeyReleased(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("Discount");

        jTxtBillDiscount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtBillDiscount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTxtBillDiscountMouseReleased(evt);
            }
        });

        jBtnDentistSave6.setBackground(new java.awt.Color(0, 102, 255));
        jBtnDentistSave6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistSave6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistSave6.setText("Save");
        jBtnDentistSave6.addActionListener(this::jBtnDentistSave6ActionPerformed);

        jBtnDentistCancel6.setBackground(new java.awt.Color(255, 51, 0));
        jBtnDentistCancel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnDentistCancel6.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDentistCancel6.setText("Cancel");
        jBtnDentistCancel6.addActionListener(this::jBtnDentistCancel6ActionPerformed);

        jTxtBillTreatmentFee.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtBillTreatmentFee.setEnabled(false);

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setText("Treatment Fee");

        jTxtBillTotalFee.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtBillTotalFee.setEnabled(false);

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setText("Total Fee");

        jPlBillPreview.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPlBillPreview.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(txtLivePreview);

        jPlBillPreview.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanelBillsContextLayout = new javax.swing.GroupLayout(jPanelBillsContext);
        jPanelBillsContext.setLayout(jPanelBillsContextLayout);
        jPanelBillsContextLayout.setHorizontalGroup(
            jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34)
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel33))
                    .addComponent(jLabel31)
                    .addComponent(jLabel52)
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addComponent(jBtnDentistSave6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnDentistCancel6))
                    .addComponent(jTxtBillTotalFee)
                    .addComponent(jTxtBillDiscount)
                    .addComponent(jTxtBillConsultationFee)
                    .addComponent(jTxtBillAppoinmentNumber)
                    .addComponent(jLabel51)
                    .addComponent(jTxtBillTreatmentFee))
                .addGap(124, 124, 124)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jPlBillPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanelBillsContextLayout.setVerticalGroup(
            jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelBillsContextLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBillAppoinmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBillConsultationFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel51)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBillTreatmentFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBillDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel52)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBillTotalFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBillsContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnDentistSave6)
                            .addComponent(jBtnDentistCancel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPlBillPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanelSettingContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setText("User Name");

        jTxtSettingUserName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 102, 255));
        jLabel36.setText("Account Setting");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setText("Full Name");

        jTxtSettingName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jBtnSettingUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnSettingUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnSettingUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnSettingUpdate.setText("Update");
        jBtnSettingUpdate.addActionListener(this::jBtnSettingUpdateActionPerformed);

        jBtnSettingClear.setBackground(new java.awt.Color(255, 51, 0));
        jBtnSettingClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnSettingClear.setForeground(new java.awt.Color(255, 255, 255));
        jBtnSettingClear.setText("Clear");
        jBtnSettingClear.addActionListener(this::jBtnSettingClearActionPerformed);

        jBtnSettingChangePassword.setBackground(new java.awt.Color(255, 102, 51));
        jBtnSettingChangePassword.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnSettingChangePassword.setForeground(new java.awt.Color(255, 255, 255));
        jBtnSettingChangePassword.setText("Change");
        jBtnSettingChangePassword.addActionListener(this::jBtnSettingChangePasswordActionPerformed);

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Current Username");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel41.setText("Old Password");

        jTxtSettingOldPw.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setText("New Password");

        jTxtSettingNewPw.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel43.setText("Re Enter New Password");

        javax.swing.GroupLayout jPanelSettingContextLayout = new javax.swing.GroupLayout(jPanelSettingContext);
        jPanelSettingContext.setLayout(jPanelSettingContextLayout);
        jPanelSettingContextLayout.setHorizontalGroup(
            jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator8)
                .addContainerGap())
            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSettingUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnSettingClear)
                        .addGap(177, 177, 177))
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jTxtSettingUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtSettingName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(339, 339, 339))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTxtSettingOldPw)
                            .addComponent(jTxtSettingNewPw, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addComponent(jBtnSettingChangePassword)))
                .addGap(394, 457, Short.MAX_VALUE))
        );
        jPanelSettingContextLayout.setVerticalGroup(
            jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel41)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtSettingOldPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingContextLayout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtSettingName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtSettingUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanelSettingContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jBtnSettingUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnSettingClear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelSettingContextLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(63, 63, 63)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addComponent(jBtnSettingChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addComponent(jTxtSettingNewPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel43)
                .addGap(96, 96, 96))
        );

        jPanelUserContext.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel44.setText("Name");

        jTxtUsersName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Microsoft Yi Baiti", 1, 48)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 102, 255));
        jLabel45.setText("Manage User");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setText("Email");

        jTxtUsersEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jBtnUsersAdd.setBackground(new java.awt.Color(0, 102, 255));
        jBtnUsersAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnUsersAdd.setForeground(new java.awt.Color(255, 255, 255));
        jBtnUsersAdd.setText("Add");
        jBtnUsersAdd.addActionListener(this::jBtnUsersAddActionPerformed);

        jBtnUsersUpdate.setBackground(new java.awt.Color(255, 102, 51));
        jBtnUsersUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnUsersUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jBtnUsersUpdate.setText("Update");
        jBtnUsersUpdate.addActionListener(this::jBtnUsersUpdateActionPerformed);

        jTblUsers.setBackground(new java.awt.Color(0, 153, 204));
        jTblUsers.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTblUsers.setForeground(new java.awt.Color(255, 255, 255));
        jTblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Email", "Stetus", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblUsersMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTblUsers);
        if (jTblUsers.getColumnModel().getColumnCount() > 0) {
            jTblUsers.getColumnModel().getColumn(0).setResizable(false);
            jTblUsers.getColumnModel().getColumn(1).setResizable(false);
            jTblUsers.getColumnModel().getColumn(2).setResizable(false);
        }

        jBtnUsersClear.setBackground(new java.awt.Color(255, 51, 0));
        jBtnUsersClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jBtnUsersClear.setForeground(new java.awt.Color(255, 255, 255));
        jBtnUsersClear.setText("Cancel");
        jBtnUsersClear.addActionListener(this::jBtnUsersClearActionPerformed);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel48.setText("Stetus");

        jCmbUserStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVATE", "DEACTIVATE", "UNREGISTERED" }));

        jCmbUsersRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STAFF", "DENTIST", "PATIENT" }));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setText("Role");

        jTxtUsersSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTxtUsersSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTxtUsersSearchMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelUserContextLayout = new javax.swing.GroupLayout(jPanelUserContext);
        jPanelUserContext.setLayout(jPanelUserContextLayout);
        jPanelUserContextLayout.setHorizontalGroup(
            jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator9)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelUserContextLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addComponent(jTxtUsersSearch))
                .addGap(83, 83, 83))
            .addGroup(jPanelUserContextLayout.createSequentialGroup()
                .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUserContextLayout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel45))
                    .addGroup(jPanelUserContextLayout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(jBtnUsersAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jBtnUsersUpdate)
                        .addGap(31, 31, 31)
                        .addComponent(jBtnUsersClear))
                    .addGroup(jPanelUserContextLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jTxtUsersName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jTxtUsersEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jCmbUserStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jCmbUsersRole, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanelUserContextLayout.setVerticalGroup(
            jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserContextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelUserContextLayout.createSequentialGroup()
                            .addComponent(jLabel46)
                            .addGap(18, 18, 18)
                            .addComponent(jTxtUsersEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelUserContextLayout.createSequentialGroup()
                            .addComponent(jLabel44)
                            .addGap(18, 18, 18)
                            .addComponent(jTxtUsersName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelUserContextLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(18, 18, 18)
                        .addComponent(jCmbUserStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                    .addGroup(jPanelUserContextLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(18, 18, 18)
                        .addComponent(jCmbUsersRole, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
                .addGap(32, 32, 32)
                .addGroup(jPanelUserContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnUsersAdd)
                    .addComponent(jBtnUsersUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnUsersClear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jTxtUsersSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
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
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelUserContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(177, Short.MAX_VALUE)))
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
                    .addGap(0, 1403, Short.MAX_VALUE)))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainContextLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTreatmentContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBillsContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSettingContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanelMainContextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainContextLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelUserContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1379, Short.MAX_VALUE)))
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

    private void jBtnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUserActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        jPanelUserContext.setVisible(true);

        viewAllDentist();
    }//GEN-LAST:event_jBtnUserActionPerformed

    private void jBtnDentistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistActionPerformed
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(false);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(true);
        jPanelUserContext.setVisible(false);
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
        jPanelUserContext.setVisible(false);
        viewAllDentist();
    }//GEN-LAST:event_jBtnPatientActionPerformed
    private void updateLivePreview() {
        try {

            String appointmentNumber = jTxtBillAppoinmentNumber.getText().trim();
            double consultation = jTxtBillConsultationFee.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jTxtBillConsultationFee.getText().trim());
            double treatment = jTxtBillTreatmentFee.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jTxtBillTreatmentFee.getText().trim());
            double discount = jTxtBillDiscount.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jTxtBillDiscount.getText().trim());

            double totalFee = (consultation + treatment) - discount;
            jTxtBillTotalFee.setText(String.valueOf(totalFee));

            txtLivePreview.setContentType("text/html");

            String liveHtml = util.ReceiptGenerator.buildHtmlReceipt(
                    "PREVIEW-MODE",
                    new dto.BillDTO(appointmentNumber, consultation, treatment, discount),
                    totalFee,
                    java.time.LocalDate.now().toString()
            );

            txtLivePreview.setText(liveHtml);

        } catch (NumberFormatException ex) {
            txtLivePreview.setText("<html><body style='padding:20px; font-family:sans-serif; color:red;'><b>Awaiting valid numerical inputs...</b></body></html>");
        }
    }
    private void jBtnDentistSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistSaveActionPerformed
        // TODO add your handling code here:

        String name = jTxtDentistName.getText().trim();
        String Specialization = jTxtDentistEmail.getText().trim();
        String contact_number = jTxtDentistContactNum.getText().trim();
        String email = jTxtDentistEmail.getText().trim();
        DentistStetus status = DentistStetus.valueOf(jCmbDentistStetus.getSelectedItem().toString());

        dentiestController.save(new DentiestDTO(name, Specialization, contact_number, email, status));
        clearInputs();
        viewAllDentist();
    }//GEN-LAST:event_jBtnDentistSaveActionPerformed

    private void jBtnDentistUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistUpdateActionPerformed
        // TODO add your handling code here:
        int row = jTblDentist.getSelectedRow();
        int id = Integer.parseInt(jTblDentist.getValueAt(row, 0).toString());

        String name = jTxtDentistName.getText().trim();
        String Specialization = jTxtDentistSpec.getText().trim();
        String contact_number = jTxtDentistContactNum.getText().trim();
        String email = jTxtDentistEmail.getText().trim();
        DentistStetus status = DentistStetus.valueOf(jCmbDentistStetus.getSelectedItem().toString());

        dentiestController.update(new DentiestDTO(id, name, Specialization, contact_number,email, status));
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

    private void jBtnAppoinmentSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAppoinmentSaveActionPerformed
        // TODO add your handling code here:
        String name = jTxtAppoinmentNumber.getText().trim();
        int patientId = (int) jCmbPatientId.getSelectedItem();
        int dentistId = (int) jCmbDentistId.getSelectedItem();
        int treatmentId = (int) jCmbTreatmentId.getSelectedItem();
        String date = jTxtAppoinmentDate.getText().trim();
        String time = jTxtAppoinmentTime.getText().trim();
        AppointmentStetus status = AppointmentStetus.valueOf(jCmbAppoinmentStetus.getSelectedItem().toString());

        appointmentController.save(new AppoinmentDTO(name, patientId, dentistId, treatmentId, date, time, status));
        clearInputs();
        viewAllDentist();

    }//GEN-LAST:event_jBtnAppoinmentSaveActionPerformed

    private void jBtnAppoinmentUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAppoinmentUpdateActionPerformed
        // TODO add your handling code here:
        int row = jTblDentist.getSelectedRow();
        int id = Integer.parseInt(jTblDentist.getValueAt(row, 0).toString());

        String name = jTxtAppoinmentNumber.getText().trim();
        int patientId = (int) jCmbPatientId.getSelectedItem();
        int dentistId = (int) jCmbDentistId.getSelectedItem();
        int treatmentId = (int) jCmbTreatmentId.getSelectedItem();
        String date = jTxtAppoinmentDate.getText().trim();
        String time = jTxtAppoinmentTime.getText().trim();
        AppointmentStetus status = AppointmentStetus.valueOf(jCmbAppoinmentStetus.getSelectedItem().toString());

        appointmentController.save(new AppoinmentDTO(id, name, patientId, dentistId, treatmentId, date, time, status));
        clearInputs();

        viewAllDentist();
    }//GEN-LAST:event_jBtnAppoinmentUpdateActionPerformed

    private void jBtnAppoinmentCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAppoinmentCancelActionPerformed
        // TODO add your handling code here:
        clearInputs();

    }//GEN-LAST:event_jBtnAppoinmentCancelActionPerformed

    private void jBtnPatientSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPatientSaveActionPerformed
        // TODO add your handling code here:
        String name = jTxtPatientName.getText().trim();
        String address = jTxtPatientAddress.getText().trim();
        String contact_number = jTxtPatientContactNum.getText().trim();
        String email = jTxtPatientEmail.getText().trim();

        patientController.save(new PatientDTO(name, address, contact_number, email));

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
        String email = jTxtPatientEmail.getText().trim();

        patientController.update(new PatientDTO(id, name, address, contact_number, email));
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

    private void jBtnDentistCancel6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDentistCancel6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnDentistCancel6ActionPerformed

    private void jBtnSettingUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSettingUpdateActionPerformed
        // TODO add your handling code here:
        String userName = jTxtSettingUserName.getText().trim();
        String name = jTxtSettingName.getText().trim();
        userController.UserUpdate(new UpdateUserDTO(userName, name));
    }//GEN-LAST:event_jBtnSettingUpdateActionPerformed

    private void jBtnSettingClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSettingClearActionPerformed
        // TODO add your handling code here:
        jTxtSettingUserName.setText("");
        jTxtSettingName.setText("");
    }//GEN-LAST:event_jBtnSettingClearActionPerformed

    private void jCmbDentistStetusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDentistStetusKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jCmbDentistStetusKeyReleased

    private void jTxtDentistContactNumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistContactNumKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtDentistContactNumKeyReleased

    private void jTxtDentistEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistEmailKeyReleased
        // TODO add your handling code here:
        checkInputs();
    }//GEN-LAST:event_jTxtDentistEmailKeyReleased

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
        String email = jTblDentist.getValueAt(row, 4).toString();
        Object stetusOb = jTblDentist.getValueAt(row, 5);

        jTxtDentistName.setText(name);
        jTxtDentistSpec.setText(spec);
        jTxtDentistEmail.setText(email);
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
        jPanelUserContext.setVisible(false);
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
        jPanelUserContext.setVisible(false);
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
        jPanelUserContext.setVisible(false);
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
        String email = jTblPatient.getValueAt(row, 4).toString();

        jTxtPatientName.setText(name);
        jTxtPatientAddress.setText(address);
        jTxtPatientContactNum.setText(contact_number);
        jTxtPatientEmail.setText(email);
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

    private void jTblAppoinmentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblAppoinmentMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTblAppoinmentMousePressed

    private void jCmbPatientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbPatientIdActionPerformed

        Patient patientById = patientController.getPatientById((int) jCmbPatientId.getSelectedItem());
        jTxtAppoinmentPatient.setText(patientById.getFullName());

        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbPatientIdActionPerformed

    private void jCmbAppoinmentStetusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbAppoinmentStetusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbAppoinmentStetusActionPerformed

    private void jBtnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLogOutActionPerformed
        // TODO add your handling code here:
        clearSession();
        this.dispose();
        clearInputs();
        Login form = new Login();
        form.setVisible(true);
    }//GEN-LAST:event_jBtnLogOutActionPerformed

    private void jCmbDentistIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDentistIdActionPerformed
        // TODO add your handling code here:
        Dentist dentistById = dentiestController.getDentistById((int) jCmbDentistId.getSelectedItem());
        jTxtAppoinmentDentist.setText(dentistById.getFullName());
    }//GEN-LAST:event_jCmbDentistIdActionPerformed

    private void jCmbTreatmentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbTreatmentIdActionPerformed
        // TODO add your handling code here:
        Treatment treatmentById = treatmentController.getTreatmentById((int) jCmbTreatmentId.getSelectedItem());
        jTxtAppoinmentTreatment.setText(treatmentById.getTretmentName());
    }//GEN-LAST:event_jCmbTreatmentIdActionPerformed

    private void jBtnSettingChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSettingChangePasswordActionPerformed
        // TODO add your handling code here:
        String oldPw = jTxtSettingOldPw.getText().trim();
        String newPw = jTxtSettingNewPw.getText().trim();
        String reEnterNewPw = jTxtSettingReEnterNewPw.getText().trim();


    }//GEN-LAST:event_jBtnSettingChangePasswordActionPerformed

    private void jBtnUsersAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUsersAddActionPerformed
        // TODO add your handling code here:
        String name = jTxtUsersName.getText().trim();
        String email = jTxtUsersEmail.getText().trim();
        UserStetus status = UserStetus.valueOf(jCmbUserStatus.getSelectedItem().toString());
        Role role = Role.valueOf(jCmbUsersRole.getSelectedItem().toString());

        userController.addUser(new AddUserDTO(name, email, status, role));
         clearInputs();
    }//GEN-LAST:event_jBtnUsersAddActionPerformed

    private void jBtnUsersUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUsersUpdateActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jBtnUsersUpdateActionPerformed

    private void jBtnUsersClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUsersClearActionPerformed
        // TODO add your handling code here:
        clearInputs();
    }//GEN-LAST:event_jBtnUsersClearActionPerformed

    private void jTxtUsersSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtUsersSearchMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtUsersSearchMouseReleased

    private void jTxtDentistSpecKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDentistSpecKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDentistSpecKeyReleased

    private void jTxtDentistEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDentistEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDentistEmailActionPerformed

    private void jTxtPatientContactNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPatientContactNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPatientContactNumActionPerformed

    private void jTxtPatientEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPatientEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPatientEmailActionPerformed

    private void jTxtPatientEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPatientEmailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPatientEmailKeyReleased

    private void jTxtBillConsultationFeeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtBillConsultationFeeKeyReleased
        // TODO add your handling code here:
        updateLivePreview();
    }//GEN-LAST:event_jTxtBillConsultationFeeKeyReleased

    private void jTxtBillDiscountMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtBillDiscountMouseReleased
        // TODO add your handling code here:
        updateLivePreview();
    }//GEN-LAST:event_jTxtBillDiscountMouseReleased

    private void jBtnSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnSettingMouseClicked
        // TODO add your handling code here:
        jPanelAppoinmentContext.setVisible(false);
        jPanelPatientContext.setVisible(false);
        jPanelBillsContext.setVisible(false);
        jPanelSettingContext.setVisible(true);
        jPanelTreatmentContext.setVisible(false);
        jPanelDentistContext.setVisible(false);
        jPanelUserContext.setVisible(false);
    }//GEN-LAST:event_jBtnSettingMouseClicked

    private void jTblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblUsersMouseClicked
        // TODO add your handling code here:
        int row = jTblUsers.getSelectedRow();

        String name = jTblUsers.getValueAt(row, 0).toString();
        String email = jTblUsers.getValueAt(row, 1).toString();
        Object stetusOb = jTblUsers.getValueAt(row, 2).toString();
        Object roleOb  = jTblUsers.getValueAt(row, 3).toString();

        jTxtUsersName.setText(name);
        jTxtUsersEmail.setText(email);
        if (stetusOb != null) {
            jCmbUserStatus.setSelectedItem(stetusOb.toString());
        }
        if (stetusOb != null) {
            jCmbUsersRole.setSelectedItem(roleOb.toString());
        }
      

        checkInputs();
        jBtnTreatmentSave.setEnabled(false);
    }//GEN-LAST:event_jTblUsersMouseClicked


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
    private javax.swing.JButton jBtnAppoinmentCancel;
    private javax.swing.JButton jBtnAppoinmentSave;
    private javax.swing.JButton jBtnAppoinmentUpdate;
    private javax.swing.JButton jBtnBill;
    private javax.swing.JButton jBtnDentist;
    private javax.swing.JButton jBtnDentistCancel;
    private javax.swing.JButton jBtnDentistCancel6;
    private javax.swing.JButton jBtnDentistDelete;
    private javax.swing.JButton jBtnDentistSave;
    private javax.swing.JButton jBtnDentistSave6;
    private javax.swing.JButton jBtnDentistUpdate;
    private javax.swing.JButton jBtnLogOut;
    private javax.swing.JButton jBtnPatient;
    private javax.swing.JButton jBtnPatientCancel;
    private javax.swing.JButton jBtnPatientDelete;
    private javax.swing.JButton jBtnPatientSave;
    private javax.swing.JButton jBtnPatientUpdate;
    private javax.swing.JLabel jBtnSetting;
    private javax.swing.JButton jBtnSettingChangePassword;
    private javax.swing.JButton jBtnSettingClear;
    private javax.swing.JButton jBtnSettingUpdate;
    private javax.swing.JButton jBtnTreatmentCancel;
    private javax.swing.JButton jBtnTreatmentDelete;
    private javax.swing.JButton jBtnTreatmentSave;
    private javax.swing.JButton jBtnTreatmentUpdate;
    private javax.swing.JButton jBtnTretment;
    private javax.swing.JButton jBtnUser;
    private javax.swing.JButton jBtnUsersAdd;
    private javax.swing.JButton jBtnUsersClear;
    private javax.swing.JButton jBtnUsersUpdate;
    private javax.swing.JComboBox<String> jCmbAppoinmentStetus;
    private javax.swing.JComboBox<Integer> jCmbDentistId;
    private javax.swing.JComboBox<String> jCmbDentistStetus;
    private javax.swing.JComboBox<Integer> jCmbPatientId;
    private javax.swing.JComboBox<Integer> jCmbTreatmentId;
    private javax.swing.JComboBox<String> jCmbUserStatus;
    private javax.swing.JComboBox<String> jCmbUsersRole;
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
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
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
    private javax.swing.JPanel jPanelUserContext;
    private javax.swing.JPanel jPlBillPreview;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTblAppoinment;
    private javax.swing.JTable jTblDentist;
    private javax.swing.JTable jTblPatient;
    private javax.swing.JTable jTblTreatment;
    private javax.swing.JTable jTblUsers;
    private javax.swing.JTextField jTxtAppoinmentDate;
    private javax.swing.JTextField jTxtAppoinmentDentist;
    private javax.swing.JTextField jTxtAppoinmentNumber;
    private javax.swing.JTextField jTxtAppoinmentPatient;
    private javax.swing.JTextField jTxtAppoinmentSearch;
    private javax.swing.JTextField jTxtAppoinmentTime;
    private javax.swing.JTextField jTxtAppoinmentTreatment;
    private javax.swing.JTextField jTxtBillAppoinmentNumber;
    private javax.swing.JTextField jTxtBillConsultationFee;
    private javax.swing.JTextField jTxtBillDiscount;
    private javax.swing.JTextField jTxtBillTotalFee;
    private javax.swing.JTextField jTxtBillTreatmentFee;
    private javax.swing.JFormattedTextField jTxtDentistContactNum;
    private javax.swing.JTextField jTxtDentistEmail;
    private javax.swing.JTextField jTxtDentistName;
    private javax.swing.JTextField jTxtDentistSpec;
    private javax.swing.JTextField jTxtPatientAddress;
    private javax.swing.JTextField jTxtPatientContactNum;
    private javax.swing.JTextField jTxtPatientEmail;
    private javax.swing.JTextField jTxtPatientName;
    private javax.swing.JTextField jTxtSettingName;
    private javax.swing.JTextField jTxtSettingNewPw;
    private javax.swing.JTextField jTxtSettingOldPw;
    private javax.swing.JTextField jTxtSettingReEnterNewPw;
    private javax.swing.JTextField jTxtSettingUserName;
    private javax.swing.JTextField jTxtTreatmentDescription;
    private javax.swing.JTextField jTxtTreatmentName;
    private javax.swing.JFormattedTextField jTxtTreatmentPrice;
    private javax.swing.JTextField jTxtUsersEmail;
    private javax.swing.JTextField jTxtUsersName;
    private javax.swing.JTextField jTxtUsersSearch;
    private javax.swing.JEditorPane txtLivePreview;
    // End of variables declaration//GEN-END:variables
}

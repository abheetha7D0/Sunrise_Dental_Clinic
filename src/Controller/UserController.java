/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import Enums.UserStetus;
import dao.DAOFactory;
import dao.costom.impl.TokenDAOImpl;
import dao.costom.impl.UserDAOImpl;
import dto.AddUserDTO;
import dto.UpdateUserDTO;
import dto.UserDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import model.User;
import util.EmailUtility;
import util.UserSession;
import static util.UserSession.getUserRole;
import view.DashbordForm;

/**
 *
 * @author ASUS
 */
public class UserController {

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOType.USER);

    TokenDAOImpl tokenDAO = (TokenDAOImpl) DAOFactory.getInstance().getDAO(DAOType.TOKEN);

    private final DashbordForm dasbordForm;

    public UserController(DashbordForm dasbordForm) {
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

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email != null && email.trim().matches(emailRegex);
    }

    public void UserUpdate(UpdateUserDTO user) {

        Role userRole = UserSession.getUserRole();

        try {
            Role roleByUserName = userDAO.getRoleByUserName(user.getUsername());
            if (!roleByUserName.equals(userRole)) {
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Access denied");
            System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        try {
            boolean UpdateUserByUser = userDAO.UpdateUserByUser(user.getUsername(), user.getPassword());
            if (UpdateUserByUser) {
                dasbordForm.showMessage("User Updated successfully");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("User Updated unsuccessfully");
            System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void register(){
        
    }

    public void addUser(AddUserDTO userDTO) {
        Role userRole = getUserRole();
        if (!Role.ADMIN.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return;
        }
        if (!isValidFullName(userDTO.getName())) {
            dasbordForm.showMessage("Please enter a valid full name (e.g., First and Last name)", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidEmail(userDTO.getEmail())) {
            dasbordForm.showMessage("Please enter a valid Email ", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            User savedUser = userDAO.addUser(new User(
                    userDTO.getEmail(),
                    "",
                    userDTO.getName(),
                    userDTO.getEmail(),
                    UserStetus.UNREGISTERED,
                    userDTO.getRole())
            );

            if (savedUser != null && savedUser.getId() > 0) {

                String secureToken = UUID.randomUUID().toString();

                boolean isTokenSaved = tokenDAO.saveToken(secureToken, savedUser.getId());

                if (isTokenSaved) {
                    String emailSubject = "Your Account Verification Code";

                    String htmlContent = "<h2>Account Verification</h2>"
                            + "<p>Dear " + savedUser.getFullName() + ",</p>"
                            + "<p>Please use the following verification token to activate your account:</p>"
                            + "<div style='background-color: #f4f4f4; padding: 15px; border-radius: 5px; font-family: monospace; font-size: 18px; font-weight: bold; color: #333; letter-spacing: 1px; display: inline-block;'>"
                            + secureToken
                            + "</div>"
                            + "<p>Copy and paste this code directly into your application verification panel.</p>";

                    EmailUtility.sendEmail(savedUser.getEmail(), emailSubject, htmlContent);

                    dasbordForm.showMessage("User saved! Verification code has been sent to your email.");
                } else {
                    dasbordForm.showMessage("User created, but token processing failed.");
                }
            } else {
                dasbordForm.showMessage("Failed to create user account record.");
            }

        } catch (SQLException ex) {
            System.err.println("Transaction crash inside addUser controller context: " + ex.getMessage());
            dasbordForm.showMessage("Database error: Account registration failed.");
        }
    }

    public List<AddUserDTO> getAll() {
        String userName = UserSession.getUserName();
        Role userRole = UserSession.getUserRole();
        System.out.println(!Role.ADMIN.equals(userRole) && !Role.DENTIST.equals(userRole));

        if (!Role.ADMIN.equals(userRole) && !Role.STAFF.equals(userRole)) {
            dasbordForm.showMessage("Access denied");
            return null;
        }

        List<User> allUser = null;
        try {
            allUser = userDAO.getAllUsers();
        } catch (SQLException ex) {
            dasbordForm.showMessage("Get all User null");
        }
        List<AddUserDTO> userList = new ArrayList<>();

        for (User user : allUser) {

            userList.add(new AddUserDTO(
                    user.getFullName(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getRole()
            ));
        }
        return userList;
    }
}

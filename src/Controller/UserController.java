/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Enums.DAOType;
import Enums.Role;
import dao.DAOFactory;
import dao.costom.impl.UserDAOImpl;
import java.sql.SQLException;
import util.UserSession;
import view.DashbordForm;


/**
 *
 * @author ASUS
 */
public class UserController {

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOType.USER);
    private final DashbordForm dasbordForm;

    public UserController(DashbordForm dasbordForm) {
        this.dasbordForm = dasbordForm;
    }

    public void UserUpdate(String userName,String name) {
        Role userRole = UserSession.getUserRole();
        
        try {
            Role roleByUserName = userDAO.getRoleByUserName(userName);
            if (!roleByUserName.equals(userRole)) {
        }
        } catch (SQLException ex) {
            dasbordForm.showMessage("Access denied");
            System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        try {
            boolean UpdateUserByUser = userDAO.UpdateUserByUser(userName, name);
            if (UpdateUserByUser) {
                dasbordForm.showMessage("User Updated successfully");
            }
        } catch (SQLException ex) {
            dasbordForm.showMessage("User Updated unsuccessfully");
            System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
                
        
       
    }
}

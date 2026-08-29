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
import model.User;
import util.UserSession;
import static util.UserSession.createSession;
import static util.UserSession.getUserName;
import static util.UserSession.getUserRole;
import view.DashbordForm;
import view.Login;

/**
 *
 * @author ASUS
 */
public class UserController {

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOType.USER);
    private final Login loginView;

    public UserController(Login loginView) {
        this.loginView = loginView;
    }

    public void logIn(String userName, String password) {
        try {
            if (userDAO.Login(new User(userName, password))) {
                Role roleByUserName = userDAO.getRoleByUserName(userName);
                createSession(userName, roleByUserName);
                DashbordForm form = new DashbordForm();
                form.setVisible(true);
                loginView.dispose();
                

            } else {
                loginView.showMessage("Invalid Username or Password");
            }
        } catch (SQLException ex) {
            System.getLogger(Login.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}

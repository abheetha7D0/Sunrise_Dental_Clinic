/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import Enums.Role;
import dao.SuperDAO;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author ASUS
 */
public interface UserDAO extends SuperDAO{
    boolean Login(User user)  throws SQLException;
    User addUser(User user)  throws SQLException;
    User updateUser(User user)  throws SQLException;
    User userDelete(User user)  throws SQLException;
    Role getRoleByUserName(String userName)throws SQLException;
    
    
}

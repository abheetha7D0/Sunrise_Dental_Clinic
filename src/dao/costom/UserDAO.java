/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import Enums.Role;
import dao.SuperDAO;
import java.sql.SQLException;
import java.util.List;
import model.User;

/**
 *
 * @author ASUS
 */
public interface UserDAO extends SuperDAO{
    boolean Login(User user)  throws SQLException;
    boolean registerUser(String token, String username, String password)  throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User addUser(User user)  throws SQLException;
    User updateUser(User user)  throws SQLException;
    User userDelete(User user)  throws SQLException;
    String getPasswordByUseName(String userName)  throws SQLException;
    boolean UpdateUserByUser(String userName, String name)  throws SQLException;
    boolean updatePassword(String userName, String password)  throws SQLException;
    Role getRoleByUserName(String userName)throws SQLException;
    
    
}

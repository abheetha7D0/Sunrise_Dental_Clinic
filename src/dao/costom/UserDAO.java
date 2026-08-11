/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.costom;

import dao.SuperDAO;
import dto.UserDTO;
import model.User;

/**
 *
 * @author ASUS
 */
public interface UserDAO extends SuperDAO{
    boolean Login(UserDTO userDTO);
    User addUser(User user);
    User updateUser(User user);
    User userDelete(User user);
}

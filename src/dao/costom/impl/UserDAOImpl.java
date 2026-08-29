/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import dao.costom.UserDAO;
import db.DBConnection;
import dto.UserDTO;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean Login(User user)  throws SQLException{
        String sql = "SELECT * FROM USERS WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();

            return rs.next(); 

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    @Override
    public User addUser(User user)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User updateUser(User user)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User userDelete(User user)  throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

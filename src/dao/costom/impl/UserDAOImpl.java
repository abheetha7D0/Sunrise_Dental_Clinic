/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import Enums.Role;
import dao.costom.UserDAO;
import db.DBConnection;
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
    public boolean Login(User user) throws SQLException {
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
    public User addUser(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User updateUser(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User userDelete(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Role getRoleByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE username = ?";
        Role role = null;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String userRole = rs.getString(6);

            if (userRole != null) {
                role = Role.valueOf(userRole.toUpperCase());
            }
        }
        return role;

    }

    @Override
    public boolean UpdateUserByUser(String userName, String Name) throws SQLException {
        String sql = "UPDATE USERS SET full_name=?, WHERE username=?";

        Connection con = DBConnection.getConnection();

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, Name);

        pst.setString(2, userName);

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;

    }

    @Override
    public boolean updatePassword(String userName, String password) throws SQLException {
        String sql = "UPDATE USERS SET password=?, WHERE username=?";

        Connection con = DBConnection.getConnection();

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

        pst.setString(1, password);

        pst.setString(2, userName);

        int executeUpdate = pst.executeUpdate();
        con.close();
        return executeUpdate > 0;
    }

    @Override
    public String getPasswordByUseName(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE username = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(3);
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
}

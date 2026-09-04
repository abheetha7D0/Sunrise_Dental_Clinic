/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import Enums.Role;
import Enums.UserStetus;
import dao.costom.UserDAO;
import db.DBConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Connection con = (Connection) DBConnection.getConnection();

        String sql = "INSERT INTO users(username,password,email,full_name,status,role) VALUES(?,?,?,?,?,?)";

        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, user.getUsername());

        pst.setString(2, user.getPassword());

        pst.setString(3, user.getEmail());

        pst.setString(4, user.getFullName());

        pst.setString(5, user.getStatus().name());

        pst.setString(6, user.getRole().name());

        int affectedRows = pst.executeUpdate();

        if (affectedRows > 0) {

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }

        con.close();
        System.out.println("User Added Successfully with ID: " + user.getId());
        return user;
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
            String userRole = rs.getString(7);

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

    @Override
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        Connection con = (Connection) DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);

            String username = rs.getString(2);
            String password = rs.getString(3);
            String fullName = rs.getString(4);
            String email = rs.getString(5);
            UserStetus status = UserStetus.valueOf(rs.getString(6).toUpperCase());
            Role role = Role.valueOf(rs.getString(7).toUpperCase());

            User user = new User(id, username, password, fullName, email, status, role);
            userList.add(user);
        }

        con.close();

        return userList;
    }

    @Override
    public boolean registerUser(String token, String username, String password) throws SQLException {
        String findTokenSql = "SELECT user_id FROM user_registration_tokens WHERE token = ?";
        String updateUserSql = "UPDATE users SET username = ?, password = ?, status = ? WHERE id = ?";
        String deleteTokenSql = "DELETE FROM user_registration_tokens WHERE token = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            int userId = -1;
            try (PreparedStatement pstToken = con.prepareStatement(findTokenSql)) {
                pstToken.setString(1, token);
                try (ResultSet rs = pstToken.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    } else {
                        con.rollback();
                        return false;
                    }
                }
            }

            try (PreparedStatement pstUser = con.prepareStatement(updateUserSql)) {
                pstUser.setString(1, username);
                pstUser.setString(2, password);
                pstUser.setString(3, UserStetus.ACTIVATE.name());
                pstUser.setInt(4, userId);

                if (pstUser.executeUpdate() <= 0) {
                    con.rollback();
                    return false;
                }
            }

            try (PreparedStatement pstDel = con.prepareStatement(deleteTokenSql)) {
                pstDel.setString(1, token);
                pstDel.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException ex) {
            if (con != null) {
                con.rollback();
            }
            throw ex;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}

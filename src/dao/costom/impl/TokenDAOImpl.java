/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.costom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import dao.costom.TokenDAO;
import db.DBConnection;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class TokenDAOImpl implements TokenDAO{

    @Override
    public boolean saveToken(String token, int userId) throws SQLException {
        String sql = "INSERT INTO user_registration_tokens (token, user_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
    
}

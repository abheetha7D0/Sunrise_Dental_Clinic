/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ASUS
 */
public class DBConnection {
     public static void main(String[] args) {
        getConnection();
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sunrise_dental_clinic?useSSL=false", "root", "1234");
            System.out.println("Database Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}

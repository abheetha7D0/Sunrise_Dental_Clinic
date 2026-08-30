/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import Enums.Role;

/**
 *
 * @author ASUS
 */
public class UserSession {
    private static String loginUser;
    private static Role logginUserRole;
    
    public static void createSession(String username,Role role){
        loginUser = username;
        logginUserRole = role;
    }
    
    public static void clearSession(){
        loginUser = "";
        logginUserRole = null;
    }
    
    public static String getUserName(){
        return loginUser;
    }
    public static Role getUserRole(){
        return logginUserRole;
    }
    
}

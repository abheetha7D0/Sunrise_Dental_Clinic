/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Enums.Role;
import Enums.UserStetus;

/**
 *
 * @author ASUS
 */
public class User {
    
    private Long id;

    private String username;

    private String password;
    
    private String fullName;
    
    private UserStetus stetus;
    
    private Role role;

    public User(String username, String password, String fullName, UserStetus stetus,Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.stetus = stetus;
        this.role = role;
    }

    public void setStetus(UserStetus stetus) {
        this.stetus = stetus;
    }
    
}

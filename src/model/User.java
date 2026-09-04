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
    
    private int id;

    private String username;

    private String password;
    
    private String fullName;
    
    private String email;
    
    private UserStetus status;
    
    private Role role;

    public User(int id, String username, String password, String fullName, String email, UserStetus stetus, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.status = stetus;
        this.role = role;
    }

    public User(String username, String password, String fullName, String email, UserStetus status, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public User(String username, String password, String fullName, UserStetus stetus,Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.status = stetus;
        this.role = role;
    }

    public User(int id, String username, String password, String fullName, UserStetus stetus, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.status = stetus;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setStatus(UserStetus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public UserStetus getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}

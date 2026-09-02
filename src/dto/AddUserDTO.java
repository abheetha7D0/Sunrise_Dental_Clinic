/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.Role;
import Enums.UserStetus;

/**
 *
 * @author ASUS
 */
public class AddUserDTO {

    private String name;
    private String email;
    private UserStetus status;
    private Role role;

    public AddUserDTO(String name, String email, UserStetus status, Role role) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(UserStetus status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserStetus getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

}

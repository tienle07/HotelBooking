/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author TienLV
 */
public class UserDTO implements Serializable {

    private String email;
    private String password;
    private String role;
    private String phone;
    private String name;
    private String address;
    private String createdDate;
    private String status;

    public UserDTO() {
    }

    public UserDTO(String email, String password, String phone, String name, String address, String createdDate, String status, String role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.createdDate = createdDate;
        this.status = status;
        this.role = role;
    }

    public UserDTO(String email, String password, String phone, String name, String address) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Account_Info;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Account_InfoDTO implements Serializable{
    
    private String userID, phone, name, address;
    int roleID, statusID;
    Date create_date;

    public Account_InfoDTO() {
    }

    public Account_InfoDTO(String userID, String phone, String name, String address, int roleID, int statusID, Date create_date) {
        this.userID = userID;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.roleID = roleID;
        this.statusID = statusID;
        this.create_date = create_date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
    
    
}

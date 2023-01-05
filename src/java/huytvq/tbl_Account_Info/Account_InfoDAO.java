/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Account_Info;

import huytvq.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class Account_InfoDAO implements Serializable {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void closeConnection() throws SQLException, NamingException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public Account_InfoDTO getUserInfo(String userID) throws SQLException, NamingException {
        Account_InfoDTO dto = null;
        try {
            conn = DBHelper.getConnection();
                String sql = "Select Phone, Name, Address, Create_Date, RoleID, StatusID "
                        + "From tbl_Account_Info "
                        + "Where UserID = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("Phone");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    Date createDate = rs.getDate("Create_Date");
                    int roleID = rs.getInt("RoleID");
                    int statusID = rs.getInt("StatusID");
                    dto = new Account_InfoDTO(userID, phone, name, address, roleID, statusID, createDate);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public int verifyAccount(String userID) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "Update tbl_Account_Info "
                    + "Set StatusID = 2 "
                    + "Where UserID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            int row = ps.executeUpdate();
            if (row > 0) {
                return 1;
            }
        } finally {
            closeConnection();
        }
        return 0;
    }

    public boolean createAccount_Info(Account_InfoDTO dto) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "Insert Into tbl_Account_Info(UserID, Phone, Name, Address, Create_Date, RoleID, StatusID )"
                    + " Values(?,?,?,?,?,?,?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getUserID());
            ps.setString(2, dto.getPhone());
            ps.setString(3, dto.getName());
            ps.setString(4, dto.getAddress());
            ps.setTimestamp(5, new Timestamp(dto.getCreate_date().getTime()));
            ps.setInt(6, dto.getRoleID());
            ps.setInt(7, dto.getStatusID());
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

}

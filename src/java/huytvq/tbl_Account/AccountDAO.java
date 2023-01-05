/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Account;

import huytvq.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class AccountDAO implements Serializable {

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

    public boolean checkLogin(String userID, String password) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
                String sql = "Select UserID, Password "
                        + "From tbl_Account "
                        + "Where UserID = ? And Password = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public boolean createAccount(String userID, String password) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "Insert Into tbl_Account(UserID, Password)"
                    + " Values(?,?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, password);
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

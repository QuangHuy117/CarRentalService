/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_DiscountDetail;

import huytvq.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class DiscountDetailDAO implements Serializable {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

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

    public boolean createDiscountDetail(DiscountDetailDTO dto) throws SQLException, NamingException {

        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT INTO tbl_Discount_Detail(DiscountID, UserID, Code, ExpiredDate, StatusID) "
                    + " VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getDiscountID());
            ps.setString(2, dto.getUserID());
            ps.setString(3, dto.getCode());
            ps.setTimestamp(4, new Timestamp(dto.getExpiredDate().getTime()));
            ps.setInt(5, 2);
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateStatusDiscount(String userID) throws SQLException, NamingException {

        try {
            conn = DBHelper.getConnection();
            String sql = "UPDATE tbl_Discount_Detail"
                    + " SET StatusID = 3"
                    + " WHERE UserID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }

        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean checkDiscountAvailableByDate(String code) throws SQLException, NamingException {

        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT DiscountDetailID"
                    + " FROM tbl_Discount_Detail"
                    + " WHERE ExpiredDate >= GETDATE()"
                    + " AND Code = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean checkDiscountAvailableByStatus(String code) throws SQLException, NamingException {

        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT DiscountDetailID"
                    + " FROM tbl_Discount_Detail"
                    + " WHERE StatusID = 2"
                    + " AND Code = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public int getDiscountPrice(String userID, String discountID) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT (SELECT d.DiscountPrice"
                    + "	FROM tbl_Discount d\n"
                    + "	WHERE d.DiscountID = dd.DiscountID) AS Price"
                    + " FROM tbl_Discount_Detail dd"
                    + " WHERE (StatusID = 2 OR ExpiredDate < GETDATE())"
                    + " AND UserID = ? AND DiscountID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, discountID);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("Price");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public String getCodeDiscount(String userID) throws SQLException, NamingException {
        String code = "";
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT Code"
                    + " FROM tbl_Discount_Detail"
                    + " WHERE UserID = ? AND StatusID = 2 AND DiscountID = 'DC-1'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                code = rs.getString("Code");
            }
        } finally {
            closeConnection();
        }
        return code;
    }

    public String getDiscountID(String code) throws SQLException, NamingException {
        String id = "";
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT DiscountID"
                    + " FROM tbl_Discount_Detail"
                    + " WHERE Code = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("DiscountID");
            }
        } finally {
            closeConnection();
        }
        return id;
    }

}

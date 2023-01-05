/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Order;

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
public class OrderDAO implements Serializable {

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

    public boolean createOrder(OrderDTO dto) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT INTO tbl_Order(OrderID, UserID, Order_Create_Date, Total_Price, DiscountPrice, TotalPayable, DiscountID, StatusID)"
                    + " VALUES(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getOrderID());
            ps.setString(2, dto.getUserID());
            ps.setTimestamp(3, new Timestamp(dto.getOrder_create_date().getTime()));
            ps.setInt(4, dto.getTotal_price());
            ps.setInt(5, dto.getDiscountPrice());
            ps.setInt(6, dto.getTotalPayable());
            ps.setString(7, dto.getDiscountID());
            ps.setInt(8, dto.getStatus());
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public String getNewestOrder() throws SQLException, NamingException {
        String id = "";
        try {
            conn = DBHelper.getConnection();
            String sql = " SELECT OrderID "
                    + " FROM tbl_Order "
                    + " WHERE OrderID = (SELECT MAX(OrderID) FROM tbl_Order)";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("OrderID");
            }
        } finally {
            closeConnection();
        }
        return id;
    }

    public boolean updateOrderStatus(String orderID) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "UPDATE tbl_Order "
                    + " SET StatusID = 2"
                    + " WHERE OrderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderID);
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

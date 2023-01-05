/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Order_Detail;

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
public class Order_DetailDAO implements Serializable {

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
    
    public boolean createOrderDetail(Order_DetailDTO dto) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT INTO tbl_Order_Detail(OrderID, OrderName, CarID, Amount, RentalDate, ReturnDate)"
                    + " VALUES(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getOrderID());
            ps.setString(2, dto.getOrderName());
            ps.setString(3, dto.getCarID());
            ps.setInt(4, dto.getAmount());
            ps.setTimestamp(5, new Timestamp(dto.getRentalDate().getTime()));
            ps.setTimestamp(6, new Timestamp(dto.getReturnDate().getTime()));
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

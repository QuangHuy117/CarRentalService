/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Car_Category;

import huytvq.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class Car_CategoryDAO implements Serializable {

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

    public List<Car_CategoryDTO> getListCategory() throws SQLException, NamingException {
        List<Car_CategoryDTO> list = null;
        Car_CategoryDTO dto = null;

        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT CategoryID, CategoryName "
                    + " FROM tbl_Car_Category";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String cateID = rs.getString("CategoryID");
                String cateName = rs.getString("CategoryName");
                dto = new Car_CategoryDTO(cateID, cateName);

                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}

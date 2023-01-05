/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Car;

import huytvq.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class CarDAO implements Serializable {

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

    public int getCountCar() throws SQLException, NamingException {
        int count = 0;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT COUNT(c.CarID) "
                    + " FROM tbl_Car c \n"
                    + " WHERE c.IsAvailable = 'true'\n"
                    + " AND 1 <= (c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(getdate() AS date) AND od.RentalDate <= CAST(getdate()+10 AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(getdate() AS date) AND od.ReturnDate <= CAST(getdate()+10 AS date))\n"
                    + "	OR (od.RentalDate < CAST(getdate() AS date) AND od.ReturnDate > CAST(getdate()+10 AS date)))),0))";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public List<CarDTO> getListCarDefault(int curPage) throws SQLException, NamingException {
        List<CarDTO> list = null;
        CarDTO dto = null;

        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT CarID, CarName, Price, Year, \n"
                    + "	(SELECT cc.CategoryName\n"
                    + "	FROM tbl_Car_Category cc\n"
                    + "	WHERE cc.CategoryID = c.CategoryID) AS CategoryName, \n"
                    + "	(c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(getdate() AS date) AND od.RentalDate <= CAST(getdate()+10 AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(getdate() AS date) AND od.ReturnDate <= CAST(getdate()+10 AS date))\n"
                    + "	OR (od.RentalDate < CAST(getdate() AS date) AND od.ReturnDate > CAST(getdate()+10 AS date)))),0)) AS Quantity\n"
                    + " FROM tbl_Car c \n"
                    + " WHERE c.IsAvailable = 'true'\n"
                    + " AND 1 <= (c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(getdate() AS date) AND od.RentalDate <= CAST(getdate()+10 AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(getdate() AS date) AND od.ReturnDate <= CAST(getdate()+10 AS date))\n"
                    + "	OR (od.RentalDate < CAST(getdate() AS date) AND od.ReturnDate > CAST(getdate()+10 AS date)))),0))"
                    + " ORDER BY CarID\n"
                    + " OFFSET (? - 1) * 20 ROWS\n"
                    + " FETCH NEXT 20 ROWS ONLY;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, curPage);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String carID = rs.getString("CarID");
                String carName = rs.getString("CarName");
                int price = rs.getInt("Price");
                int year = rs.getInt("Year");
                String cateName = rs.getString("CategoryName");
                int quantity = rs.getInt("Quantity");
                dto = new CarDTO(carID, carName, cateName, year, price, quantity);

                list.add(dto);
            }
        } finally {
            closeConnection();
        }

        return list;
    }

    public List<CarDTO> searchCarByCarNameOrCategoryWithAmount(String car_Name, String category_id, int amount, Date rentalDate, Date returnDate) throws SQLException, NamingException {
        List<CarDTO> list = null;
        CarDTO dto = null;

        try {
            conn = DBHelper.getConnection();
            String sql = " DECLARE @rentalDate AS Date = ? \n"
                    + " DECLARE @returnDate AS Date = ? \n"
                    + " SELECT c.CarID, c.CarName, c.Price, C.Year, \n"
                    + "	(SELECT cc.CategoryName\n"
                    + "	FROM tbl_Car_Category cc\n"
                    + "	WHERE cc.CategoryID = c.CategoryID) AS CategoryName, \n"
                    + "	(c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(@rentalDate AS date) AND od.RentalDate <= CAST(@returnDate AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(@rentalDate AS date) AND od.ReturnDate <= CAST(@returnDate AS date))\n"
                    + "	OR (od.RentalDate < CAST(@rentalDate AS date) AND od.ReturnDate > CAST(@returnDate AS date)))),0)) AS Quantity\n"
                    + " FROM tbl_Car c \n"
                    + " WHERE c.IsAvailable = 'true' AND (c.CarName LIKE ? OR c.CategoryID = ?)\n"
                    + " AND ? <=  (c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(@rentalDate AS date) AND od.RentalDate <= CAST(@returnDate AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(@rentalDate AS date) AND od.ReturnDate <= CAST(@returnDate AS date))\n"
                    + "	OR (od.RentalDate < CAST(@rentalDate AS date) AND od.ReturnDate > CAST(@returnDate AS date)))),0))";

            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(rentalDate.getTime()));
            ps.setTimestamp(2, new Timestamp(returnDate.getTime()));
            if (car_Name == null || car_Name.isEmpty()) {
                ps.setString(3, "");
            } else {
                ps.setString(3, "%" + car_Name + "%");
            }
            if (category_id == null || category_id.isEmpty()) {
                ps.setString(4, "");
            } else {
                ps.setString(4, category_id);
            }
            ps.setInt(5, amount);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String carID = rs.getString("CarID");
                String carName = rs.getString("CarName");
                int price = rs.getInt("Price");
                int year = rs.getInt("Year");
                String categoryName = rs.getString("CategoryName");
                int quantity = rs.getInt("Quantity");
                dto = new CarDTO(carID, carName, categoryName, year, price, quantity);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }

        return list;
    }

    public CarDTO getCarByID(String carID) throws SQLException, NamingException {
        CarDTO dto = null;
        try {
            conn = DBHelper.getConnection();
            String sql = " SELECT CarName, Year, Price, Quantity, IsAvailable, \n"
                    + "	(SELECT cc.CategoryName\n"
                    + "	FROM tbl_Car_Category cc\n"
                    + "	WHERE cc.CategoryID = c.CategoryID) AS CategoryName,\n"
                    + "	(c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID\n"
                    + "	AND ((od.RentalDate >= GETDATE() AND od.RentalDate <= GETDATE() + 10)\n"
                    + "	OR (od.ReturnDate >= GETDATE() AND od.ReturnDate <= GETDATE() + 10)\n"
                    + "	OR (od.RentalDate < GETDATE() AND od.ReturnDate > GETDATE() + 10))),0)) AS MaxCar\n"
                    + " FROM tbl_Car c\n"
                    + " WHERE c.IsAvailable = 'true' AND c.CarID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, carID);
            rs = ps.executeQuery();
            if (rs.next()) {
                String carName = rs.getString("CarName");
                String categoryName = rs.getString("CategoryName");
                int year = rs.getInt("Year");
                int price = rs.getInt("Price");
                int quantity = rs.getInt("Quantity");
                int maxCar = rs.getInt("MaxCar");
                boolean isAvai = rs.getBoolean("IsAvailable");
                dto = new CarDTO(carID, carName, categoryName, year, price, quantity, maxCar, isAvai);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkCarIsAvailable(String carID, int amount) throws SQLException, NamingException {
        try {
            conn = DBHelper.getConnection();
            String sql = " SELECT CarID\n"
                    + " FROM tbl_Car c \n"
                    + " WHERE c.IsAvailable = 'true' AND c.CarID = ?\n"
                    + " AND ? <= (c.Quantity - ISNULL((SELECT sum(od.Amount)\n"
                    + "	FROM tbl_Order_Detail od\n"
                    + "	WHERE od.CarID = c.CarID \n"
                    + "	AND ((od.RentalDate >= CAST(getdate() AS date) AND od.RentalDate <= CAST(getdate()+10 AS date)) \n"
                    + "	OR (od.ReturnDate >= CAST(getdate() AS date) AND od.ReturnDate <= CAST(getdate()+10 AS date))\n"
                    + "	OR (od.RentalDate < CAST(getdate() AS date) AND od.ReturnDate > CAST(getdate()+10 AS date)))),0))";
            ps = conn.prepareStatement(sql);
            ps.setString(1, carID);
            ps.setInt(2, amount);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Order_Detail;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Order_DetailDTO implements Serializable{
    
    String orderID, orderName, carID;
    int amount;
    Date rentalDate, returnDate;

    public Order_DetailDTO() {
    }

    public Order_DetailDTO(String orderID, String orderName, String carID, int amount, Date rentalDate, Date returnDate) {
        this.orderID = orderID;
        this.orderName = orderName;
        this.carID = carID;
        this.amount = amount;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    
    
}

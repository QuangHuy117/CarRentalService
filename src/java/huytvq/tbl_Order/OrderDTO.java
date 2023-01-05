/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Order;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class OrderDTO implements Serializable{
    
    private String orderID, userID, discountID;
    Date order_create_date;
    int total_price, discountPrice, totalPayable, status;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String userID, String discountID, Date order_create_date, int total_price, int discountPrice, int totalPayable, int status) {
        this.orderID = orderID;
        this.userID = userID;
        this.discountID = discountID;
        this.order_create_date = order_create_date;
        this.total_price = total_price;
        this.discountPrice = discountPrice;
        this.totalPayable = totalPayable;
        this.status = status;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(int totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public Date getOrder_create_date() {
        return order_create_date;
    }

    public void setOrder_create_date(Date order_create_date) {
        this.order_create_date = order_create_date;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
    
    
    
}

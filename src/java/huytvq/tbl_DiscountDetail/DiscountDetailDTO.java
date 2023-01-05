/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_DiscountDetail;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class DiscountDetailDTO implements Serializable{
    
    private String discountID, userID, code;
    Date expiredDate;
    int statusID;

    public DiscountDetailDTO(String discountID, String userID, String code, Date expiredDate, int statusID) {
        this.discountID = discountID;
        this.userID = userID;
        this.code = code;
        this.expiredDate = expiredDate;
        this.statusID = statusID;
    }

    
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountDetailDTO() {
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    
    
    
}

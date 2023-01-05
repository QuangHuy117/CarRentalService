/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Discount;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class DiscountDTO implements Serializable{
    
    private String discountID, discountName;
    int discountPrice;

    public DiscountDTO() {
    }

    public DiscountDTO(String discountID, String discountName, int discountPrice) {
        this.discountID = discountID;
        this.discountName = discountName;
        this.discountPrice = discountPrice;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }
    
    
    
}

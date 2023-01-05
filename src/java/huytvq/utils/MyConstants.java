/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.utils;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class MyConstants implements Serializable{
    
    //paging
    public static final int TOTAL_ITEM_IN_PAGE = 20;
    //Status
    public static final int STATUS_NEW = 1,
            STATUS_ACTIVE = 2,
            STATUS_INACTIVE = 3,
            STATUS_ACCEPT = 4,
            STATUS_DELETE = 5;
    
    //Recaptcha Google
    public static final String SITE_KEY = "6LfI8vUdAAAAAPHtzRQ657p-gcF-a_3fkCaDdY9e";
    public static final String SECRET_KEY = "6LfI8vUdAAAAAAFFsT8otgsC1MdXhDUdVLXAdryT";
}

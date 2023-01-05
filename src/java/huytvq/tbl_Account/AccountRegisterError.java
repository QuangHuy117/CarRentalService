/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Account;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AccountRegisterError implements Serializable{
    
    private String emailErr;

    public AccountRegisterError() {
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.cart;

import huytvq.tbl_Car.CarDTO;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class CarCart implements Serializable {

    private HashMap<String, CarDTO> cart;
    private String accountName;

    public HashMap<String, CarDTO> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, CarDTO> cart) {
        this.cart = cart;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CarCart() {
    }

    public CarCart(HashMap<String, CarDTO> cart, String accountName) {
        this.cart = cart;
        this.accountName = accountName;
    }

    public CarCart(String accountName) {
        this.accountName = accountName;
        this.cart = new HashMap<>();
    }

    public void addToCart(CarDTO dto) throws Exception {
        if (this.cart.containsKey(dto.getCarID())) {
            int newQuantity = this.cart.get(dto.getCarID()).getQuantity() + 1;
            this.cart.get(dto.getCarID()).setQuantity(newQuantity);
        } else {
            this.cart.put(dto.getCarID(), dto);
        }
    }

    
    public void remove(String id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }
    
    public int getTotal() throws Exception {
        int result = 0;
        for (CarDTO dto : this.cart.values()) {
            result += dto.getQuantity() * dto.getPrice();
        }
        return result;
    }
    
    public void updateToCart(String id, int quantity) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setQuantity(quantity);
        }
    }
}

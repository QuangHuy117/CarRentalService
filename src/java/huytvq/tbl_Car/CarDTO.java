/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.tbl_Car;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CarDTO implements Serializable {

    private String carID, carName, categoryID, categoryName;
    int year, price, quantity, maxCar;
    boolean isAvailable;

    public CarDTO() {
    }

//    public CarDTO(String carID, String carName, String categoryName, int year, int price, int quantity, int maxCar) {
//        this.carID = carID;
//        this.carName = carName;
//        this.categoryName = categoryName;
//        this.year = year;
//        this.price = price;
//        this.quantity = quantity;
//        this.maxCar = maxCar;
//    }

    public CarDTO(String carID, String carName, String categoryName, int year, int price, int quantity, int maxCar, boolean isAvailable) {
        this.carID = carID;
        this.carName = carName;
        this.categoryName = categoryName;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
        this.maxCar = maxCar;
        this.isAvailable = isAvailable;
    }

    public CarDTO(String carID, String carName, String categoryName, int year, int price, int quantity) {
        this.carID = carID;
        this.carName = carName;
        this.categoryName = categoryName;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxCar() {
        return maxCar;
    }

    public void setMaxCar(int maxCar) {
        this.maxCar = maxCar;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}

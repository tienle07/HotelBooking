/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author TienLV
 */
public class CartItemDTO implements Serializable {

    private int roomID;
    private float oneDayPrice;
    private String roomType;
    private float price;
    private String checkInDate;
    private String checkOutDate;
    private String dateOfStay;

    public CartItemDTO() {
    }

    public CartItemDTO(int roomID, float oneDayPrice, String roomType, float price, String checkInDate, String checkOutDate, String dateOfStay) {
        this.roomID = roomID;
        this.oneDayPrice = oneDayPrice;
        this.roomType = roomType;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.dateOfStay = dateOfStay;
    }

    public String getDateOfStay() {
        return dateOfStay;
    }

    public void setDateOfStay(String dateOfStay) {
        this.dateOfStay = dateOfStay;
    }

    public float getOneDayPrice() {
        return oneDayPrice;
    }

    public void setOneDayPrice(float oneDayPrice) {
        this.oneDayPrice = oneDayPrice;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

}

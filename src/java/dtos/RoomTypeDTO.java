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
public class RoomTypeDTO implements Serializable {

    private int roomTypeID;
    private String roomType;
    private float price;
    private String description;

    public RoomTypeDTO() {
    }

    public RoomTypeDTO(int roomTypeID, String roomType, float price, String description) {
        this.roomTypeID = roomTypeID;
        this.roomType = roomType;
        this.price = price;
        this.description = description;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author TienLV
 */
public class BookingDTO implements Serializable {

    private int bookingID;
    private String email;
    private float totalPrice;
    private String bookingDate;
    private ArrayList<BookingDetailDTO> bookingDetail;
    

    public BookingDTO() {
    }

    public BookingDTO(int bookingID, String email, float totalPrice, String bookingDate, ArrayList<BookingDetailDTO> bookingDetail) {
        this.bookingID = bookingID;
        this.email = email;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.bookingDetail = bookingDetail;
        
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public ArrayList<BookingDetailDTO> getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(ArrayList<BookingDetailDTO> bookingDetail) {
        this.bookingDetail = bookingDetail;
    }   
    
}

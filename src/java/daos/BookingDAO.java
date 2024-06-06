/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.BookingDTO;
import dtos.BookingDetailDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DBUtilties;
import utils.DateUtils;

/**
 *
 * @author TienLV
 */
public class BookingDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public int insertBooking(String email, float totalPrice) throws Exception {
        int insertedID = 0;
        DateUtils date = new DateUtils();
        String sql = "INSERT INTO tblBooking (email, totalPrice, bookingDate, status, verified)"
                + " OUTPUT Inserted.bookingID"
                + " VALUES (?,?,?,?,?)";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setFloat(2, totalPrice);
            preStm.setTimestamp(3, java.sql.Timestamp.valueOf(date.getCurrentDate()));
            preStm.setBoolean(4, true);
            preStm.setBoolean(5, false);
            rs = preStm.executeQuery();
            if (rs.next()) {
                insertedID = rs.getInt("bookingID");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return insertedID;
    }
    

    public boolean isExisted(int bookingID) throws Exception {
        boolean isExisted = false;
        String sql = "SELECT bookingID FROM tblBooking WHERE bookingID = ?"
                + " AND status = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, bookingID);
            preStm.setBoolean(2, true);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return isExisted;
    }

    public ArrayList<BookingDTO> getBookingHistory(String email, String fromDate, String toDate, String bookingName) throws Exception {
        ArrayList<BookingDTO> listBooking = new ArrayList<>();
        String sql = "SELECT b.bookingID, b.email, b.totalPrice, b.bookingDate, b.verified,"
                + " bd.roomID, bd.price, bd.checkInDate, bd.checkOutDate, bd.feedback, bd.bookingDetailID"
                + " FROM tblBooking b"
                + " INNER JOIN tblBookingDetail bd on b.bookingID = bd.bookingID"
                + " INNER JOIN tblRoom r on bd.roomID = r.roomID"
                + " INNER JOIN tblHotel h on r.hotelID = h.hotelID"
                + " WHERE b.email = ?"
                + " AND b.status = ?";

        if (fromDate != null && !fromDate.equals("")) {
            sql += " AND b.bookingDate >= '" + fromDate + "'";
        }
        if (toDate != null && !toDate.equals("")) {
            sql += " AND b.bookingDate <= '" + toDate + "'";
        }
        if (bookingName != null || !bookingName.equals("")) {
            sql += " AND h.hotelName LIKE '%" + bookingName + "%'";
        }
        sql += " ORDER BY b.bookingID DESC";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setBoolean(2, true);
            rs = preStm.executeQuery();

            int currentBookingID = 0;
            int currentIndex = -1;
            ArrayList<BookingDetailDTO> listBookingDetail = new ArrayList<>();
            while (rs.next()) {
                int bookingID = rs.getInt("bookingID");
                float totalPrice = rs.getFloat("totalPrice");
                String bookingDate = rs.getTimestamp("bookingDate").toString();
                Boolean verified = rs.getBoolean("verified");
                BookingDetailDTO bookingDetail = new BookingDetailDTO();
                bookingDetail.setBookingDetailID(rs.getInt("bookingDetailID"));
                bookingDetail.setRoomID(rs.getInt("roomID"));
                bookingDetail.setPrice(rs.getFloat("price"));
                bookingDetail.setCheckInDate(rs.getTimestamp("checkInDate").toString());
                bookingDetail.setCheckOutDate(rs.getTimestamp("checkOutDate").toString());
                

                if (currentBookingID == 0 || currentBookingID != bookingID) {
                    currentBookingID = bookingID;
                    BookingDTO booking = new BookingDTO();
                    booking.setBookingID(bookingID);
                    booking.setTotalPrice(totalPrice);
                    booking.setBookingDate(bookingDate);
                    

                    listBookingDetail = new ArrayList<>();
                    listBookingDetail.add(bookingDetail);

                    booking.setBookingDetail(listBookingDetail);

                    listBooking.add(booking);
                    currentIndex += 1;
                } else if (currentBookingID != 0 && currentBookingID == bookingID) {

                    listBookingDetail.add(bookingDetail);
                    listBooking.get(currentIndex).setBookingDetail(listBookingDetail);
                }
            }
        } catch (Exception e) {
            
        } finally {
            closeConnection();
        }
        return listBooking;
    }

    public boolean deleteBookingHistory(int bookingID) throws Exception {
        boolean isSuccess = false;
        String sql = "UPDATE tblBooking SET status = ?"
                + " WHERE bookingID = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setBoolean(1, false);
            preStm.setInt(2, bookingID);
            preStm.executeUpdate();
            isSuccess = true;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return isSuccess;
    }
}

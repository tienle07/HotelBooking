/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoomDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DBUtilties;

/**
 *
 * @author TienLV
 */
public class RoomDAO implements Serializable {

    private static final String BASE_SELECT
            = "SELECT tblRoom.roomID, tblRoom.roomTypeID, tblRoomType.roomType,"
            + " tblRoomType.price, tblRoomType.description"
            + " FROM tblRoom"
            + " INNER JOIN tblRoomType ON tblRoom.roomTypeID = tblRoomType.roomTypeID";

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

    public ArrayList<RoomDTO> getByParams(Integer roomTypeID, String checkInDate, String checkOutDate) throws Exception {
        ArrayList<RoomDTO> roomList = new ArrayList<>();

        String sql = BASE_SELECT;
        sql += " WHERE tblRoom.status = ?";
        sql += " AND tblRoom.roomID NOT IN"
                + " (SELECT tblBookingDetail.roomID"
                + " FROM tblBookingDetail"
                + " WHERE tblBookingDetail.checkOutDate > ?" //checkindate
                + " AND tblBookingDetail.checkInDate < ?)"; //checkoutdate
        if (roomTypeID != 0) {
            sql += " AND tblRoom.roomTypeID = ?";
        }
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setBoolean(1, true);
            preStm.setDate(2, java.sql.Date.valueOf(checkInDate));
            preStm.setDate(3, java.sql.Date.valueOf(checkOutDate));
            if (roomTypeID != 0) {
                preStm.setInt(4, roomTypeID);
            }
            rs = preStm.executeQuery();
            while (rs.next()) {
                RoomDTO room = new RoomDTO();
                room.setRoomID(rs.getInt("roomID"));
                room.setRoomTypeID(rs.getInt("roomTypeID"));
                room.setRoomType(rs.getString("roomType"));
                room.setPrice(rs.getLong("price"));
                room.setDescription(rs.getString("description"));
                roomList.add(room);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return roomList;
    }

    public RoomDTO getByID(int roomID) throws Exception {
        RoomDTO room = new RoomDTO();
        String sql = BASE_SELECT
                + " WHERE tblRoom.status = ?"
                + " AND tblRoom.roomID = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setBoolean(1, true);
            preStm.setInt(2, roomID);
            rs = preStm.executeQuery();
            while (rs.next()) {
                room.setRoomID(rs.getInt("roomID"));
                room.setRoomTypeID(rs.getInt("roomTypeID"));
                room.setRoomType(rs.getString("roomType"));
                room.setPrice(rs.getLong("price"));
                room.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return room;
    }

    public ArrayList<RoomDTO> getAll() throws Exception {
        ArrayList<RoomDTO> roomList = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE tblRoom.status = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setBoolean(1, true);
            rs = preStm.executeQuery();

            while (rs.next()) {
                RoomDTO room = new RoomDTO();
                room.setRoomID(rs.getInt("roomID"));
                room.setRoomTypeID(rs.getInt("roomTypeID"));
                room.setRoomType(rs.getString("roomType"));
                room.setPrice(rs.getLong("price"));
                room.setDescription(rs.getString("description"));
                roomList.add(room);

            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return roomList;
    }
}

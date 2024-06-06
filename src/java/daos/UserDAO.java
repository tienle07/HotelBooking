/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtilties;
import utils.DateUtils;
import utils.SHA256;

/**
 *
 * @author TienLV
 */
public class UserDAO implements Serializable {

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

    public UserDTO getRole(UserDTO user) throws Exception {
        UserDTO userDTO = new UserDTO();
        String sql = "SELECT r.roleName, u.status, u.name FROM tblUser u"
                + " INNER JOIN tblRole r on r.roleID = u.roleID"
                + " WHERE u.status = ? AND u.email = ? AND u.password = ?";
        String encrptedPassword = SHA256.toHexString(SHA256.hash(user.getPassword()));
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setBoolean(1, Boolean.TRUE);
            preStm.setString(2, user.getEmail());
            preStm.setString(3, encrptedPassword);
            rs = preStm.executeQuery();
            if (rs.next()) {
                userDTO.setRole(rs.getString("roleName"));
                userDTO.setStatus(rs.getString("status"));
                userDTO.setName(rs.getString("name"));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return userDTO;
    }

    public boolean isExisted(String email) throws Exception {
        String sql = "SELECT email FROM tblUser WHERE email = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean registerUser(UserDTO user) throws Exception {
        boolean isSuccess = false;
        String sql = "INSERT INTO tblUser"
                + " (email, password, roleID, phone, name, address, createDate, status)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        String encrptedPassword = SHA256.toHexString(SHA256.hash(user.getPassword()));
        try {
            DateUtils dateUtils = new DateUtils();
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, user.getEmail());
            preStm.setString(2, encrptedPassword);
            preStm.setInt(3, 2);
            preStm.setString(4, user.getPhone());
            preStm.setString(5, user.getName());
            preStm.setString(6, user.getAddress());
            preStm.setTimestamp(7, java.sql.Timestamp.valueOf(dateUtils.getCurrentDate()));
            preStm.setBoolean(8, true);
            preStm.executeUpdate();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return isSuccess;
    }

    public String checkDuplicateEmail(String email) throws SQLException, Exception {
        String emailUser = "";
        String sql = "SELECT email FROM tblUser WHERE email = ?";
        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                emailUser = rs.getString("email");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return emailUser;
    }
}

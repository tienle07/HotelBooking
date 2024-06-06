/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.AreaDTO;
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
public class AreaDAO implements Serializable {

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

    public ArrayList<AreaDTO> getAllArea() throws Exception {
        ArrayList<AreaDTO> areaList = new ArrayList<>();
        String sql = "SELECT areaID, areaName FROM tblArea";

        try {
            conn = DBUtilties.makeConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            while (rs.next()) {
                AreaDTO area = new AreaDTO();
                area.setAreaID(rs.getInt("areaID"));
                area.setAreaName(rs.getString("areaName"));
                areaList.add(area);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return areaList;
    }
}

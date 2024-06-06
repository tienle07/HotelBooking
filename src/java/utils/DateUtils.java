/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author TienLV
 */
public class DateUtils {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int dayDiff(String txtCheckInDate, String txtCheckOutDate) {
        int diffDays = -1;
        try {
            Date checkInDate = sdff.parse(txtCheckInDate);
            Date checkOutDate = sdff.parse(txtCheckOutDate);
            long diff = checkOutDate.getTime() - checkInDate.getTime();
            diffDays = (int) (diff / (1000 * 60 * 60 * 24));

        } catch (Exception e) {
        }
        return diffDays;
    }

    public String updateDate(String txtCheckInDate, int dateOfStay) {
        LocalDate date = LocalDate.parse(txtCheckInDate).plusDays(dateOfStay);
        return dtf.format(date);
    }

    public String getCurrentDate() {
        Date date = new Date();
        return sdf.format(date);
    }
}

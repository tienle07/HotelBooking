/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.BookingDAO;
import define.Define;
import dtos.BookingDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.DateUtils;

/**
 *
 * @author TienLV
 */
public class BookingHistoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Define.BOOKING_HISTORY_PAGE;
        HttpSession session = request.getSession();
        try {
            String email = (String) session.getAttribute("EMAIL");
            String fromDate = request.getParameter("txtFromDate");
            String toDate = request.getParameter("txtToDate");
            String txtBookingName = request.getParameter("txtBookingName");

            if (fromDate == null || fromDate.isEmpty()) {
                fromDate = "";
            }
            if (toDate == null || toDate.isEmpty()) {
                toDate = "";
            }
            if (txtBookingName == null || txtBookingName.isEmpty()) {
                txtBookingName = "";
            }
            DateUtils date = new DateUtils();
            int dayDiffs = date.dayDiff(fromDate, toDate);
            // date && name == null
            if ((fromDate.equals("") && toDate.equals("")) && txtBookingName.equals("")) {
                BookingDAO bookingDAO = new BookingDAO();
                ArrayList<BookingDTO> listBooking = bookingDAO.getBookingHistory(email, "", "", "");
                request.setAttribute("LIST_BOOKING", listBooking);
                // date || name # null
            } else if (((!fromDate.isEmpty() && !toDate.isEmpty())) || !txtBookingName.isEmpty()) {
                // name == null
                if (txtBookingName.equals("")) {
                    // date > 0
                    if (dayDiffs > 0) {
                        BookingDAO bookingDAO = new BookingDAO();
                        ArrayList<BookingDTO> listBooking = bookingDAO.getBookingHistory(email, fromDate, toDate, txtBookingName);
                        request.setAttribute("LIST_BOOKING", listBooking);
                    } else {
                        request.setAttribute("DATE_HISTORY_MSG", "Invalid from date is bigger than to date");
                    }
                    // name != null
                } else {
                    BookingDAO bookingDAO = new BookingDAO();
                    ArrayList<BookingDTO> listBooking = bookingDAO.getBookingHistory(email, fromDate, toDate, txtBookingName);
                    request.setAttribute("LIST_BOOKING", listBooking);
                }
                // date && name # null
            } else if ((!fromDate.equals("") && !toDate.equals("")) && !txtBookingName.equals("")) {
                BookingDAO bookingDAO = new BookingDAO();
                ArrayList<BookingDTO> listBooking = bookingDAO.getBookingHistory(email, fromDate, toDate, txtBookingName);
                request.setAttribute("LIST_BOOKING", listBooking);
            }

            request.setAttribute("txtFromDate", fromDate);
            request.setAttribute("txtToDate", toDate);
            request.setAttribute("txtBookingName", txtBookingName);
        } catch (Exception e) {
            log("Error At Booking History Controller" + e.getLocalizedMessage());
            request.setAttribute("error", e.getLocalizedMessage());
            url = Define.BOOKING_HISTORY_PAGE;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

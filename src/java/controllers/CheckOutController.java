/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.BookingDAO;
import daos.BookingDetailDAO;
import define.Define;
import dtos.CartItemDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.DateUtils;
import utils.SendEmailUtils;

/**
 *
 * @author TienLV
 */
public class CheckOutController extends HttpServlet {

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
        String url = Define.INDEX_PAGE;
        HttpSession session = request.getSession();
        try {
            String txtTotalPrice = request.getParameter("txtTotalPrice");
            String email = (String) session.getAttribute("EMAIL");
            ArrayList<CartItemDTO> cart = (ArrayList<CartItemDTO>) session.getAttribute("CART");
            BookingDAO bookingDAO = new BookingDAO();

            Random random = new Random();
            int number_random = random.nextInt(10000);

            int insertedBookingID = bookingDAO.insertBooking(email, Float.parseFloat(txtTotalPrice));
            if (insertedBookingID != 0) {
                BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                for (CartItemDTO item : cart) {
                    bookingDetailDAO.insert(insertedBookingID, item);
                }
                request.setAttribute("BOOKING_MESSAGE", "Your booking has been accepted");
                session.removeAttribute("CART");         
            }
        } catch (Exception e) {
            log("Error At CheckOut Controller" + e.getLocalizedMessage());
            request.setAttribute("error", e.getLocalizedMessage());
            url = Define.ERROR_PAGE;
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

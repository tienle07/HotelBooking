/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import define.Define;
import dtos.CartItemDTO;
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
public class UpdateFromCartController extends HttpServlet {

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
        String url = Define.CART_PAGE;
        HttpSession session = request.getSession();
        DateUtils dateUtils = new DateUtils();
        try {
            Integer roomID = Integer.parseInt(request.getParameter("txtRoomID"));
            Integer dayOfStay = Integer.parseInt(request.getParameter("txtDayOfStay"));
            ArrayList<CartItemDTO> cart = (ArrayList<CartItemDTO>) session.getAttribute("CART");
            String txtCheckInDate = (String) session.getAttribute("CHECK_IN_DATE");

            if (dayOfStay <= 30 && dayOfStay > 0) {
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getRoomID() == roomID) {
                        String txtConvertCheckOut = dateUtils.updateDate(txtCheckInDate, dayOfStay);
                        int dayDiffs = dateUtils.dayDiff(txtCheckInDate, txtConvertCheckOut);
                        cart.get(i).setCheckOutDate(txtConvertCheckOut);
                        cart.get(i).setDateOfStay(String.valueOf(dayDiffs));
                        cart.get(i).setPrice(cart.get(i).getOneDayPrice() * dayDiffs);
                        break;
                    }
                }
            } else {
                request.setAttribute("UPDATE_CART", "Booking " + roomID + " room is not bigger than 30 days.");
            }
            session.setAttribute("CART", cart);
        } catch (Exception e) {
            request.setAttribute("error", e.getLocalizedMessage());
            log("Error At Update Cart Controller " + e.getLocalizedMessage());
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

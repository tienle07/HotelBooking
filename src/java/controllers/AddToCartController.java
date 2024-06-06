/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.RoomDAO;
import define.Define;
import dtos.CartItemDTO;
import dtos.RoomDTO;
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
public class AddToCartController extends HttpServlet {

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
        String url = Define.SEARCH_ROOM_CONTROLLER;
        HttpSession session = request.getSession();
        DateUtils dateUtils = new DateUtils();
        try {
            Integer roomID = Integer.parseInt(request.getParameter("txtRoomID"));
            ArrayList<CartItemDTO> cart = (ArrayList<CartItemDTO>) session.getAttribute("CART");
            String msg = "";
            if (cart != null) {
                boolean isExisted = false;
                for (CartItemDTO room : cart) {
                    if (room.getRoomID() == roomID) {
                        msg = "This room " + roomID + " is already in your cart";
                        isExisted = true;
                        break;
                    }
                }
                if (!isExisted) {
                    String txtCheckInDate = (String) session.getAttribute("CHECK_IN_DATE");
                    String txtCheckOutDate = (String) session.getAttribute("CHECK_OUT_DATE");

                    RoomDAO roomDAO = new RoomDAO();
                    RoomDTO room = roomDAO.getByID(roomID);
                    CartItemDTO cartItem = new CartItemDTO();
                    cartItem.setRoomID(roomID);
                    cartItem.setOneDayPrice(room.getPrice());
                    cartItem.setRoomType(room.getRoomType());
                    cartItem.setCheckInDate(txtCheckInDate);
                    cartItem.setCheckOutDate(txtCheckOutDate);
                    int dayDiffs = dateUtils.dayDiff(txtCheckInDate, txtCheckOutDate);
                    cartItem.setDateOfStay(String.valueOf(dayDiffs));
                    cartItem.setPrice(cartItem.getOneDayPrice() * dayDiffs);
                    cart.add(cartItem);
                    msg = "This room " + roomID + " is added to your cart";
                }
            } else {
                cart = new ArrayList<>();
                String txtCheckInDate = (String) session.getAttribute("CHECK_IN_DATE");
                String txtCheckOutDate = (String) session.getAttribute("CHECK_OUT_DATE");

                RoomDAO roomDAO = new RoomDAO();
                RoomDTO room = roomDAO.getByID(roomID);
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setRoomID(roomID);
                cartItem.setOneDayPrice(room.getPrice());
                cartItem.setRoomType(room.getRoomType());
                cartItem.setCheckInDate(txtCheckInDate);
                cartItem.setCheckOutDate(txtCheckOutDate);
                int dayDiffs = dateUtils.dayDiff(txtCheckInDate, txtCheckOutDate);
                cartItem.setDateOfStay(String.valueOf(dayDiffs));
                cartItem.setPrice(cartItem.getOneDayPrice() * dayDiffs);
                cart.add(cartItem);
                msg = "This room " + roomID + " is added to your cart";
            }
            request.setAttribute("ADD_TO_CART_MSG", msg);
            session.setAttribute("CART", cart);
        } catch (Exception e) {
            log("Error At Add To Cart Controller " + e.getLocalizedMessage());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.AreaDAO;
import daos.RoomTypeDAO;
import define.Define;
import dtos.AreaDTO;
import dtos.RoomTypeDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TienLV
 */
public class MainController extends HttpServlet {

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
        try {
            String action = request.getParameter("btnAction");
            if (action != null && action != "") {
                if (action.equals("Login")) {
                    url = Define.LOGIN_CONTROLLER;
                } else if (action.equals("Logout")) {
                    url = Define.LOGOUT_CONTROLLER;
                } else if (action.equals("Search Room")) {
                    url = Define.SEARCH_ROOM_CONTROLLER;
                } else if (action.equals("Add To Cart")) {
                    url = Define.ADD_TO_CART_CONTROLLER;
                } else if (action.equals("Remove From Cart")) {
                    url = Define.REMOVE_CART_CONTROLLER;
                } else if (action.equals("Update Cart")) {
                    url = Define.UPDATE_CART_CONTROLLER;
                } else if (action.equalsIgnoreCase("Verify Booking")) {
                    url = Define.VERIFY_BOOKING_CONTROLLER;
                } else if (action.equals("Check Out")) {
                    url = Define.CHECK_OUT_CONTROLLER;
                } else if (action.equals("Register")) {
                    url = Define.REGISTER_USER_CONTROLLER;
                } else if (action.equals("Booking History")) {
                    url = Define.BOOKING_HISTORY_CONTROLLER;              
                } else if (action.equals("Delete Booking")) {
                    url = Define.DELETE_BOOKING_CONTROLLER;
                }
            }
            if (url.equalsIgnoreCase(Define.INDEX_PAGE)) {
                AreaDAO areaDAO = new AreaDAO();
                ArrayList<AreaDTO> listArear = areaDAO.getAllArea();
                RoomTypeDAO categoryDAO = new RoomTypeDAO();
                ArrayList<RoomTypeDTO> listCategory = categoryDAO.getAll();
                request.setAttribute("ROOM_TYPE_LIST", listCategory);
                request.setAttribute("AREA_LIST", listArear);
            }
        } catch (Exception e) {
            log("Error at MainController " + e.getLocalizedMessage());
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

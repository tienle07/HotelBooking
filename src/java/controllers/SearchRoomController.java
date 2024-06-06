/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.AreaDAO;
import daos.RoomDAO;
import daos.RoomTypeDAO;
import define.Define;
import dtos.AreaDTO;
import dtos.RoomDTO;
import dtos.RoomTypeDTO;
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
public class SearchRoomController extends HttpServlet {

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
        RoomDAO roomDAO = new RoomDAO();
        try {
            String ssCheckInDate = (String) session.getAttribute("CHECK_IN_DATE");
            String ssCheckOutDate = (String) session.getAttribute("CHECK_OUT_DATE");
            String txtCheckInDate = request.getParameter("txtCheckInDate");
            String txtCheckOutDate = request.getParameter("txtCheckOutDate");
            DateUtils date = new DateUtils();

            Integer selectedRoomType = 0;
            if (request.getParameter("selectRoomType") != null) {
                selectedRoomType = Integer.parseInt(request.getParameter("selectRoomType"));
            }
            String selectedArea = "";
            if (request.getParameter("selectArea") != null) {
                selectedArea = new String(request.getParameter("selectArea").getBytes("iso-8859-1"), "UTF-8");
            }
            int dayDiffs = date.dayDiff(txtCheckInDate, txtCheckOutDate);
            if (txtCheckInDate != null && txtCheckOutDate != null) {
                if (dayDiffs > 0) {
                    ArrayList<RoomDTO> listRoom = roomDAO.getByParams(selectedRoomType, txtCheckInDate, txtCheckOutDate);
                    session.setAttribute("CHECK_IN_DATE", txtCheckInDate);
                    session.setAttribute("CHECK_OUT_DATE", txtCheckOutDate);
                    request.setAttribute("LIST_ROOM", listRoom);
                } else {
                    request.setAttribute("CONFIMR_DATE_MSG", "Invalid checkout date is bigger than check-in date");
                }
            } else if (ssCheckInDate != null && ssCheckOutDate != null && !ssCheckInDate.isEmpty() && !ssCheckOutDate.isEmpty()) {
                ArrayList<RoomDTO> listRoom = roomDAO.getByParams(0, ssCheckInDate, ssCheckOutDate);
                request.setAttribute("LIST_ROOM", listRoom);
            }

            AreaDAO areaDAO = new AreaDAO();
            ArrayList<AreaDTO> listArear = areaDAO.getAllArea();
            RoomTypeDAO categoryDAO = new RoomTypeDAO();
            ArrayList<RoomTypeDTO> listCategory = categoryDAO.getAll();
            request.setAttribute("ROOM_TYPE_LIST", listCategory);
            request.setAttribute("AREA_LIST", listArear);
            request.setAttribute("sledRoomType", selectedRoomType);
            request.setAttribute("sledArea", selectedArea);
        } catch (Exception e) {
            log("Error At Search Room Controller " + e.getLocalizedMessage());
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

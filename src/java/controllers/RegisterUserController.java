/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.UserDAO;
import define.Define;
import dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TienLV
 */
public class RegisterUserController extends HttpServlet {

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
        String url = Define.REGISTER_PAGE;
        try {
            String email = request.getParameter("inpEmail");
            String password = request.getParameter("inpPassword");
            String name = new String(request.getParameter("inpName").getBytes("iso-8859-1"), "UTF-8");
            String address = new String(request.getParameter("inpAddress").getBytes("iso-8859-1"), "UTF-8");
            String phone = request.getParameter("inpPhone");

            UserDTO user = new UserDTO(email, password, phone, name, address);
            UserDAO userDAO = new UserDAO();

            String emailUser = userDAO.checkDuplicateEmail(email);
            if (!emailUser.equals("")) {
                url = Define.REGISTER_PAGE;
                request.setAttribute("inpAddress", address);
                request.setAttribute("inpName", name);
                request.setAttribute("inpPhone", phone);
                request.setAttribute("inpEmail", email);
                request.setAttribute("REGISTER_MSG", emailUser + " this email address has registered!");
            } else {
                boolean isSuccess = userDAO.registerUser(user);
                if (isSuccess) {
                    url = Define.LOGIN_PAGE;
                    request.setAttribute("REGISTER_MSG", "Register successful");
                } else {
                    url = Define.REGISTER_PAGE;
                    request.setAttribute("REGISTER_MSG", "Your email has been registered");
                }
            }
        } catch (Exception e) {
            url = Define.ERROR_PAGE;
            log("Error at Register Controller " + e.getLocalizedMessage());
            request.setAttribute("error", e.getLocalizedMessage());
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

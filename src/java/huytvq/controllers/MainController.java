/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Account_Info.Account_InfoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String HOME_PAGE_CONTROLLER = "HomePageController";
    private final String LOGIN_CONTROLLER = "LoginController";
    private final String LOGOUT_CONTROLLER = "LogoutController";
    private final String VERIFY_EMAIL_CONTROLLER = "SendVerifyEmailController";
    private final String REGISTER_CONTROLLER = "RegisterController";
    private final String SEARCH_CONTROLLER = "SearchController";
    private final String BOOKING_CONTROLLER = "BookingController";
    private final String REMOVE_CAR_FROM_CART_CONTROLLER = "RemoveCarFromCartController";
    private final String UPDATE_CART_CONTROLLER = "UpdateCartController";
    private final String CHECK_OUT_CONTROLLER = "CheckOutController";
    private final String CHECK_DISCOUNT_CONTROLLER = "CheckDiscountController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("btnAction");
        String url = LOGIN_PAGE;

        try {

            Account_InfoDTO user = (Account_InfoDTO) request.getSession().getAttribute("USER");
            
            if (user == null) {
                if (action == null) {
                    url = HOME_PAGE_CONTROLLER;
                } else if (action.equals("LoginPage")) {
                    url = LOGIN_PAGE;
                } else if (action.equals("Login")) {
                    url = LOGIN_CONTROLLER;
                } else if (action.equals("Verify")) {
                    url = VERIFY_EMAIL_CONTROLLER;
                } else if (action.equals("Register")) {
                    url = REGISTER_CONTROLLER;
                } else if (action.equals("Search")) {
                    url = SEARCH_CONTROLLER;
                }
            } else {
                if (action == null) {
                    url = HOME_PAGE_CONTROLLER;
                }  else if (action.equals("Search")) {
                    url = SEARCH_CONTROLLER;
                } else if (action.equals("Logout")) {
                    url = LOGOUT_CONTROLLER;
                } else if (action.equals("Booking")) {
                    url = BOOKING_CONTROLLER;
                } else if (action.equals("Remove")) {
                    url = REMOVE_CAR_FROM_CART_CONTROLLER;
                } else if (action.equals("Update")) {
                    url = UPDATE_CART_CONTROLLER;
                } else if (action.equals("CheckOut")) {
                    url = CHECK_OUT_CONTROLLER;
                } else if (action.equals("CheckDiscount")) {
                    url = CHECK_DISCOUNT_CONTROLLER;
                }
            }

        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
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

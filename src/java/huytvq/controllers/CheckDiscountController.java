/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.cart.CarCart;
import huytvq.tbl_Account_Info.Account_InfoDTO;
import huytvq.tbl_DiscountDetail.DiscountDetailDAO;
import huytvq.utils.SendDiscountMailUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CheckDiscountController", urlPatterns = {"/CheckDiscountController"})
public class CheckDiscountController extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String error = "";
        String url = VIEW_CART_PAGE;
        HttpSession session = request.getSession();
//        String discountCode = (String) session.getAttribute("DISCOUNT_CODE");
        CarCart cart = (CarCart) session.getAttribute("CART");
        Account_InfoDTO user = (Account_InfoDTO) session.getAttribute("USER");
        String code = request.getParameter("txtDiscountCode");

        try {
            DiscountDetailDAO discountDAO = new DiscountDetailDAO();
            String discountCode = discountDAO.getCodeDiscount(user.getUserID());
            if (code.equals(discountCode)) {
                String discountID = discountDAO.getDiscountID(code);
                session.setAttribute("DISCOUNT_ID", discountID);
                int price = cart.getTotal();
                boolean checkDate = discountDAO.checkDiscountAvailableByDate(code);
                if (checkDate) {
                    boolean checkStatus = discountDAO.checkDiscountAvailableByStatus(code);
                    if (checkStatus) {
                        int num = discountDAO.getDiscountPrice(user.getUserID(), discountID);
                        int discountPrice = (price * num) / 100;
                        int total = price - discountPrice;
                        request.setAttribute("DISCOUNT_PRICE", discountPrice);
                        request.setAttribute("PRICE_AFTER_DISCOUNT", total);
                    } else {
                        error = "Code have been used!!";
                        request.setAttribute("ERROR_DISCOUNT", error);
                    }
                } else {
                    error = "Code have been expired!!";
                    request.setAttribute("ERROR_DISCOUNT", error);
                }
            } else {
                error = "Code is incorrect!";
                request.setAttribute("ERROR_DISCOUNT", error);
            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
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

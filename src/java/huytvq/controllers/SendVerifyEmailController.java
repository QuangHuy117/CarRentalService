/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Account_Info.Account_InfoDAO;
import huytvq.tbl_Account_Info.Account_InfoDTO;
import huytvq.tbl_DiscountDetail.DiscountDetailDAO;
import huytvq.tbl_DiscountDetail.DiscountDetailDTO;
import huytvq.utils.SendDiscountMailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
@WebServlet(name = "SendVerifyEmailController", urlPatterns = {"/SendVerifyEmailController"})
public class SendVerifyEmailController extends HttpServlet {

    private final String HOME_PAGE_CONTROLLER = "HomePageController";
    private final String VERIFY_EMAIL_PAGE = "confirmEmail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = VERIFY_EMAIL_PAGE;
        String errorString = null;
        boolean sendCode;

        DiscountDetailDAO discountDAO = new DiscountDetailDAO();
        DiscountDetailDTO discountDTO = new DiscountDetailDTO();
        SendDiscountMailUtil sm = new SendDiscountMailUtil();

        String verifyCode = request.getParameter("txtVerifyCode");

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("VERIFY_CODE");
        String userID = (String) session.getAttribute("EMAIL");

        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 10);  // number of days to add      
        String date = (String) (formattedDate.format(c.getTime()));

        try {
            Date exDate = formattedDate.parse(date);
            if (verifyCode.equals(code)) {
                Account_InfoDAO dao = new Account_InfoDAO();
                int check = dao.verifyAccount(userID);
                if (check > 0) {
                    Account_InfoDTO user = dao.getUserInfo(userID);
                    if (user != null) {
                        sendCode = true;
                        session.setAttribute("USER", user);
                        url = HOME_PAGE_CONTROLLER;
                        request.getRequestDispatcher(url).forward(request, response);
//                        response.sendRedirect(HOME_PAGE_CONTROLLER);
                        if (sendCode) {
                            String codeDiscount = sm.getRandomCodeDiscount();
                            discountDTO.setDiscountID("DC-1");
                            discountDTO.setUserID(userID);
                            discountDTO.setCode(codeDiscount);
                            discountDTO.setExpiredDate(exDate);
                            discountDTO.setStatusID(2);
                            boolean checkDiscount = discountDAO.createDiscountDetail(discountDTO);
                            if (checkDiscount) {
                                 sm.SendDiscountMail(userID, codeDiscount);
//                                if (test) {
//                                    session.setAttribute("DISCOUNT_CODE", codeDiscount);
//                                }
                            }
                        }
                    }
                }
            } else {
                errorString = "Incorrect Verification Code";
                request.setAttribute("errorString", errorString);
            }

        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
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

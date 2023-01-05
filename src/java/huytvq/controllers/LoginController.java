/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Account.AccountDAO;
import huytvq.tbl_Account_Info.Account_InfoDAO;
import huytvq.tbl_Account_Info.Account_InfoDTO;
import huytvq.utils.MyConstants;
import huytvq.utils.SendMailUtil;
import huytvq.utils.VerifyRecaptchaUtil;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private final String HOME_PAGE_CONTROLLER = "HomePageController";
    private final String LOGIN_PAGE = "login.jsp";
    private final String VERIFY_EMAIL_PAGE = "confirmEmail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userID = request.getParameter("txtUserID").trim();
        String password = request.getParameter("txtPassword").trim();
        String url = LOGIN_PAGE;
        boolean sendCode;
        Account_InfoDTO dto = null;
        try {
            boolean valid = true;
            String errorString = null;

            String recaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyRecaptchaUtil.verify(recaptchaResponse);

            if (!valid) {
                errorString = "Captcha invalid!";
                request.setAttribute("errorString", errorString);
            }
            if (valid) {
                AccountDAO dao = new AccountDAO();
                boolean check = dao.checkLogin(userID, password);
                if (check) {
                    Account_InfoDAO daos = new Account_InfoDAO();
                    dto = daos.getUserInfo(userID);
                    if (dto != null) {
                        if (dto.getStatusID() == MyConstants.STATUS_ACTIVE) {
                            HttpSession session = request.getSession();
                            session.setAttribute("USER", dto);
                            response.sendRedirect(HOME_PAGE_CONTROLLER);
                            return;
                        }
                        if (dto.getStatusID() == MyConstants.STATUS_NEW) {
                            sendCode = true;
                            HttpSession session = request.getSession();
                            session.setAttribute("EMAIL", dto.getUserID());
                            url = VERIFY_EMAIL_PAGE;
                            request.getRequestDispatcher(url).forward(request, response);
                            if (sendCode) {
                                SendMailUtil sm = new SendMailUtil();
                                String code = sm.getRandomCode();
                                boolean test = sm.sendMail(userID, dto.getName(), Integer.parseInt(code));
                                if(test) {
                                    session.setAttribute("VERIFY_CODE", code);
                                }
                            }
                            return;
                        }
                    }
                } else {
                    errorString = "Email or Password invalid!";
                    request.setAttribute("errorString", errorString);
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
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

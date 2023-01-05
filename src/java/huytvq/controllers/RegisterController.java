/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Account.AccountDAO;
import huytvq.tbl_Account.AccountRegisterError;
import huytvq.tbl_Account_Info.Account_InfoDAO;
import huytvq.tbl_Account_Info.Account_InfoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String REGISTER_PAGE = "register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = REGISTER_PAGE;
        String userID = request.getParameter("txtUserID");
        String pass = request.getParameter("txtPassword");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        AccountRegisterError error = new AccountRegisterError();

        Account_InfoDTO dto = new Account_InfoDTO();

        Account_InfoDAO infoDao = new Account_InfoDAO();
        AccountDAO accDao = new AccountDAO();

        try {
            boolean check = accDao.createAccount(userID, pass);
            if (check) {
                dto.setUserID(userID);
                dto.setPhone(phone);
                dto.setName(name);
                dto.setAddress(address);
                dto.setCreate_date(new Date());
                dto.setRoleID(2);
                dto.setStatusID(1);
                check = infoDao.createAccount_Info(dto);
                if (check) {
                    url = LOGIN_PAGE;
                }
            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            String errorMsg = e.getMessage();
            log("Error SQLException at " + this.getClass().getName() + ": " + errorMsg);
            if (errorMsg.contains("duplicate")) {
                error.setEmailErr(userID + " is Existed!!!");
                request.setAttribute("errorString", error);
            }
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

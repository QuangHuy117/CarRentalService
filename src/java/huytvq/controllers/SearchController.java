/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Account.AccountDAO;
import huytvq.tbl_Car.CarDAO;
import huytvq.tbl_Car.CarDTO;
import huytvq.tbl_Car_Category.Car_CategoryDAO;
import huytvq.tbl_Car_Category.Car_CategoryDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private final String HOME_PAGE = "homePage.jsp";
    private final String ERROR_PAGE = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;
        String dropSearch = request.getParameter("dropSearch").trim();
        String cateID = request.getParameter("dropCate").trim();
        String textName = request.getParameter("txtName").trim();
        String amount = request.getParameter("txtAmount").trim();
        String rentalDate = request.getParameter("txtRentalDate").trim();
        String returnDate = request.getParameter("txtReturnDate").trim();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int amounts;

        CarDAO dao = new CarDAO();
        try {
            Date rentDate = format.parse(rentalDate);
            Date retuDate = format.parse(returnDate);

            Car_CategoryDAO cateDao = new Car_CategoryDAO();
            List<Car_CategoryDTO> cateList = cateDao.getListCategory();
            request.setAttribute("LIST_CATEGORY", cateList);
            if (dropSearch.equals("Name")) {
                if (amount == null || amount.isEmpty()) {
                    amounts = 0;
                } else {
                    amounts = Integer.parseInt(amount);
                }
                List<CarDTO> listSearch = dao.searchCarByCarNameOrCategoryWithAmount(textName, "", amounts, rentDate, retuDate);
                request.setAttribute("LIST_CAR_SEARCH", listSearch);
                url = HOME_PAGE;
            } else {
                if (amount == null || amount.isEmpty()) {
                    amounts = 0;
                } else {
                    amounts = Integer.parseInt(amount);
                }
                List<CarDTO> listSearch = dao.searchCarByCarNameOrCategoryWithAmount("", cateID, amounts, rentDate, retuDate);
                request.setAttribute("LIST_CAR_SEARCH", listSearch);
                url = HOME_PAGE;
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

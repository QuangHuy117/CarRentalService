/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.tbl_Car.CarDAO;
import huytvq.tbl_Car.CarDTO;
import huytvq.tbl_Car_Category.Car_CategoryDAO;
import huytvq.tbl_Car_Category.Car_CategoryDTO;
import huytvq.utils.MyConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "HomePageController", urlPatterns = {"/HomePageController"})
public class HomePageController extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HOME_PAGE = "homePage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;

        String curPageString = request.getParameter("txtCurPage");
        int curPage;
        if (curPageString == null || curPageString.isEmpty()) {
            curPage = 1;
        } else {
            curPage = Integer.parseInt(curPageString);
        }
        try {
            ServletContext context = getServletContext();
            Car_CategoryDAO cateDao = new Car_CategoryDAO();
            List<Car_CategoryDTO> cateList = cateDao.getListCategory();
            context.setAttribute("LIST_CATEGORY", cateList);
            
            CarDAO carDao = new CarDAO();
            int count = carDao.getCountCar();
            int pageSize = MyConstants.TOTAL_ITEM_IN_PAGE;
            int endPage = 0;
            endPage = count / pageSize;
            if (count % pageSize != 0) {
                endPage++;
            }
            List<CarDTO> list = carDao.getListCarDefault(curPage);
            context.setAttribute("END", endPage);
            context.setAttribute("LIST_CAR_DEFAULT", list);
            url = HOME_PAGE;

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

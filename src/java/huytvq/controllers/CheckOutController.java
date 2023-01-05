/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.controllers;

import huytvq.cart.CarCart;
import huytvq.tbl_Account_Info.Account_InfoDTO;
import huytvq.tbl_Car.CarDAO;
import huytvq.tbl_Car.CarDTO;
import huytvq.tbl_DiscountDetail.DiscountDetailDAO;
import huytvq.tbl_Order.OrderDAO;
import huytvq.tbl_Order.OrderDTO;
import huytvq.tbl_Order_Detail.Order_DetailDAO;
import huytvq.tbl_Order_Detail.Order_DetailDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private final String HOME_PAGE_CONTROLLER = "HomePageController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "";
        String error = "";
        boolean checkOrder = false;

        HttpSession session = request.getSession();
        CarCart cart = (CarCart) session.getAttribute("CART");
        Account_InfoDTO user = (Account_InfoDTO) session.getAttribute("USER");
        String discountID = (String) session.getAttribute("DISCOUNT_ID");
        String rentalDate = request.getParameter("txtRentalDate");
        String returnDate = request.getParameter("txtReturnDate");
        String discountPrice = request.getParameter("txtDiscount");
        String payable = request.getParameter("txtPayable");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        CarDAO carDao = new CarDAO();

        try {
            Date rentDate = format.parse(rentalDate);
            Date retuDate = format.parse(returnDate);

            for (CarDTO dto : cart.getCart().values()) {
                boolean check = carDao.checkCarIsAvailable(dto.getCarID(), dto.getQuantity());
                if (check) {
                    checkOrder = true;
                } else {
                    error += "The amount of " + dto.getCarName() + "is not enough for booking!! \n";
                }
            }
            OrderDAO orderDAO = new OrderDAO();
            String newestID = orderDAO.getNewestOrder();
            String orderID = "";
            if (newestID != null) {
                String[] tmp = newestID.trim().split("-");
                orderID = "OD-" + (Integer.parseInt(tmp[1]) + 1);
            } else {
                orderID = "OD-" + "1";
            }
            if (checkOrder) {
                Order_DetailDAO orderDetailDAO = new Order_DetailDAO();
                Order_DetailDTO orderDetailDTO = null;
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrderID(orderID);
                orderDTO.setUserID(user.getUserID());
                orderDTO.setOrder_create_date(new Date());
                orderDTO.setTotal_price(cart.getTotal());
                orderDTO.setDiscountPrice(Integer.parseInt(discountPrice));
                orderDTO.setTotalPayable(Integer.parseInt(payable));
                if (discountID == null) {
                    orderDTO.setDiscountID("DC-0");
                } else {
                    orderDTO.setDiscountID(discountID);
                }
                orderDTO.setStatus(1);
                boolean checkCreateOrder = orderDAO.createOrder(orderDTO);
                DiscountDetailDAO discountDAO = new DiscountDetailDAO();
                boolean checkDiscount = discountDAO.updateStatusDiscount(user.getUserID());
                boolean checkOrderUpdate = false;
                if (checkDiscount) {
                    if (checkCreateOrder) {
                        for (CarDTO dto : cart.getCart().values()) {
                            String orderName = "Customer " + user.getName() + " has booked a " + dto.getCarName();
                            orderDetailDTO = new Order_DetailDTO(orderID, orderName, dto.getCarID(), dto.getQuantity(), rentDate, retuDate);
                            checkOrderUpdate = orderDetailDAO.createOrderDetail(orderDetailDTO);
                        }
                        if (checkOrderUpdate) {
                            boolean checkOrderUpdateFinish = orderDAO.updateOrderStatus(orderID);
                            if (checkOrderUpdateFinish) {
                                session.removeAttribute("DISCOUNT_ID");
                                session.removeAttribute("CART");
                                url = HOME_PAGE_CONTROLLER;
                            }
                        }
                    }
                }
            } else {
                request.setAttribute("ERROR", error);
                response.sendRedirect("viewCart.jsp");
            }
            request.getRequestDispatcher(url).forward(request, response);
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

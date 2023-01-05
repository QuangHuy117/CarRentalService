<%-- 
    Document   : viewCart
    Created on : Jan 17, 2022, 10:46:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            table,
            table td {
                border: 1px solid #6699FF;
            }
            td {
                height: 40px;
                width: 120px;
                text-align: center;
                vertical-align: middle;
            }
            h4{
                display: inline;
            }
            div{
                height: 30px;
            }
            a {
                text-decoration: none;
                color: blue;
            }
            a:active{
                color: red;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty sessionScope.CART.accountName}">
            <h2>${sessionScope.CART.accountName}'s Cart</h2>
        </c:if>

        <a href="homePage.jsp">Continue shopping</a> <br/> <br/>
        <c:set var="cart" value="${sessionScope.CART.cart.values()}"/>
        <c:if test="${not empty cart}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Name</th>
                        <th>Category Name</th>
                        <th>Year</th>
                        <th>Amount</th>
                        <th>Price</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart}" var="cartDTO" varStatus="counter">
                    <form action="MainController" method="POST">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${cartDTO.carName}
                            </td>
                            <td>
                                ${cartDTO.categoryName}
                            </td>
                            <td>
                                ${cartDTO.year}
                            </td>
                            <td>
                                <input type="hidden" name="txtCarID" value="${cartDTO.carID}" />
                                <input type="number" name="txtQuantity" value="${cartDTO.quantity}" min="1" max="${cartDTO.maxCar}" />
                            </td>
                            <td>
                                ${cartDTO.price}
                            </td>
                            <td>
                                <input style="background-color: #66FF33" type="submit" value="Update" name="btnAction" />
                            </td>
                            <td>
                                <input style="background-color: #FF6A6A" type="submit" value="Remove" name="btnAction" onclick="return confirmAction()"/>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
                <form action="MainController" method="POST" >
                    <tr>
                        <td colspan="8">
                            <label>Discount Code: </label> 
                            <input style="margin-top: 10px; margin-bottom: 10px" type="text" name="txtDiscountCode"> <br/>
                            <c:if test="${not empty requestScope.ERROR_DISCOUNT}">
                                <h3 style="color: red">${requestScope.ERROR_DISCOUNT}</h3>
                            </c:if>
                            <input style="margin-bottom: 5px" type="submit" value="CheckDiscount" name="btnAction" />
                        </td>
                    </tr>
                </form> 
            </tbody>
        </table>
    </c:if>
    <form action="MainController" method="POST" onsubmit="return checkOutError()">
        <c:if test="${not empty requestScope.ERROR}">
            <h3 style="color: red">${requestScope.ERROR}</h3>
        </c:if>

        <label>Rental Date: </label>
        <input id="rental" type="date" name="txtRentalDate" onchange="checkReturnDate(this.value)">
        <label id="rentalErr" style="color:red"></label> <br/> <br/>

        <label>Return Date: </label>
        <input id="return" type="date" name="txtReturnDate">
        <label id="returnErr" style="color:red"></label> <br/> <br/>

        <c:if test="${not empty cart}">

            <h3 style="color: red; display: inline">Total Price: </h3>    
            <h3 style="color: red; display: inline">${sessionScope.CART.total} VND</h3> <br/>


            <h3 style="color: red; display: inline">Discount: </h3>
            <c:if test="${empty requestScope.DISCOUNT_PRICE}">
                <h3 style="color: red; display: inline">0 VND</h3> <br/>
                <input type="hidden" name="txtDiscount" value="0"/>
            </c:if>
            <c:if test="${not empty requestScope.DISCOUNT_PRICE}">
                <h3 style="color: red; display: inline">${requestScope.DISCOUNT_PRICE} VND</h3> <br/>
                <input type="hidden" name="txtDiscount" value="${requestScope.DISCOUNT_PRICE}"/>
            </c:if>



            <h3 style="color: red; display: inline">Total Payable: </h3>
            <c:if test="${empty requestScope.PRICE_AFTER_DISCOUNT}">
                <h3 style="color: red; display: inline">${sessionScope.CART.total} VND</h3> <br/> <br/>
                <input type="hidden" name="txtPayable" value="${sessionScope.CART.total}"/>
            </c:if>
            <c:if test="${not empty requestScope.PRICE_AFTER_DISCOUNT}">
                <h3 style="color: red; display: inline">${requestScope.PRICE_AFTER_DISCOUNT} VND</h3> <br/> <br/>
                <input type="hidden" name="txtPayable" value="${requestScope.PRICE_AFTER_DISCOUNT}"/>
            </c:if>

        </c:if>
        <input style="height: 30px; width: 90px;" type="submit" value="CheckOut" name="btnAction" />
    </form>
    <c:if test="${empty cart}">
        <h2 style="color: red">Cart is Empty !!!</h2>
    </c:if>
</body>

<script>
    var rentalDate = new Date();
    var dd = rentalDate.getDate();
    var mm = rentalDate.getMonth() + 1; //January is 0!
    var yyyy = rentalDate.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    rentalDate = yyyy + '-' + mm + '-' + dd;
    document.getElementById("rental").setAttribute("min", rentalDate);
    var returnDate = new Date();
    returnDate.setDate(returnDate.getDate() + 1);
    dd = returnDate.getDate();
    mm = returnDate.getMonth() + 1; //January is 0!
    yyyy = returnDate.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    returnDate = yyyy + '-' + mm + '-' + dd;
    document.getElementById("return").setAttribute("min", returnDate);
    function checkReturnDate(val) {
        var date = new Date(val);
        date.setDate(date.getDate() + 1);
        dd = date.getDate();
        mm = date.getMonth() + 1; //January is 0!
        yyyy = date.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }
        date = yyyy + '-' + mm + '-' + dd;
        document.getElementById("return").setAttribute("min", date);
    }

    function checkOutError() {
        var isNotErr = true;
        var rentalDate = document.getElementById('rental').value;
        if (!rentalDate) {
            isNotErr = false;
            document.getElementById("rentalErr").innerHTML = 'Rental date is invalid!';
        } else {
            document.getElementById("rentalErr").innerHTML = '';
        }
        var returnDate = document.getElementById('return').value;
        if (!returnDate) {
            isNotErr = false;
            document.getElementById("returnErr").innerHTML = 'Return date is invalid!';
        } else {
            document.getElementById("returnErr").innerHTML = '';
        }
        return isNotErr;
    }

    function confirmAction() {
        if (confirm("Do you want to remove this car ?") === true) {
            return true;
        } else {
            return false;
        }
    }


</script>
</html>

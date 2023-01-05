<%-- 
    Document   : homepage
    Created on : Jan 11, 2022, 3:42:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="./homePage.js"></script>
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

    <c:set var="User" value="${sessionScope.USER}"/>
    <!--Welcome, ${not empty User ? User.name : "User"} -->
    Welcome, 
    <c:if test="${not empty User}">
        <h4 style="color: red">${User.name}</h4>
    </c:if>

    <c:if test="${empty User}">
        User
    </c:if>
    <br/>
    <c:if test="${not empty User}">
        <a href="MainController?btnAction=Logout">Logout</a>
    </c:if>
    <c:if test="${empty User}">
        <a href="MainController?btnAction=LoginPage">Login</a>
    </c:if>
    <h1>Car Rental Service</h1>
    <form action="MainController" method="GET" onsubmit="return checkError()">
        <label>Select type to Search</label>
        <select name="dropSearch" id="dropSearch" >
            <option value="Name" selected="selected">Name</option>
            <option value="Category">Category</option>
        </select>


        <label id="labelName">Car Name: </label>
        <input type="text" name="txtName" id="textName" />

        <label id="labelCate">Car Category: </label>
        <c:set var="listCate" value="${applicationScope.LIST_CATEGORY}"/>
        <select name="dropCate" id="dropCate" >
            <option value="">Select Category</option>
            <c:forEach var="cate" items="${listCate}">
                <option value="${cate.categoryID}">${cate.categoryName}</option>
            </c:forEach>
        </select> <br/> <br/>


        <label>Rental Date:</label>
        <input id="rental" type="date" name="txtRentalDate" value="${param.txtRentalDate}" onchange="checkReturnDate(this.value)">
        <label id="rentalErr" style="color:red"></label> <br/> <br/>


        <label>Return Date:</label>
        <input id="return" type="date" name="txtReturnDate" value="${param.txtReturnDate}">
        <label id="returnErr" style="color:red"></label> <br/> <br/>

        <label>Amount: </label>
        <input type="text" name="txtAmount"/> 

        <input type="submit" name="btnAction" value="Search" /> <br/> <br/>
    </form>

    <a href="viewCart.jsp" style="font-size: 20px"> View Cart
        <c:if test="${not empty sessionScope.CART.cart.values()}">
            (${sessionScope.CART.cart.values().size()})
        </c:if>
    </a> <br/> <br/>

    <c:set var="carSearch" value="${requestScope.LIST_CAR_SEARCH}"/>
    <!--List Car Default-->
    <c:if test="${empty carSearch}">
        <div>
            <c:forEach begin="1" end="${applicationScope.END}" var="i">
                <a href="MainController?txtCurPage=${i}">${i}</a>
            </c:forEach>
        </div>

        <c:set var="car" value="${applicationScope.LIST_CAR_DEFAULT}"/>
        <c:if test="${not empty car}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Name</th>
                        <th>Year</th>
                        <th>Category Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                            <c:if test="${User.roleID ne 1}">
                            <th>Action</th>
                            </c:if>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cardto" items="${car}" varStatus="counter">
                    <form action="MainController" method="POST">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${cardto.carName}
                            </td>
                            <td>
                                ${cardto.year}
                            </td>
                            <td>
                                ${cardto.categoryName}
                            </td>
                            <td>
                                ${cardto.price}đ
                            </td>
                            <td>
                                ${cardto.quantity}
                            </td>
                            <c:if test="${User.roleID ne 1}">
                                <td>
                                    <input type="hidden" name="txtCarID" value="${cardto.carID}"/>
                                    <input type="hidden" name="txtQuantity" value="${cardto.quantity}" />
                                    <c:if test="${not empty User}">
                                        <input type="submit" value="Booking" name="btnAction"/>
                                    </c:if>
                                    <c:if test="${empty User}">
                                        <input type="submit" value="Booking" name="btnAction" onclick="return showMessageLogin()"/>
                                    </c:if>
                                </td>
                            </c:if>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</c:if>

<!--List Car Search-->
<c:if test="${not empty carSearch}">
    <table border="1">
        <thead>
            <tr>
                <th>No.</th>
                <th>Car Name</th>
                <th>Year</th>
                <th>Category Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cardto" items="${carSearch}" varStatus="counter">
            <form action="MainController" method="POST">
                <tr>
                    <td>
                        ${counter.count}
                    </td>
                    <td>
                        ${cardto.carName}
                    </td>
                    <td>
                        ${cardto.year}
                    </td>
                    <td>
                        ${cardto.categoryName}
                    </td>
                    <td>
                        ${cardto.price}đ
                    </td>
                    <td>
                        ${cardto.quantity}
                    </td>
                    <td>
                        <input type="hidden" name="txtCarID" value="${cardto.carID}"/>
                        <input type="hidden" name="txtQuantity" value="${cardto.quantity}" />
                        <input type="submit" value="Booking" name="btnAction"/>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </tbody>
</table>
</c:if>
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

    function checkError() {
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

    function showMessageLogin() {
        alert("Please login to book this car!");
    }

</script>

</html>

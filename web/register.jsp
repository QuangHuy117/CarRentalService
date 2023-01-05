<%-- 
    Document   : register
    Created on : Jan 15, 2022, 3:28:39 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="./validate.js"></script>
    </head>
    <body>
        <h2>Register Form</h2>
        <form name="RegisterForm" action="MainController" method="POST">
            <label>Email</label> 
            <input type="text" name="txtUserID" id="userID" value="${param.txtUserID}"/> <br/> <br/>

            <label>Password</label> 
            <input type="password" name="txtPassword" id="pass"/> <br/> <br/>

            <label>Confirm Password</label> 
            <input type="password" name="txtConfirmPassword" id="conPass"/> <br/> <br/>

            <label>Name</label> 
            <input type="text" name="txtName" id="name" value="${param.txtName}"/> <br/> <br/>

            <label>Phone</label> 
            <input type="text" name="txtPhone" id="phone" value="${param.txtPhone}"/> <br/> <br/>

            <label>Address</label> 
            <input type="text" name="txtAddress" id="address" value="${param.txtAddress}"/> <br/> <br/>
            
            <c:if test="${not empty requestScope.errorString}">
                <h3 style="color: red">${requestScope.errorString.emailErr}</h3>
            </c:if>
            
            <input type="submit" value="Register" name="btnAction" onclick="return validateForm()"/>
        </form>
    </body>
</html>


<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
        <script src="./validate.js"></script>
    </head>
    <body>
        <h2>Login Form</h2>
        <form action="MainController" method="POST">
            <label>Email: </label><input type="text" name="txtUserID" /><br/> <br/>
            <label>Password: </label> <input type="password" name="txtPassword" /><br/> <br/>
            <div class="g-recaptcha" data-sitekey="6LfI8vUdAAAAAPHtzRQ657p-gcF-a_3fkCaDdY9e"></div> <br/>
            <c:if test="${not empty requestScope.errorString}">
                <h3 style="color: red">${requestScope.errorString}</h3>
            </c:if>
            <input type="submit" value="Login" name="btnAction"  onclick="return validateLoginForm()"/> 
        </form>
        <br/>
        <label>Don't have an account? <a href="register.html">Register an account</a></label> <br/><br/>
    </body>
</html>

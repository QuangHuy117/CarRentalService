<%-- 
    Document   : confirmEmail
    Created on : Jan 10, 2022, 10:57:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
    </head>
    <body>
        <h1>Confirm Account</h1>
        <form action="MainController" method="POST">
            <label>Verification Code: </label> <input type="text" name="txtVerifyCode" /> <br/>
            <h3 style="color: red">${requestScope.errorString != null ? requestScope.errorString : ""}</h3> <br/>
            <input type="submit" value="Verify" name="btnAction" onclick="showMessage()"/>
        </form>

        <script>
            function showMessage() {
                    window.onload = alert("Please check your email to receive a discount!");
            }
        </script>
    </body>
</html>

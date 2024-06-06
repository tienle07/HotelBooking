<%--
    Document   : login
    Created on : Apr 18, 2022, 01:32:51 AM
    Author     : TienLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" href="style.css">
        <title>Login Page</title>
    </head>
    <body>
        <nav class="navbar navbar-dark bg-primary navbar-expand-lg">
            <div class="container">
                <c:choose>
                    <c:when test="${empty ROLE}">
                        <a class="navbar-brand" href="login.jsp">Login</a>
                    </c:when>
                    <c:when test="${not empty ROLE}">
                        <form id="logoutForm" action="MainController">
                            <input type="hidden" name="btnAction" value="Logout">
                            <a id="logoutLink" class="navbar-brand" href="#">Logout</a>
                        </form>
                    </c:when>
                </c:choose>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="SearchRoomController">Home</a>
                        </li>
                        <c:choose>
                            <c:when test="${ROLE == 'CUSTOMER'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="cart.jsp">Your cart</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="BookingHistoryController">Booking history</a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <c:if test="${not empty ROLE}">
            <c:redirect url="index.jsp"/>
        </c:if>
        <h3 class="container d-flex justify-content-center text-danger">${LOGIN_MSG}</h3>
        <h3 class="container d-flex justify-content-center">${REGISTER_MSG}</h3>


        <div class="container d-flex justify-content-center">
            <div class="wrapper w-50">
                <div class="wrapper-header">
                    <p>Login form</p>
                </div>
                <div class="wrapper-content">
                    <form method="POST" action="MainController">
                        <div class="form-group">
                            <label>EMAIL</label>
                            <input class="form-control" type="email" name="txtEmail" placeholder="Your email" required="">
                        </div>
                        <div class="form-group">
                            <label>PASSWORD</label>
                            <input class="form-control" type="password" name="txtPassword" placeholder="Your password" required="">
                        </div>
                        <div class="row">
                            <div class="col">
                                <a href="register.jsp" class="btn btn-outline-dark w-100 mt-2">REGISTER</a>
                            </div>
                            <div class="col">
                                <button type="submit" name="btnAction" value="Login" class="mt-2 btn btn-dark w-100">LOGIN</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

<%--
    Document   : register
    Created on : Apr 18, 2022, 01:04:51 AM
    Author     : TienLV
--%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" href="style.css">
        <title>Register Page</title>
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

        <h3 class="text-danger text-center">${REGISTER_MSG}</h3>
        <div class="container d-flex justify-content-center">
            <div class="wrapper w-50 mt-5">
                <div class="wrapper-header">
                    <p>REGISTER</p>
                </div>
                <div class="wrapper-content">
                    <form method="POST" action="MainController">
                        <div class="form-group">
                            <label>Email</label>
                            <input name="inpEmail" value="${inpEmail}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" type="email" required="" placeholder="Your email ex: abc@gmail.com" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Name</label>
                            <input name="inpName" value="${inpName}" type="text" required="" pattern="[^!@#$%^&*()_+0-9]{0,}" placeholder="Your name ex:Nguyen Van A" class="form-control">
                        </div>
                        <div class="form-group mt-1">
                            <label>Password</label>
                            <input id="inpRegPassword" name="inpPassword" type="password" required="" placeholder="Your password" class="form-control">
                        </div>
                        <div class="form-group mt-1">
                            <label>Re-type password</label>
                            <input id="reTypePassword" type="password" required="" placeholder="Re-type your password" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input name="inpPhone" value="${inpPhone}" type="tel" placeholder="Phone number ex:(0832434244)" pattern="[0]{1}[0-9]{9}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <input name="inpAddress" value="${inpAddress}" type="text" placeholder="Your address" class="form-control">
                        </div>
                        <div class="row mt-3">
                            <div class="col-5">
                                <a href="login.jsp" class="btn btn-outline-dark w-100">Already a member ?</a>
                            </div>
                            <div class="col-7">
                                <button name="btnAction" value="Register" type="submit" class="btn btn-dark w-100"
                                        onclick="return checkReTypePassword()">Register</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script>
        function checkReTypePassword() {
            const password = document.getElementById("inpRegPassword").value;
            const reTypePassword = document.getElementById("reTypePassword").value;
            if (password === reTypePassword) {
                return true;
            } else {
                alert('Password and Re-type password do not match!');
                return false;
            }
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</html>
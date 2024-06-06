<%--
    Document   : index
    Created on : Apr 18, 2022, 12:37:54 AM
    Author     : TienLV
--%>

<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.css">
        <link rel="stylesheet" href="style.css">
        <title>Index Page</title>
    </head>
    <body>
        <!--Navbar-->
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
                                    <a class="nav-link" href="BookingHistoryController">Lich su dat phong</a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <!--End of navbar-->
        <div class="container">
            <div>
                <c:if test="${not empty USER}">
                    <h3>Welcome to Hotel Booking - ${USER}</h3>
                </c:if>
            </div>
            <h3 class="text-center">${ADD_TO_CART_MSG}</h3>
            <h3 class="text-center">${BOOKING_MESSAGE}</h3>
            <h3 class="text-center">${VERIFY_MSG}</h3>
            <h3 class="text-center">${CONFIMR_DATE_MSG}</h3>
            <h3 class="text-center">${EMAIL_MSG}</h3>
            <h2 style="color: red">${ERROR}</h2>
            <div class="wrapper mt-2">
                <div class="wrapper-header">
                    <p>Search room</p>
                </div>
                <div class="wrapper-content">
                    <form action="MainController" method="POST">
                        <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Check-in date</label>
                                    <input type="date" name="txtCheckInDate" class="form-control" min="<%=LocalDate.now()%>" value="${CHECK_IN_DATE}" required>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Check-out date</label>
                                    <input type="date" name="txtCheckOutDate" class="form-control" min="<%=LocalDate.now().minusDays(-1)%>" value="${CHECK_OUT_DATE}" required>
                                </div>
                            </div>

                        </div>
                        <div class="row d-flex align-items-end">
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Area</label>
                                    <select name="selectArea" class="form-control">
                                        <option value="0">SELECT AREA</option>
                                        <c:forEach items="${AREA_LIST}" var="area">
                                            <option value="${area.areaID}" ${area.areaID == sledArea ? 'selected' : ''}>${area.areaName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Room type</label>
                                    <select class="form-control" name="selectRoomType">
                                        <option value="0">SELECT ROOM TYPE</option>
                                        <c:forEach items="${ROOM_TYPE_LIST}" var="roomType">
                                            <option value="${roomType.roomTypeID}" ${roomType.roomTypeID== sledRoomType ? 'selected' : ''}>${roomType.roomType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-4 d-flex justify-content-end">
                                <button type="submit" name="btnAction" value="Search Room" class="btn btn-primary w-100">Search</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="wrapper mt-3">
                <div class="wrapper-content">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Room number</th>
                                <th scope="col">Room type</th>
                                <th scope="col">Description</th>
                                <th scope="col">Price</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${LIST_ROOM}" var="room">
                                <tr>
                                    <th scope="row">${room.roomID}</th>
                                    <td>${room.roomType}</td>
                                    <td>${room.description}</td>
                                    <td>
                                        <fmt:setLocale value = "vi_VN"/>
                                        <fmt:formatNumber value = "${room.price}" type = "currency"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty ROLE}">
                                                <span>Login to booking</span>
                                            </c:when>
                                            <c:when test="${ROLE == 'CUSTOMER'}">
                                                <form action="MainController" method="POST">
                                                    <input type="hidden" name="txtRoomID" value="${room.roomID}">
                                                    <button type="submit" class="btn btn-success" name="btnAction" value="Add To Cart">Add to cart</button>
                                                </form>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script>
            document.getElementById("logoutLink").onclick = function () {
                document.getElementById("logoutForm").submit();
            };
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    </body>
</html>
<%--
    Document   : cart
    Created on : Apr 17, 2022, 11:30:56 PM
    Author     : TienLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <title>Cart</title>
    </head>
    <body>
        <c:if test="${ROLE != 'CUSTOMER'}">
            <c:redirect url="index.jsp"/>
        </c:if>
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
                                    <a class="nav-link" href="BookingHistoryController">Booking history</a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <!--End of navbar-->
        <div class="container">
            <div class="wrapper mt-4">
                <h3 class="text-center">${APPLY_DISCOUNT_MSG}</h3>
                <h3 class="text-center">${REMOVE_FROM_CART}</h3>
                <h3 class="text-center">${UPDATE_CART}</h3>
                <div class="wrapper-header">
                    <p>YOUR CART </p>
                </div>
                <div class="wrapper-content">
                    <c:choose>
                        <c:when test="${empty CART}">
                            <h3>Your cart is empty</h3>
                        </c:when>
                        <c:when test="${not empty CART}">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>ROOM</th>
                                        <th>TYPE</th>
                                        <th>Check In Date</th>
                                        <th>Check Out Date</th>
                                        <th>Date Of Stay</th>
                                        <th>Price 1 Date</th>
                                        <th>Price</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${CART}" var="room">
                                        <tr>
                                            <td>${room.roomID}</td>
                                            <td>${room.roomType}</td>
                                            <td>${room.checkInDate}</td>
                                            <td>${room.checkOutDate}</td>
                                            <td class="col">
                                                <form action="MainController" method="POST">
                                                    <div class="row w-65">
                                                        <div class="col">
                                                            <input class="form-control" value="${room.dateOfStay}" type="number" min="1" step="1" placeholder="Date of stay" name="txtDayOfStay" required="">
                                                        </div>
                                                        <div class="col">
                                                            <input type="hidden" name="txtRoomID" value="${room.roomID}">
                                                            <button type="submit" name="btnAction" value="Update Cart" class="btn btn-success">Update</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </td>
                                            <td>
                                                <fmt:setLocale value = "vi_VN"/>
                                                <fmt:formatNumber value = "${room.oneDayPrice}" type = "currency"/>
                                            </td>
                                            <td>
                                                <fmt:setLocale value = "vi_VN"/>
                                                <fmt:formatNumber value = "${room.price}" type = "currency"/>
                                            </td>
                                            <td>
                                                <button onclick="setIdForDeleteModal(${room.roomID})" class="btn btn-outline-danger w-100" type="button" data-bs-toggle="modal" data-bs-target="#deleteModal">Remove</button>
                                            </td>
                                        </tr>
                                        <c:set var="total" value="${total + room.price}" />
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
                <c:if test="${not empty CART}">
                    <div class="wrapper-content pt-0">
                        <form action="MainController" method="POST">
                                    <span>
                                        <b>Total price:
                                            <fmt:setLocale value = "vi_VN"/>
                                            <fmt:formatNumber value = "${total}" type = "currency"/>
                                        </b>
                                    </span>
                                </div>
                                <div class="col-2 d-flex align-items-end">
                                    <div>
                                        <input type="hidden" value="${total}" name="txtTotalPrice">
                                        <button style="submit" value="Check Out" name="btnAction" class="btn btn-success">Check out</button>
                                    </div>
                            </div>
                        </form>

                    </div>
                </c:if>
            </div>   
        


        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Deleting Room</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Are you want to delete this room ?
                    </div>
                    <div class="modal-footer">
                        <form action="MainController" method="POST">
                            <input type="hidden" id="deleteRoomID" name="txtRoomID">
                            <button type="submit" name="btnAction" value="Remove From Cart" class="btn btn-danger">Remove</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            document.getElementById("logoutLink").onclick = function () {
                document.getElementById("logoutForm").submit();
            };
            function setIdForDeleteModal(roomID) {
                document.getElementById("deleteRoomID").value = roomID;
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    </body>
</html>
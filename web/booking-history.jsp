<%-- 
    Document   : booking-history
    Created on : Apr 17, 2022, 11:04:51 PM
    Author     : TienLV
--%>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="crossorigin="anonymous"></script>
        <title>Booking history</title>
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
                                    <a class="nav-link" href="BookingHistoryController">Lich su dat phong</a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <!--End of navbar-->
        <div class="container mt-4">
            <div class="wrapper">
                <div class="wrapper-content">
                    <form action="MainController" method="POST">
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label>SEARCH BY NAME</label>
                                    <input type="text" value="${txtBookingName}" name="txtBookingName" class="form-control" placeholder="Hotel Name">
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-5">
                                <div class="form-group">
                                    <label>FROM DATE BOOKING</label>
                                    <input type="date" value="${txtFromDate}" name="txtFromDate" class="form-control" value="${FROM_BOOKING}">
                                </div>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <label>TO DATE BOOKING</label>
                                    <input type="date" value="${txtToDate}" name="txtToDate" class="form-control" value="${TO_BOOKING}">
                                </div>
                            </div>
                            <div class="col-2 d-flex align-items-end">
                                <button type="submit" name="btnAction" value="Booking History" class="btn btn-primary w-100">SEARCH</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div>
                <h4>${BOOKING_HISTORY_MSG}${SEARCH_BOOKING_HISTORY_MSG}</h4>
                <h3 class="text-center">${RATING_MSG}</h3>
                <h3 class="text-center">${DELETE_BOOKING_HISTORY_MSG}</h3>
                <h3 class="text-center">${DATE_HISTORY_MSG}</h3>
            </div>
            <c:if test="${LIST_BOOKING != null}">
                <c:if test="${not empty LIST_BOOKING}">
                    <div class="mt-2">
                        <h4 class="text-center">User: ${EMAIL}</h4>
                    </div>
                    <c:forEach items="${LIST_BOOKING}" var="booking">
                        <div class="wrapper-header">
                            <div class="row">
                                <div class="col-10">
                                    <p style="font-size: 1.2em">Booking At: <strong>${booking.bookingDate}</strong> </p>
                                </div>
                                <div class="col-2 d-flex justify-content-end">
                                    <div>
                                        <button style="margin-top: -5px;" onclick="openDeleteModal(${booking.bookingID})" class="btn btn-sm btn-outline-danger">Delete</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="wrapper-content">
                            <c:forEach items="${booking.bookingDetail}" var="bookingDetail">
                                <div style="border-bottom: 1px solid #ccc; padding: 1rem;">
                                    <div>
                                        <h4>Room: ${bookingDetail.roomID}</h4>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <div class="d-flex align-items-center" style="height: 100%;">
                                                <span> Price:
                                                    <fmt:setLocale value = "vi_VN"/>
                                                    <fmt:formatNumber value = "${bookingDetail.price}" type = "currency"/>
                                                </span>
                                            </div>
                                        </div>            
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <span style="color: green;">CHECK IN ON: ${bookingDetail.checkInDate}</span>
                                        </div>
                                        <div class="col">
                                            <span style="color: red;">CHECK OUT ON: ${bookingDetail.checkOutDate}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="mt-3">
                                <strong>Total price:
                                    <fmt:setLocale value = "vi_VN"/>
                                    <fmt:formatNumber value = "${booking.totalPrice}" type = "currency"/>
                                </strong>
                            </div>
                        </div>
                    </div>
                    <hr/>
                </c:forEach>
            </c:if>
            <c:if test="${empty LIST_BOOKING}">
                <h3 style="color: red">Not found Booking</h3>
            </c:if>
        </c:if>
    </div>
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">DELETE</h5>
                </div>
                <div class="modal-body">
                    Are you want to delete this booking history ?
                </div>
                <div class="modal-footer">
                    <form method="POST" action="MainController">
                        <input type="hidden" id="bookingID" name="txtBookingID"/>
                        <button type="submit" value="Delete Booking" name="btnAction" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.getElementById("logoutLink").onclick = function () {
            document.getElementById("logoutForm").submit();
        };

        $('#closeModal1').click(function () {
            $('#myModal').modal('hide');
        });

        $('#closeModal2').click(function () {
            $('#myModal').modal('hide');
        });

        function openDeleteModal(id) {
            document.getElementById("bookingID").value = id;
            $('#deleteModal').modal('show');
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>

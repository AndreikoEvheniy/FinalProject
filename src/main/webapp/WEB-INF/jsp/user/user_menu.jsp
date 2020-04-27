<%--
  Created by IntelliJ IDEA.
  User: zhenia
  Date: 14.03.2020
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Flights list" scope="page"/>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<main role="main" id="content">
    <div class="content" style="padding-left: 20px; padding-top: 5px;">
        <form action="controller">
            <input type="hidden" name="command" value="searchFlight"/>
            <input name="numberFlight" type="text" placeholder="Search by number">

            <label>
                <input name="isFrom" type="text" placeholder="From">
            </label>
            <label>
                <input name="whereTo" type="text" placeholder="Where">
            </label>
            <label>
                <input name="date" type="text" placeholder="Date">
            </label>
            <input type="submit" class="btn btn-primary" value="search"><br>
            <input type="hidden" value="searchFlight"/>
        </form>
        <table class="table">
            <thead>
            <tr>
                <td>
                    <form action="controller">
                        <input type="hidden" name="command" value="sortedListByNumber"/>
                        <input type="submit" style="background: transparent; border: 0;" value="Number">
                    </form>
                </td>
                <td>
                    <form action="controller">
                        <input type="hidden" name="command" value="sortedListByName"/>
                        <input type="submit" style="background: transparent; border: 0;" value="Name">
                    </form>
                </td>
                <td>Date</td>
                <td>From</td>
                <td>Where</td>
                <td>Status</td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${flightsList}">
                <c:set var="k" value="${k+1}"/>
                <tr>
                    <td>${item.number}</td>
                    <td>${item.name}</td>
                    <td>${item.date}</td>
                    <td>${item.isFrom}</td>
                    <td>${item.whereTo}</td>
                    <td>${item.status_id}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
<footer class="footer" style="text-align: center;">
    <div class="container">
        <span class="text-muted">Airlines (AirlineExample, EPAM-KNURE Java Training), 2020</span>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</body>
</html>




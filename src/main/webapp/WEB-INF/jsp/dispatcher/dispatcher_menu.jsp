<%--
  Created by IntelliJ IDEA.
  User: zhenia
  Date: 15.03.2020
  Time: 14:10
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
        <table class="table">
            <thead>
            <tr>
                <td>№</td>
                <td>Name</td>
                <td>Date</td>
                <td>From</td>
                <td>Where</td>
                <td>Brigade</td>
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
                    <td>${item.brigade}</td>
                    <td>${item.status_id}</td>
                    <form action="controller">
                        <input type="hidden" name="command" value="changeStatus"/>
                        <input type="hidden" name="flightNumber" value="${item.number}">
                        <td>
                            <select name="statusId">
                                <option value="1">open</option>
                                <option value="2">done</option>
                                <option value="3">canceled</option>
                            </select>
                        </td>
                        <td>
                            <input type="submit" value="Change status" class="btn btn-primary">
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>
        <table class="table">
            <thead>
            <tr>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Rank</td>
                <td>Number brigades</td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${workersList}">
                <c:set var="k" value="${k+1}"/>
                <tr>
                    <td>${item.firstName}</td>
                    <td>${item.lastName}</td>
                    <td>${item.workersRank}</td>
                    <td>${item.brigade_id}</td>
                </tr>
            </c:forEach>
        </table>
        <table class="table">
            <thead>
            <tr>
                <td>№</td>
                <td>Subject</td>
                <td>Message</td>
                <td>Status</td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${applicationList}">
                <c:set var="k" value="${k+1}"/>
                <tr>
                    <td>${item.id}</td>
                    <td>${item.subject}</td>
                    <td>${item.message}</td>
                    <td>${item.status_id}</td>
                </tr>
            </c:forEach>
        </table>
        <form id="flight_list" action="controller">
            <input type="hidden" name="command" value="brigadeFormation"/>
            <select name="flightNumber">
                <c:forEach var="flight" items="${flightsListFormationBrigade}">
                    <option value="${flight.number}">${flight.name}</option>
                </c:forEach>
            </select>
            <select name="pilotsId">
                <c:forEach var="pilots" items="${pilotsList}">
                    <option value="${pilots.id}">${pilots.lastName} ${pilots.firstName}</option>
                </c:forEach>
            </select>
            <select name="navigatorId">
                <c:forEach var="navigator" items="${navigatorList}">
                    <option value="${navigator.id}">${navigator.lastName} ${navigator.firstName}</option>
                </c:forEach>
            </select>
            <select name="operatorId">
                <c:forEach var="operator" items="${operatorList}">
                    <option value="${operator.id}">${operator.lastName} ${operator.firstName}</option>
                </c:forEach>
            </select>
            <select name="stewardessId">
                <c:forEach var="stewardess" items="${stewardessList}">
                    <option value="${stewardess.id}">${stewardess.lastName} ${stewardess.firstName}</option>
                </c:forEach>
            </select>
            <input type="submit" class="btn btn-primary" value="create"><br>
            <input type="hidden" value="brigadeFormation"/>
        </form>
        <form id="flight_list2" action="controller">
            <input type="hidden" name="command" value="createApp"/>
            <input name="subject" type="text" placeholder="Subject">
            <input name="message" type="text" placeholder="Message">
            <input type="submit" class="btn btn-primary" value="Send"><br>
        </form>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</body>
</html>
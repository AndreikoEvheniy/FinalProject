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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<c:set var="title" value="Flights list" scope="page"/>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
    <nav class="navbar navbar-dark bg-white">
        <form class="form-inline">
            <button type="button" data-toggle="modal"
                    data-target="#createFlightModal" class="btn btn-primary">Create flight
            </button>
            &nbsp;
            <button type="button" data-toggle="modal"
                    data-target="#createWorkerModal" class="btn btn-primary">Create worker
            </button>
            &nbsp;
            <button type="button" data-toggle="modal"
                    data-target="#viewAppList" class="btn btn-primary">View application
            </button>
        </form>
    </nav>
</div>
<main role="main" id="content">
    <div class="content" style="padding-left: 20px; padding-top: 5px">
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
                        <td>
                            <button type="button" id="${item.id}" class="editFlight btn btn-primary"
                                    data-id="${item.id}"
                                    data-toggle="modal"
                                    data-target="#flightModal">
                                Edit flight
                            </button>
                        </td>
                    </form>
                    <form action="controller">
                        <input type="hidden" name="command" value="removeFlight"/>
                        <input type="hidden" name="flightId" value="${item.id}">
                        <td>
                            <input type="submit" value="Remove flight" class="btn btn-primary">
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>

        <div id="flightModal" class="modal fade" tabindex="-1">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Make changes</h4>
                    </div>
                    <form class="form-horizontal" role="form" action="controller">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="numberFlightInput">Number flight</label>
                            <div class="col-sm-9">
                                <input type="text" value="" class="form-control" name="numberFlightInput"
                                       id="numberFlightInput"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="dateInput">Date</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="dateInput" value="" id="dateInput"/>
                                <div class="modal-footer">
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                    <input type="hidden" name="command" value="editFlight"/>
                                    <input type="hidden" name="flightId" id="flightId" value="">
                                    <button type="submit" id="flightSubmit" onclick="getNumbAir()"
                                            class="btn btn-primary">Save changes
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                crossorigin="anonymous"></script>

        <div id="createFlightModal" class="modal fade" tabindex="-1">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="controller">
                            <input type="hidden" name="command" value="createFlights"/>
                            <div class="form-group">
                                <input name="number" type="text" placeholder="Number">
                            </div>
                            <div class="form-group">
                                <input name="date" type="text" placeholder="Date">
                            </div>
                            <div class="form-group">
                                <input name="from" type="text" placeholder="From">
                            </div>
                            <div class="form-group">
                                <input name="where" type="text" placeholder="Where">
                            </div>
                            <input type="submit" class="btn btn-primary" value="Create"><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>

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
                    <form id="flight_list4" action="controller">
                        <td>
                            <button type="button" id="${item.id}" class="html-editorWorker btn btn-primary"
                                    data-id="${item.id}"
                                    data-toggle="modal"
                                    data-target="#workerModal">
                                Edit worker
                            </button>
                        </td>
                    </form>
                    <form id="flight_list5" action="controller">
                        <input type="hidden" name="command" value="removeWorker"/>
                        <input type="hidden" name="workerId" value="${item.id}">
                        <td>
                            <input type="submit" value="Remove worker" class="btn btn-primary">
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>

        <div id="workerModal" class="modal fade" tabindex="-1">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Make changes</h4>
                    </div>
                    <form class="form-horizontal" role="form" action="controller">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="lastNameInput">Last name</label>
                            <div class="col-sm-9">
                                <input type="text" value="" class="form-control" name="lastNameInput"
                                       id="lastNameInput"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Rank</label>
                            <div class="col-sm-9">
                                <select name="editRankId">
                                    <option value="1">pilot</option>
                                    <option value="2">navigator</option>
                                    <option value="3">operator</option>
                                    <option value="4">stewardess</option>
                                </select>
                                <div class="modal-footer">
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                    <input type="hidden" name="command" value="editWorker"/>
                                    <input type="hidden" name="workerId" id="workerId" value="">
                                    <button type="submit" id="workerSubmit" onclick="setLastNameWorker()"
                                            class="btn btn-primary">Save changes
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div id="createWorkerModal" class="modal fade" tabindex="-1">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="controller">
                            <div class="form-group">
                                <input type="hidden" name="command" value="createWorkers"/>
                            </div>
                            <div class="form-group">
                                <input name="firstName" type="text" placeholder="First Name">
                            </div>
                            <div class="form-group">
                                <input name="lastName" type="text" placeholder="Last Name">
                            </div>
                            <div class="form-group">
                                <select name="rankId">
                                    <option value="1">pilot</option>
                                    <option value="2">navigator</option>
                                    <option value="3">operator</option>
                                    <option value="4">stewardess</option>
                                </select>
                            </div>
                            <input type="submit" class="btn btn-primary" value="Create"><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="viewAppList" class="modal fade" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-body">
                        <table class="table">
                            <tr>
                                <td>№</td>
                                <td>Subject</td>
                                <td>Message</td>
                                <td>Status</td>
                            </tr>
                            <c:set var="k" value="0"/>
                            <c:forEach var="item" items="${applicationList}">
                                <c:set var="k" value="${k+1}"/>
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.subject}</td>
                                    <td>${item.message}</td>
                                    <td>${item.status_id}</td>
                                    <form id="flight_list0" action="controller">
                                        <input type="hidden" name="command" value="changeAppStatus"/>
                                        <input type="hidden" name="appId" value="${item.id}">
                                        <td>
                                            <select name="statusId">
                                                <option value="1">open</option>
                                                <option value="2">reject</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" value="Change status" class="btn btn-primary">
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</main>
<footer class="footer" style="text-align: center;">
    <div class="container">
        <span class="text-muted">Airlines (AirlineExample, EPAM-KNURE Java Training), 2020</span>
    </div>
</footer>
<script>
    $(".editFlight").on("click", (function () {
        var id = $(this).attr('data-id');
        flightId.value = id
    }));


    $(".html-editorWorker").on("click", function () {
        var idWorker = $(this).attr('data-id')
        workerId.value = idWorker
    });

    function getNumbAir() {
        $("#flightSubmit").on("click", (function () {
            var numFlight = document.getElementsByName('numberFlightInput');
            var dateFlight = document.getElementsByName('dateInput');
            numberFlightInput.value = numFlight
            dateFlightInput.value = dateFlight
        }));
    }

    function setLastNameWorker() {
        $(".workerModal").on("click", (function () {
            var lastNameWorker = document.getElementsByName('lastNameInput');
            lastNameInput.value = lastNameWorker
        }));
    }

    $("#exampleModal").on('show.bs.modal', function (e) {
        var flightId = $(e.relatedTarget).data('flight-id');
        var cols = $('#flight-' + flightId + ' td');
        var numberFlight = $(cols[0]).text();
        var date = $(cols[1]).text();
        $('#numberFlightInput').val(numberFlight);
        $('#dateInput').val(date);
    });
    $("#exampleModal").on('hidden.bs.modal', function () {
        var form = $(this).find('form');
        form[0].reset();
    });
</script>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>
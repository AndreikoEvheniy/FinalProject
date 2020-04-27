package ua.nure.andreiko.airline.web.command.dispatcherCommands;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.entity.Application;
import ua.nure.andreiko.airline.db.entity.Brigades;
import ua.nure.andreiko.airline.db.entity.Flights;
import ua.nure.andreiko.airline.db.entity.Workers;
import ua.nure.andreiko.airline.exception.AppException;
import ua.nure.andreiko.airline.exception.DBException;
import ua.nure.andreiko.airline.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Brigade formation command.
 *
 * @author E.Andreiko
 */

public class BrigadeFormationCommand extends Command {
    private static final Logger LOG = Logger.getLogger(BrigadeFormationCommand.class);
    DBManager dbManager;
    List<Workers> workersList = new ArrayList<>();
    Brigades brigade;
    int pilotsId;
    int navigatorId;
    int operatorId;
    int stewardessId;
    Flights flight;
    Workers pilot;
    Workers navigator;
    Workers operator;
    Workers stewardess;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command start");

        dbManager = DBManager.getInstance();
        String flightNumber = request.getParameter("flightNumber");
        pilotsId = Integer.parseInt(request.getParameter("pilotsId"));
        navigatorId = Integer.parseInt(request.getParameter("navigatorId"));
        operatorId = Integer.parseInt(request.getParameter("operatorId"));
        stewardessId = Integer.parseInt(request.getParameter("stewardessId"));

        flight = dbManager.getFlightIdStatus(flightNumber);
        pilot = dbManager.getWorker(pilotsId);
        navigator = dbManager.getWorker(navigatorId);
        operator = dbManager.getWorker(operatorId);
        stewardess = dbManager.getWorker(stewardessId);

        LOG.trace("Found in DB: flight, pilot, navigator, operator, stewardess --> " + flight + ", " + pilot + ", " +
                navigator + ", " + operator + ", " + stewardess);

        dbManager.addWorkersToBrigades(pilotsId, navigatorId, operatorId, stewardessId, flightNumber);

        brigade = dbManager.getIdBrigades(flightNumber);

        LOG.trace("Create and found in DB new brigade --> " + brigade);

        flight.setBrigade(brigade.getId());
        dbManager.updateFlight(flight);

        LOG.trace("Update flight " + flight);

        workWithWorkerList(pilot, navigator, operator, stewardess);
        fillingTables(request);

        LOG.trace("Set the request attribute all lists");

        LOG.debug("Commands finished");
        return Path.PAGE_DISPATCHER_FUNC;
    }

    /**
     * The method that forms the brigade and presses the brigade into the db.
     *
     * @param pilot      Rank workers.
     * @param navigator  Rank workers.
     * @param operator   Rank workers.
     * @param stewardess Rank workers.
     * @throws DBException
     */

    private void workWithWorkerList(Workers pilot, Workers navigator, Workers operator,
                                    Workers stewardess) throws DBException {
        workersList = new ArrayList<>();
        workersList.add(pilot);
        workersList.add(navigator);
        workersList.add(operator);
        workersList.add(stewardess);

        for (Workers workers : workersList) {
            workers.setBrigade_id(brigade.getId());
            dbManager.updateWorkers(workers);
        }
    }

    /**
     * Filling tables in dispatcher_menu.jsp
     *
     * @param request Http request.
     * @throws DBException
     */

    private void fillingTables(HttpServletRequest request) throws DBException {
        List<Flights> flightsListFormationBrigade = dbManager.findFlights();
        List<Flights> flightsList = dbManager.findFlights();
        List<Application> applicationList = dbManager.findApp();
        List<Workers> workersList = dbManager.findWorkers();
        List<Workers> pilotsList = dbManager.getPilots();
        List<Workers> navigatorList = dbManager.getNavigator();
        List<Workers> operatorList = dbManager.getOperator();
        List<Workers> stewardessList = dbManager.getStewardess();

        flightsListFormationBrigade.removeIf(flight -> flight.getBrigade() != 0);
        pilotsList.removeIf(workers -> workers.getBrigade_id() != 0);
        navigatorList.removeIf(workers -> workers.getBrigade_id() != 0);
        operatorList.removeIf(workers -> workers.getBrigade_id() != 0);
        stewardessList.removeIf(workers -> workers.getBrigade_id() != 0);

        request.setAttribute("flightsList", flightsList);
        request.setAttribute("flightsListFormationBrigade", flightsListFormationBrigade);
        request.setAttribute("applicationList", applicationList);
        request.setAttribute("workersList", workersList);
        request.setAttribute("pilotsList", pilotsList);
        request.setAttribute("navigatorList", navigatorList);
        request.setAttribute("operatorList", operatorList);
        request.setAttribute("stewardessList", stewardessList);
    }
}

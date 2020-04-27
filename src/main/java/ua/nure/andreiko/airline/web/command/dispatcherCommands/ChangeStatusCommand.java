package ua.nure.andreiko.airline.web.command.dispatcherCommands;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.entity.Application;
import ua.nure.andreiko.airline.db.entity.Flights;
import ua.nure.andreiko.airline.db.entity.Workers;
import ua.nure.andreiko.airline.exception.AppException;
import ua.nure.andreiko.airline.exception.DBException;
import ua.nure.andreiko.airline.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Change status flight command.
 *
 * @author E.Andreiko
 */

public class ChangeStatusCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ChangeStatusCommand.class);
    DBManager dbManager;
    String flightNumber;
    String statusId;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command start");

        dbManager = DBManager.getInstance();
        flightNumber = request.getParameter("flightNumber");
        statusId = request.getParameter("statusId");

        Flights flight = dbManager.getFlightIdStatus(flightNumber);
        LOG.trace("Found in DB: flight --> " + flight);

        flight.setStatus_id(statusId);
        dbManager.updateStatusFlight(flight);
        LOG.trace("Update in DB: flight --> " + flight);

        fillingTables(request);
        LOG.trace("Set the request attribute all lists");

        LOG.debug("Commands finished");
        return Path.PAGE_DISPATCHER_FUNC;
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

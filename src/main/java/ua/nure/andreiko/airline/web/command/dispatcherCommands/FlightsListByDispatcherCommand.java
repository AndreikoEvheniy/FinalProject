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
 * Dispatcher first page.
 *
 * @author E.Andreiko
 */

public class FlightsListByDispatcherCommand extends Command {
    private static final Logger LOG = Logger.getLogger(FlightsListByDispatcherCommand.class);
    DBManager dbManager;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command start");

        dbManager = DBManager.getInstance();

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

        LOG.trace("Found in DB: all lists");

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

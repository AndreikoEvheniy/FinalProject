package ua.nure.andreiko.airline.web.command.userCommands;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.entity.Flights;
import ua.nure.andreiko.airline.exception.AppException;
import ua.nure.andreiko.airline.exception.DBException;
import ua.nure.andreiko.airline.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Search flight by number command.
 *
 * @author E.Andreiko
 */

public class SearchFlightsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(SearchFlightsCommand.class);
    DBManager dbManager;
    String number;
    String isFrom;
    String whereTo;
    String date;
    List<Flights> flightsList;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        dbManager = DBManager.getInstance();

        number = request.getParameter("numberFlight");
        isFrom = request.getParameter("isFrom");
        whereTo = request.getParameter("whereTo");
        date = request.getParameter("date");

        if (!number.equals("")) {
            flightsList = dbManager.findFlightsByNumber(number);
        } else {
            findFlightByCriteria();
        }

        request.setAttribute("flightsList", flightsList);
        LOG.trace("Set the request attribute: flightsList --> " + flightsList);
        return Path.PAGE_FLIGHTS_LIST;
    }

    /**
     * This method is for getting list from database by criteria.
     *
     * @throws DBException
     */
    private void findFlightByCriteria() throws DBException {
        LOG.trace("Start method --> findFlightListByCriteria");
        flightsList = dbManager.findFlightsIsFrom(isFrom);

        LOG.trace("Found in DB: flightList -->" + flightsList);

        if (!whereTo.equals("")) {
            flightsList.removeIf(i -> !i.getWhereTo().equals(whereTo));
        }

        if (isFrom.equals("") && whereTo.equals("")) {
            flightsList = dbManager.findFlights();
            flightsList.removeIf(i -> !i.getDate().equals(date));
        } else if (!date.equals("")) {
            flightsList.removeIf(i -> !i.getDate().equals(date));
        }
        LOG.trace("Finish method --> findFlightListByCriteria");
    }
}

package ua.nure.andreiko.airline.web.command.userCommands;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.entity.Flights;
import ua.nure.andreiko.airline.exception.AppException;
import ua.nure.andreiko.airline.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Sorting button by number command.
 *
 * @author E.Andreiko
 */

public class SortingButtonsByNumber extends Command {
    private static final Logger LOG = Logger.getLogger(SortingButtonsByNumber.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Commands starts");

        List<Flights> flightsList = DBManager.getInstance().findFlights();
        LOG.trace("Found in DB: flightsList --> " + flightsList);

        Comparator<Flights> comparator = Comparator.comparing(Flights::getNumber);
        flightsList.sort(comparator);

        request.setAttribute("flightsList", flightsList);
        LOG.trace("Set the request attribute: flightsList --> " + flightsList);

        LOG.debug("Commands finished");
        return Path.PAGE_FLIGHTS_LIST;
    }
}

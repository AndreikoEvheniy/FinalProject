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
import java.util.List;

/**
 * First page user.
 *
 * @author E.Andreiko
 */

public class FlightsListCommand extends Command {
    private static final Logger LOG = Logger.getLogger(FlightsListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        List<Flights> flightsList = DBManager.getInstance().findFlights();
        LOG.trace("Found in DB: flightList -->" + flightsList);


        request.setAttribute("flightsList", flightsList);
        LOG.trace("Set the request attribute: flightList --> " + flightsList);

        LOG.debug("Command finished");
        return Path.PAGE_FLIGHTS_LIST;
    }
}

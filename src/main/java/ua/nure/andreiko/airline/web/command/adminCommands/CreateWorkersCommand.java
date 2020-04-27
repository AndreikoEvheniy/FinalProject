package ua.nure.andreiko.airline.web.command.adminCommands;

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
 * Create worker command.
 *
 * @author E.Andreiko
 */

public class CreateWorkersCommand extends Command {
    private static final Logger LOG = Logger.getLogger(CreateWorkersCommand.class);
    DBManager dbManager;
    List<Flights> flightsList;
    List<Workers> workersList;
    List<Application> applicationList;
    String firstName;
    String lastName;
    int rankId;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        dbManager = DBManager.getInstance();

        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        rankId = Integer.parseInt(request.getParameter("rankId"));

        createNewWorker();
        LOG.trace("Creating a new worker with parameters: --> " + firstName + ", " + lastName + ", " + rankId);


        getListFromDB();
        LOG.trace("Found in DB: flightsList, workersList, applicationList --> " + flightsList + ", " + workersList + ", "
                + applicationList);

        fillingTables(request);
        LOG.trace("Set the request attribute: flightsList, workersList, applicationList --> " + flightsList + ", "
                + workersList + ", " + applicationList);

        LOG.debug("Command finished");
        return Path.PAGE_ADMIN_FUNC;
    }

    /**
     * This method is for getting list from database.
     *
     * @throws DBException
     */
    private void getListFromDB() throws DBException {
        flightsList = dbManager.findFlights();
        workersList = dbManager.findWorkers();
        applicationList = dbManager.findApp();
    }

    /**
     * This method is for filling list to admin page.
     *
     * @param request HttpServletRequest
     */
    private void fillingTables(HttpServletRequest request) {
        request.setAttribute("applicationList", applicationList);
        request.setAttribute("flightsList", flightsList);
        request.setAttribute("workersList", workersList);
    }

    /**
     * This method is for adding a new worker in database.
     *
     * @throws AppException
     */
    private void createNewWorker() throws AppException {
        if (firstName.equals("") && lastName.equals("")) {
            LOG.trace("Type first name and last name employee");
            throw new AppException("Type first name and last name employee");
        } else if (firstName.equals("")) {
            LOG.trace("Type first name employee");
            throw new AppException("Type first name employee");
        } else if (lastName.equals("")) {
            LOG.trace("Type last name employee");
            throw new AppException("Type last name employee");
        } else {
            dbManager.createWorker(firstName, lastName, rankId);
        }
    }
}

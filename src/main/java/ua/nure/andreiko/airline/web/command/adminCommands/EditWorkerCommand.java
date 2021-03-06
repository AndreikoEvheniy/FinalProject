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
 * Employee change command.
 *
 * @author E.Andreiko
 */

public class EditWorkerCommand extends Command {
    private static final Logger LOG = Logger.getLogger(EditWorkerCommand.class);
    DBManager dbManager;
    List<Flights> flightsList;
    List<Workers> workersList;
    List<Application> applicationList;
    int workerId;
    String newLastName;
    String newRank;
    Workers worker;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        dbManager = DBManager.getInstance();
        workerId = Integer.parseInt(request.getParameter("workerId"));
        newLastName = request.getParameter("lastNameInput");
        newRank = request.getParameter("editRankId");

        worker = dbManager.getWorker(workerId);
        LOG.trace("Found in DB: worker --> " + worker);

        updateWorkerInDataBase();
        LOG.trace("Update in DB: worker --> " + worker);

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
     * This method is for update a worker in database.
     *
     * @throws DBException
     */
    private void updateWorkerInDataBase() throws DBException {
        if (!newLastName.equals("") && !newRank.equals("")) {
            worker.setLastName(newLastName);
            worker.setWorkersRank(newRank);
            dbManager.updateLastNameWorker(worker);
            dbManager.updateRankWorker(worker);
        } else if (!newLastName.equals("")) {
            worker.setLastName(newLastName);
            dbManager.updateLastNameWorker(worker);
        } else {
            worker.setWorkersRank(newRank);
            dbManager.updateRankWorker(worker);
        }
    }
}

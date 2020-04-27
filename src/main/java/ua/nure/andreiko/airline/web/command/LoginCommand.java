package ua.nure.andreiko.airline.web.command;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.Role;
import ua.nure.andreiko.airline.db.entity.User;
import ua.nure.andreiko.airline.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login command.
 *
 * @author E.Andreiko
 */

public class LoginCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.debug("Command start");

        HttpSession session = request.getSession();

        DBManager dbManager = DBManager.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login ==> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("login/password cannot be empty");
        }

        User user = dbManager.findUserByLogin(login);
        LOG.trace("Found in DB: user ==> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            throw new AppException("Cannot find user with such login/password");
        }

        Role userRole = Role.getRole(user);
        LOG.trace("userRole ==> " + userRole);

        String forward = Path.PAGE_ERROR_PAGE;

        if (userRole == Role.ADMIN) {
            forward = Path.COMMAND_LIST_ADMIN;
        }

        if (userRole == Role.DISPATCHER) {
            forward = Path.COMMAND_LIST_DISPATCHER;
        }

        if (userRole == Role.USER) {
            forward = Path.COMMAND_LIST_USER;
        }
        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user ==> " + user);

        session.setAttribute("userRole", userRole);
        LOG.trace("Set the session attribute: userRole ==> " + userRole);

        LOG.info("User " + user + "logged as " + userRole.toString().toLowerCase());

        LOG.debug("Command finished");
        return forward;
    }
}

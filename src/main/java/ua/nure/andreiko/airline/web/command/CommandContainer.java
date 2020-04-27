package ua.nure.andreiko.airline.web.command;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.web.command.adminCommands.*;
import ua.nure.andreiko.airline.web.command.dispatcherCommands.BrigadeFormationCommand;
import ua.nure.andreiko.airline.web.command.dispatcherCommands.ChangeStatusCommand;
import ua.nure.andreiko.airline.web.command.dispatcherCommands.CreateAppCommand;
import ua.nure.andreiko.airline.web.command.dispatcherCommands.FlightsListByDispatcherCommand;
import ua.nure.andreiko.airline.web.command.userCommands.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author E.Andreiko
 */

public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());

        commands.put("listUser", new FlightsListCommand());
        commands.put("searchFlight", new SearchFlightsCommand());
        commands.put("sortedListByNumber", new SortingButtonsByNumber());
        commands.put("sortedListByName", new SortingButtonsByName());

        commands.put("listDispatcher", new FlightsListByDispatcherCommand());
        commands.put("brigadeFormation", new BrigadeFormationCommand());
        commands.put("changeStatus", new ChangeStatusCommand());
        commands.put("createApp", new CreateAppCommand());

        commands.put("listAdmin", new AdminMainPageCommand());
        commands.put("editFlight", new EditFlightCommand());
        commands.put("editWorker", new EditWorkerCommand());
        commands.put("createFlights", new CreateFlightsCommand());
        commands.put("createWorkers", new CreateWorkersCommand());
        commands.put("removeFlight", new RemoveFlightCommand());
        commands.put("removeWorker", new RemoveWorkerCommand());
        commands.put("changeAppStatus", new ChangeStatusAppCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}

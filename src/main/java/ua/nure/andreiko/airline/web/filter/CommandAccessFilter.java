package ua.nure.andreiko.airline.web.filter;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.Path;
import ua.nure.andreiko.airline.db.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter.
 *
 * @author E.Andreiko
 */

public class CommandAccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

    private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
    private List<String> commons = new ArrayList<String>();
    private List<String> outOfControl = new ArrayList<String>();

    @Override
    public void destroy() {
        LOG.debug("Filter destruction starts");
        //do nothing
        LOG.debug("Filter destruction finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");

        if (accessAllowed(servletRequest)) {
            LOG.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMassage = "You don`t have permission to access the request resource";

            servletRequest.setAttribute("errorMassage", errorMassage);
            LOG.trace("Set the request attribute: errorMessage ==> " + errorMassage);

            servletRequest.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        if (outOfControl.contains(commandName)) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return false;
        }
        Role userRole = (Role)session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }
        return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Filter initialization starts");
        //roles
        accessMap.put(Role.ADMIN, asList(filterConfig.getInitParameter("admin")));
        accessMap.put(Role.DISPATCHER, asList(filterConfig.getInitParameter("dispatcher")));
        accessMap.put(Role.USER, asList(filterConfig.getInitParameter("user")));
        LOG.trace("accessMap ==> " + accessMap);
        //commons
        commons = asList(filterConfig.getInitParameter("common"));
        LOG.trace("Common commands ==> " + commons);
        //out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands ==> " + outOfControl);

        LOG.debug("Filter initialization finished");
    }


    /**
     * Extracts parameter values from string.
     *
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }
}

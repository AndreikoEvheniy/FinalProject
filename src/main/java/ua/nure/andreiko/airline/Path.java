package ua.nure.andreiko.airline;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author E.Andreiko
 */

public class Path {

    /**
     * Pages.
     */
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_FLIGHTS_LIST = "/WEB-INF/jsp/user/user_menu.jsp";
    public static final String PAGE_DISPATCHER_FUNC = "/WEB-INF/jsp/dispatcher/dispatcher_menu.jsp";
    public static final String PAGE_ADMIN_FUNC = "/WEB-INF/jsp/admin/admin_menu.jsp";

    /**
     * Commands for functionality.
     */
    public static final String COMMAND_LIST_ADMIN = "/controller?command=listAdmin";
    public static final String COMMAND_LIST_DISPATCHER = "/controller?command=listDispatcher";
    public static final String COMMAND_LIST_USER = "/controller?command=listUser";
}

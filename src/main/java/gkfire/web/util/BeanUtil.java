package gkfire.web.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 * The type Bean util.
 */
public class BeanUtil {

    /**
     * Gets session.
     *
     * @return the session
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    /**
     * Gets request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }

    /**
     * Exceute js.
     *
     * @param js the js
     */
    public static void exceuteJS(String js) {
        RequestContext.getCurrentInstance().execute(js);
    }

    /**
     * Is ajax request boolean.
     *
     * @return the boolean
     */
    public static boolean isAjaxRequest() {
        return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
    }

//    public static String getUserName() {
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//        return session.getAttribute("username").toString();
//    }
//
//    public static String getUserId() {
//        HttpSession session = getSession();
//        if (session != null) {
//            return (String) session.getAttribute("userid");
//        } else {
//            return null;
//        }
//    }
//
//    public static String getOwnerId() {
//        HttpSession session = getSession();
//        if (session != null) {
//            return (String) session.getAttribute("OwnerId");
//        } else {
//            return null;
//        }
//    }
}

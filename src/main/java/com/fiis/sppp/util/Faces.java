package com.fiis.sppp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

/**
 * The type Faces.
 */
public class Faces {

    /**
     * Attributo alternativo para mostrar nombre del usuario logeado
     */
    public static String ATTRIBUTE_USER_ADMIN = "userLoged";
    /**
     * The constant ATTRIBUTE_PRACTICA.
     */
    public static String ATTRIBUTE_PRACTICA = "practiceId";

    /**
     * Para ejecutar Java Script como tambien poder hacer update a algun
     * elemento de la vista PrimeFaces Util.
     *
     * @return request context
     */
    public static RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public static ServletContext getContext() {
        return (ServletContext)Faces.getExternalContext().getContext();
    }

    /**
     * Gets resource as stream.
     *
     * @param path the path
     * @return the resource as stream
     */
    public static InputStream getResourceAsStream(String path) {
        return FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path);
    }

    /**
     * Get current instance faces context.
     *
     * @return the faces context
     */
    public static FacesContext getCurrentInstance(){
        return FacesContext.getCurrentInstance();
    }

    /**
     * Gets real path.
     *
     * @param path the path
     * @return the real path
     */
    public static String getRealPath(String path) {
        return FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
    }

    /**
     * Gets external context.
     *
     * @return the external context
     */
    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * Gets session attribute.
     *
     * @param key the key
     * @return the session attribute
     */
    public static Object getSessionAttribute(String key) {
        return Faces.getRequest().getSession().getAttribute(key);
    }

    /**
     * Sets session attribute.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setSessionAttribute(String key, Object value) {
        Faces.getRequest().getSession().setAttribute(key, value);
    }

    /**
     * Remove session attribute.
     *
     * @param key the key
     */
    public static void removeSessionAttribute(String key) {
        Faces.getRequest().getSession().removeAttribute(key);
    }

    /**
     * Invalidate session.
     */
    public static void invalidateSession() {
        Faces.getRequest().getSession().invalidate();
    }

    /**
     * Logout.
     *
     * @throws ServletException the servlet exception
     */
    public static void logout() throws ServletException {
        Faces.getRequest().logout();
    }

    /**
     * Redirect.
     *
     * @param url the url
     * @throws IOException the io exception
     */
    public static void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    /**
     * Gets request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) Faces.getExternalContext().getRequest();
    }

    /**
     * Principalmente para redireccionar hacia una pagina con sentRedirect()
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {
        return ((HttpServletResponse) Faces.getExternalContext().getResponse());
    }

    /**
     * Con esto nos deslogeamos, el siguiente paso que deberia realizar el de
     * Redireccionar.
     *
     * @throws ServletException the servlet exception
     */
    public static void logOut() throws ServletException {
        //Faces.logout();
    }

    /**
     * Para Enviar mensajes a la vista
     *
     * @param summary      El contenido que tendra los mensajes
     * @param details      Los detalles que se mostraran despues de summary
     * @param TYPE_MESSAGE El tipo de mensaje que mostrara, Alert, warning, error, fatal
     */
    public static void addMessage(String summary, String details, FacesMessage.Severity TYPE_MESSAGE) {
        FacesMessage message = new FacesMessage(TYPE_MESSAGE, summary, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
        //Faces.getContext().addMessage(null, message);
        Faces.getRequestContext().update("growl");
    }

    /**
     * Encrypt user int.
     *
     * @param userName the user name
     * @param password the password
     * @return the int
     */
    public static int encryptUser(String userName, String password) {
        return (userName + password).hashCode() / 2;
    }

    /**
     * Gets nombre decano.
     *
     * @return the nombre decano
     */
    public static String getNombreDecano() {
        String archivo = ManejoArchivo.getTextoOfFile(Faces.getRealPath(File.separatorChar + "resources" + File.separatorChar + "file" + File.separatorChar + "config.txt"));
        
        if (archivo == null) {
            archivo = "";
        }

        if (!archivo.equals("")) {
            String[] files = archivo.split("=");
            return files[0];
        }
        return "";
    }

    /**
     * Gets nombre anio.
     *
     * @return the nombre anio
     */
    public static String getNombreAnio() {
        String archivo = ManejoArchivo.getTextoOfFile(Faces.getRealPath(File.separatorChar + "resources" + File.separatorChar + "file" + File.separatorChar + "config.txt"));
        
        if (archivo == null) {
            archivo = "";
        }

        if (!archivo.equals("")) {
            String[] files = archivo.split("=");
            return files[1];
        }
        return "";
    }
    
}

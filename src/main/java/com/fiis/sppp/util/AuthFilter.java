package com.fiis.sppp.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Auth filter.
 */
//@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {

    /**
     * Instantiates a new Auth filter.
     */
    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            // allow user to proccede if url is login.xhtml or user logged in or
            // user is accessing any page in 
            // public folder
            String reqURI = req.getRequestURI();

            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute(Faces.ATTRIBUTE_USER_ADMIN) != null)
                    || (ses != null && ses.getAttribute(Faces.ATTRIBUTE_PRACTICA) != null)
                    || reqURI.indexOf("/public/") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                try {
                    chain.doFilter(request, response);
                } catch (java.lang.IndexOutOfBoundsException e) {
                }
            } else {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("Mensaje de Error en Filtro: " + t.getMessage());
        }
    } // doFilter

    @Override
    public void destroy() {

    }
}

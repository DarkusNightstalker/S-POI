/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean;

import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.JavaScriptMessage;
import edu.unas.spoi.security.model.User;
import edu.unas.spoi.security.service.interfac.IPermissionService;
import edu.unas.spoi.security.service.interfac.IUserService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.web.util.BeanUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Data;

/**
 * The type Session bean.
 *
 * @author Darkus
 */
@ManagedBean
@SessionScoped
@Data
public class SessionBean implements java.io.Serializable {
    
    @ManagedProperty(value = "#{userService}")
    private IUserService userService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{permissionService}")
    private IPermissionService permissionService;
    private Authentication authentication;
    
    private User currentUser;
    private Dependency currentDependency;
    private Integer operationYear;
    private ILoadable loadable;
    private String topLeftName;
    private String avatarURL;
    private boolean superAdmin;
    private List<JavaScriptMessage> messages;
    private List<Integer> operationYears;
    private List<String> permissions;
    private List<Object[]> dependenciesAllowed;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        setSuperAdmin(false);
        setMessages((List<JavaScriptMessage>) new ArrayList());
        setAuthentication(new Authentication());
        operationYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        operationYears = new ArrayList();
        for (int i = operationYear + 2; i > 2000; i--) {
            operationYears.add(i);
        }
    }

    /**
     * On load.
     */
    public void onLoad() {
        try {
            loadable.onLoad(false);
        } catch (NullPointerException e) {
        }
        if (BeanUtil.isAjaxRequest()) {
            return;
        }
        updateDependenciesAllowed();
    }

    /**
     * Messages to js string.
     *
     * @return the string
     */
    public String messagesToJS() {
        String js = "";
        while (!messages.isEmpty()) {
            js += getMessages().remove(0).toJavaScript();
        }
        return js;
    }

    /**
     * Authorize boolean.
     *
     * @param code the code
     * @return the boolean
     */
    public boolean authorize(String code) {
        if (currentDependency == null) {
            return true;
        }
        for (String item : permissions) {
            if (item.equals(code)) {
                return true;
            }
        }
        return false;
    }
    
    private void loadPermissions() {
        permissions = userService.getPermissionsCode(currentUser.getId());
    }

    /**
     * Sets current user.
     *
     * @param currentUser the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        try {
            this.setCurrentDependency(currentUser.getInvolved().getDependency());
            this.setOperationYear(getCurrentDependency().getOperationYear());
            this.setTopLeftName(currentUser.getUsername());
            updateDependenciesAllowed();
            this.setSuperAdmin(false);
            loadPermissions();
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("user", currentUser);
        } catch (NullPointerException e) {
            if (currentUser != null) {
                this.setSuperAdmin(true);
                this.setTopLeftName(currentUser.getUsername());
                setAvatarURL("/assets/img/avatars/no-image.png");
                permissions = Collections.EMPTY_LIST;
                dependenciesAllowed = Collections.EMPTY_LIST;
                ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("user", currentUser);
            }
        }
    }

    /**
     * Sets operation year.
     *
     * @param operationYear the operation year
     */
    public void setOperationYear(Integer operationYear) {
        this.operationYear = operationYear;
    }
    
    private void updateDependenciesAllowed() {
        if (currentDependency == null) {
            dependenciesAllowed = Collections.EMPTY_LIST;
            return;
        }
        dependenciesAllowed = dependencyService.getChildrenDependenciesBasicData(currentUser.getInvolved().getDependency().getPath(), operationYear);
        
    }

    /**
     * The type Authentication.
     */
// <editor-fold defaultstate="collapsed" desc="Authentication Class">
    @Data
    public final class Authentication implements java.io.Serializable {
        
        private String username;
        private String password;
        
        private boolean validLogin;
        private String messageLogin;
        private String titleLogin;

        /**
         * Instantiates a new Authentication.
         */
        Authentication() {
            refresh();
        }

        /**
         * Refresh.
         */
        void refresh() {
            validLogin = true;
        }

        /**
         * Login string.
         *
         * @return the string
         * @throws IOException the io exception
         */
        public String login() throws IOException {
            validLogin = true;
            try {
                String username = this.username.trim();
                String password = this.password.trim();
                User currentUser = userService.login(username, password);
                if (currentUser == null) {
                    throw new IndexOutOfBoundsException();
                } else if (!currentUser.getActive()) {
                    throw new IndexOutOfBoundsException();
                }
                SessionBean.this.setCurrentUser(currentUser);
                return "index?faces-redirect=true";
            } catch (IndexOutOfBoundsException e) {
                validLogin = false;
                messages.add(new SmartMessage("Error de Autenticacion", "La combinacion usuario/contraseña no es correcta", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated"));
                
                return "login?faces-redirect=true";
            } catch (Exception e) {
                e.printStackTrace();
                validLogin = false;
                messages.add(new SmartMessage(e.getClass().getName(), e.getMessage(), SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated"));

//                messageLogin = messageLogin.replace("\n", " \\n ");
                return "login?faces-redirect=true";
            }
        }

        /**
         * Logout string.
         *
         * @return the string
         */
        public String logout() {
            HttpSession session = gkfire.web.util.BeanUtil.getSession();
            session.invalidate();
            refresh();
            return "login?faces-redirect=true";
        }
        
    }

    //</editor-fold>
}

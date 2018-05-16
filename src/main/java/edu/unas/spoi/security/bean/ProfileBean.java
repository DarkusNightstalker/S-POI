/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.bean;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import edu.unas.spoi.security.service.interfac.IPermissionCategoryService;
import edu.unas.spoi.security.service.interfac.IPermissionService;
import edu.unas.spoi.security.service.interfac.IUserService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Profile bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements ILoadable, Serializable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{permissionService}")
    private IPermissionService permissionService;
    @ManagedProperty(value = "#{permissionCategoryService}")
    private IPermissionCategoryService permissionCategoryService;
    @ManagedProperty(value = "#{userService}")
    private IUserService userService;
    private String password;
    private String currentPassword;
    private String repeatPassword;
    private boolean validPassword;
    private String messagePassword;
    private Map<Integer, List<Permission>> permissions;
    private List<PermissionCategory> permissionCategories;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
    }

    /**
     * Refresh password.
     */
    public void refreshPassword() {
        password = "";
        repeatPassword = "";
        currentPassword = "";
        validPassword = true;
        messagePassword = "";
    }

    /**
     * Change psssword.
     */
    public void changePsssword() {
        sessionBean.getCurrentUser().setPassword(password);
        userService.saveOrUpdate(sessionBean.getCurrentUser());
        new SmartMessage("Exito!", "Se ha cambiado la clave", SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save").execute();
        validPassword = true;

    }

    /**
     * Refresh.
     */
    public void refresh() {
        if (!sessionBean.isSuperAdmin()) {

            List<String> permissionCode = sessionBean.getPermissions();
            List<Integer> iC = null;
            AliasList aliasList = new AliasList();
            aliasList.add("permissionCategory", "pc");
            if (!permissionCode.isEmpty()) {
                ProjectionList projectionList = Projections.projectionList()
                        .add(Projections.distinct(Projections.property("pc.id")));
                OrderList orderList = new OrderList();
                orderList.add(Order.asc("pc.id"));
                CriterionList criterionList = new CriterionList()
                        ._add(Restrictions.in("code", permissionCode))
                        ._add(Restrictions.eq("active", true));
                iC = permissionService.addRestrictionsVariant(aliasList, projectionList, criterionList, orderList);
            } else {
                iC = new ArrayList();
            }
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("id"));
            permissions = new HashMap();
            permissionCategories = new ArrayList<>();
            for (Integer id : iC) {
                CriterionList criterionList = new CriterionList()
                        ._add(Restrictions.in("code", permissionCode))
                        ._add(Restrictions.eq("pc.id", id))
                        ._add(Restrictions.eq("active", true));
                List<Permission> data = permissionService.addRestrictionsVariant(criterionList, orderList, aliasList);

                permissionCategories.add(permissionCategoryService.getById(id));
                permissions.put(id, data);
            }
        }
    }

    /**
     * Gets session bean.
     *
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * Sets session bean.
     *
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets repeat password.
     *
     * @return the repeatPassword
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Sets repeat password.
     *
     * @param repeatPassword the repeatPassword to set
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Map<Integer, List<Permission>> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions to set
     */
    public void setPermissions(Map<Integer, List<Permission>> permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets permission categories.
     *
     * @return the permissionCategories
     */
    public List<PermissionCategory> getPermissionCategories() {
        return permissionCategories;
    }

    /**
     * Sets permission categories.
     *
     * @param permissionCategories the permissionCategories to set
     */
    public void setPermissionCategories(List<PermissionCategory> permissionCategories) {
        this.permissionCategories = permissionCategories;
    }

    /**
     * Gets permission service.
     *
     * @return the permissionService
     */
    public IPermissionService getPermissionService() {
        return permissionService;
    }

    /**
     * Sets permission service.
     *
     * @param permissionService the permissionService to set
     */
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * Gets user service.
     *
     * @return the userService
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * Sets user service.
     *
     * @param userService the userService to set
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Gets permission category service.
     *
     * @return the permissionCategoryService
     */
    public IPermissionCategoryService getPermissionCategoryService() {
        return permissionCategoryService;
    }

    /**
     * Sets permission category service.
     *
     * @param permissionCategoryService the permissionCategoryService to set
     */
    public void setPermissionCategoryService(IPermissionCategoryService permissionCategoryService) {
        this.permissionCategoryService = permissionCategoryService;
    }

    /**
     * Gets current password.
     *
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets current password.
     *
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Is valid password boolean.
     *
     * @return the validPassword
     */
    public boolean isValidPassword() {
        return validPassword;
    }

    /**
     * Sets valid password.
     *
     * @param validPassword the validPassword to set
     */
    public void setValidPassword(boolean validPassword) {
        this.validPassword = validPassword;
    }

    /**
     * Gets message password.
     *
     * @return the messagePassword
     */
    public String getMessagePassword() {
        return messagePassword;
    }

    /**
     * Sets message password.
     *
     * @param messagePassword the messagePassword to set
     */
    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }
}

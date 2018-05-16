/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unas.spoi.security.bean;

import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.security.service.UserService;
import edu.unas.spoi.security.service.interfac.IUserService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 * The type User bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class UserBean implements ILoadable,java.io.Serializable {
    
    @ManagedProperty(value = "#{userService}")
    private IUserService userService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    
    private DependencySearcher dependencySearcher;
    
    private String fullName;
    private String username;
    private Integer dependencyId;
    private List<Object[]> data;
    
    private Integer idUserSelected;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        dependencySearcher = new DependencySearcher();
    }
    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
        dependencySearcher.update();
    }

    /**
     * Refresh.
     */
    public void refresh() {
        fullName = "";
        username = "";
        dependencyId = null;
        search();
    }

    /**
     * Change state.
     */
    public void changeState() {
        String sql = "update User set active=false where id=" + idUserSelected;
        try {
            userService.updateHQL(sql);
            new SmartMessage("Se ha eliminado un usuario...!", ".", SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Search.
     */
    public void search() {
        fullName = fullName.trim();
        username = username.trim();
        ProjectionList  projectionList = Projections.projectionList()
                .add(Projections.distinct(Projections.property("id")))
                .add(Projections.property("p.fullName"))
                .add(Projections.property("username"))
                .add(Projections.property("d.path"))
                .add(Projections.property("active"))
                .add(Projections.property("d.id"));
        OrderList orderList = new OrderList();
        orderList.add(Order.desc("id"));
        AliasList aliasList = new AliasList();
        aliasList.add("involved", "i",JoinType.LEFT_OUTER_JOIN);
        aliasList.add("i.person", "p",JoinType.LEFT_OUTER_JOIN);
        aliasList.add("i.dependency", "d",JoinType.LEFT_OUTER_JOIN);
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        
        if (dependencyId != null) {
            criterionList.add(Restrictions.eq("d.id", dependencyId));
        }
        if(fullName.length() != 0){
            criterionList.add(Restrictions.like("p.fullName", fullName,MatchMode.ANYWHERE).ignoreCase());
        }
        if(username.length() != 0){
            criterionList.add(Restrictions.like("username", username,MatchMode.ANYWHERE).ignoreCase());
        }
        data = userService.addRestrictionsVariant(Arrays.asList(aliasList, projectionList, criterionList, orderList));
    }


    /**
     * Gets full name.
     *
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets id user selected.
     *
     * @return the id user selected
     */
    public Integer getIdUserSelected() {
        return idUserSelected;
    }

    /**
     * Sets id user selected.
     *
     * @param idUserSelected the id user selected
     */
    public void setIdUserSelected(Integer idUserSelected) {
        this.idUserSelected = idUserSelected;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets dependency id.
     *
     * @return the dependencyId
     */
    public Integer getDependencyId() {
        return dependencyId;
    }

    /**
     * Sets dependency id.
     *
     * @param dependencyId the dependencyId to set
     */
    public void setDependencyId(Integer dependencyId) {
        this.dependencyId = dependencyId;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<Object[]> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data to set
     */
    public void setData(List<Object[]> data) {
        this.data = data;
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
     * Gets dependency service.
     *
     * @return the dependencyService
     */
    public IDependencyService getDependencyService() {
        return dependencyService;
    }

    /**
     * Sets dependency service.
     *
     * @param dependencyService the dependencyService to set
     */
    public void setDependencyService(IDependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    /**
     * Gets dependency searcher.
     *
     * @return the dependencySearcher
     */
    public DependencySearcher getDependencySearcher() {
        return dependencySearcher;
    }

    /**
     * Sets dependency searcher.
     *
     * @param dependencySearcher the dependencySearcher to set
     */
    public void setDependencySearcher(DependencySearcher dependencySearcher) {
        this.dependencySearcher = dependencySearcher;
    }

    /**
     * The type Dependency searcher.
     */
    public class DependencySearcher implements java.io.Serializable{
        private List<Object[]> data;

        /**
         * Update.
         */
        public void update(){
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("path"))
                    .add(Projections.property("name"));
            dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("involveds", "i");
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.isNotEmpty("i.users"));
            criterionList.add(Restrictions.isNotEmpty("involveds"));
            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("path"));
            data =  getDependencyService().getListByRestrictions(projectionList,criterionList,orderList,aliasList);
        }

        /**
         * Gets data.
         *
         * @return the data
         */
        public List<Object[]> getData() {
            return data;
        }

        /**
         * Sets data.
         *
         * @param data the data to set
         */
        public void setData(List<Object[]> data) {
            this.data = data;
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.view;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AViewBean;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type View product budget program bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ViewProductBudgetProgramBean extends AViewBean<ProductBudgetProgram, Long, IProductBudgetProgramService> {

    /**
     * The Main service.
     */
    @ManagedProperty(value = "#{productBudgetProgramService}")
    protected IProductBudgetProgramService mainService;
    /**
     * The Activity budget program service.
     */
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    protected IActivityBudgetProgramService activityBudgetProgramService;
    /**
     * The Session bean.
     */
    @ManagedProperty(value = "#{sessionBean}")
    protected SessionBean sessionBean;

    /**
     * The Bp data.
     */
    protected List<Object[]> bpData;
    /**
     * The Abp map.
     */
    protected Map<Integer, List<Object[]>> abpMap;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        permissionDisabled = "R_BP";
    }

    @Override
    protected void update(boolean widthDisabled) {
        super.update(widthDisabled);

        AliasList aliasList = new AliasList();
        aliasList.add("budgetPrograms", "bp");
        CriterionList criterionList = new CriterionList()
                ._add(Restrictions.eq("id", selected.getId()))
                ._add(Restrictions.eq("bp.active", true));

        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("bp.id"))
                .add(Projections.property("bp.code"))
                .add(Projections.property("bp.name"));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("code"));

        bpData = mainService.addRestrictionsVariant(aliasList, criterionList, projectionList, orderList);

        abpMap = new HashMap();
        for (Object[] item : bpData) {
            aliasList = new AliasList();
            aliasList.add("productBudgetProgram", "pbp");
            aliasList.add("budgetProgram", "bp");
            projectionList = Projections.projectionList()
                    .add(Projections.id())
                    .add(Projections.property("code"))
                    .add(Projections.property("name"))
                    .add(Projections.property("functionalSequence"))
                    .add(Projections.property("id"));
            criterionList = new CriterionList()
                    ._add(Restrictions.eq("pbp.id", selected.getId()))
                    ._add(Restrictions.eq("bp.id", item[0]))
                    ._add(Restrictions.eq("active", true));
            List<Object[]> data = activityBudgetProgramService.addRestrictionsVariant(aliasList, criterionList, projectionList);
            projectionList = Projections.projectionList().add(Projections.count("sg.id"));
            aliasList.clear();
            aliasList.add("specificGoals", "sg");
            for (Object[] item2 : data) {
                criterionList.clear();
                criterionList
                        ._add(Restrictions.eq("sg.active", true))
                        ._add(Restrictions.eq("id", item2[0]));
                item2[4] = activityBudgetProgramService.addRestrictionsVariant(aliasList, projectionList, criterionList).get(0);
            }
            abpMap.put((Integer)item[0], data);
        }

    }

    /**
     * @return the mainService
     */
    public IProductBudgetProgramService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    public void setMainService(IProductBudgetProgramService mainService) {
        this.mainService = mainService;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets activity budget program service.
     *
     * @return the activityBudgetProgramService
     */
    public IActivityBudgetProgramService getActivityBudgetProgramService() {
        return activityBudgetProgramService;
    }

    /**
     * Sets activity budget program service.
     *
     * @param activityBudgetProgramService the activityBudgetProgramService to set
     */
    public void setActivityBudgetProgramService(IActivityBudgetProgramService activityBudgetProgramService) {
        this.activityBudgetProgramService = activityBudgetProgramService;
    }

    /**
     * Gets bp data.
     *
     * @return the bpData
     */
    public List<Object[]> getBpData() {
        return bpData;
    }

    /**
     * Sets bp data.
     *
     * @param bpData the bpData to set
     */
    public void setBpData(List<Object[]> bpData) {
        this.bpData = bpData;
    }

    /**
     * Gets abp map.
     *
     * @return the abpMap
     */
    public Map<Integer, List<Object[]>> getAbpMap() {
        return abpMap;
    }

    /**
     * Sets abp map.
     *
     * @param abpMap the abpMap to set
     */
    public void setAbpMap(Map<Integer, List<Object[]>> abpMap) {
        this.abpMap = abpMap;
    }

}

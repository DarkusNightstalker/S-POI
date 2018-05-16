/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.view;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.model.UoM;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AViewBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type View activity budget program bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ViewActivityBudgetProgramBean extends AViewBean<ActivityBudgetProgram, Long, IActivityBudgetProgramService> {

    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService mainService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private BudgetProgram budgetProgram;
    private ProductBudgetProgram productBudgetProgram;
    private UoM uom;
    private List<Object[]> saData;
    private Map<Integer, List<Object[]>> sgMap;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        permissionDisabled = "R_ABP";
    }

    @Override
    protected void update(boolean widthDisabled) {
        super.update(widthDisabled);
        Object[] aData = (Object[]) mainService.getByHQL("SELECT abp.budgetProgram,abp.productBudgetProgram FROM ActivityBudgetProgram abp WHERE abp.id =  ?", selected.getId());

        budgetProgram = (BudgetProgram) aData[0];
        productBudgetProgram = (ProductBudgetProgram) aData[1];
        uom = (UoM) mainService.getByHQL("SELECT abp.uom FROM  ActivityBudgetProgram abp WHERE abp.id =  ?", selected.getId());

        StrategicPlan plan = strategicPlanService.getBy(sessionBean.getOperationYear());

        AliasList aliasList = new AliasList();
        aliasList.add("strategicActivities", "sta");
        aliasList.add("sta.specificGoal", "spg");
        aliasList.add("spg.strategicGoal", "stg");
        aliasList.add("stg.strategicAxis", "sa");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("sa.active", true));
        criterionList.add(Restrictions.eq("sa.strategicPlan", plan));
        criterionList.add(Restrictions.eq("id", selected.getId()));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.distinct(Projections.property("sa.id")))
                .add(Projections.property("sa.code"))
                .add(Projections.property("sa.name"))
                .add(Projections.property("sa.description"));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("sa.code"));
        saData = mainService.addRestrictionsVariant(aliasList, criterionList, projectionList, orderList);
        projectionList = Projections.projectionList()
                .add(Projections.distinct(Projections.property("sta.id")))
                .add(Projections.property("sta.code"))
                .add(Projections.property("sta.name"));

        sgMap = new HashMap();
        for (Object[] item : saData) {
            criterionList = new CriterionList()
                    ._add(Restrictions.eq("spg.active", true))
                    ._add(Restrictions.eq("sa.strategicPlan", plan))
                    ._add(Restrictions.eq("id", selected.getId()))
                    ._add(Restrictions.eq("sa.id", item[0]));
            List<Object[]> data = mainService.addRestrictionsVariant(aliasList, criterionList, projectionList);
            sgMap.put((Integer) item[0], data);
        }
    }

    /**
     * @return the mainService
     */
    @Override
    public IActivityBudgetProgramService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IActivityBudgetProgramService mainService) {
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
     * Gets budget program.
     *
     * @return the budgetProgram
     */
    public BudgetProgram getBudgetProgram() {
        return budgetProgram;
    }

    /**
     * Sets budget program.
     *
     * @param budgetProgram the budgetProgram to set
     */
    public void setBudgetProgram(BudgetProgram budgetProgram) {
        this.budgetProgram = budgetProgram;
    }

    /**
     * Gets product budget program.
     *
     * @return the productBudgetProgram
     */
    public ProductBudgetProgram getProductBudgetProgram() {
        return productBudgetProgram;
    }

    /**
     * Sets product budget program.
     *
     * @param productBudgetProgram the productBudgetProgram to set
     */
    public void setProductBudgetProgram(ProductBudgetProgram productBudgetProgram) {
        this.productBudgetProgram = productBudgetProgram;
    }

    /**
     * Gets uom.
     *
     * @return the uom
     */
    public UoM getUom() {
        return uom;
    }

    /**
     * Sets uom.
     *
     * @param uom the uom to set
     */
    public void setUom(UoM uom) {
        this.uom = uom;
    }

    /**
     * Gets sa data.
     *
     * @return the saData
     */
    public List<Object[]> getSaData() {
        return saData;
    }

    /**
     * Sets sa data.
     *
     * @param saData the saData to set
     */
    public void setSaData(List<Object[]> saData) {
        this.saData = saData;
    }

    /**
     * Gets sg map.
     *
     * @return the sgMap
     */
    public Map<Integer, List<Object[]>> getSgMap() {
        return sgMap;
    }

    /**
     * Sets sg map.
     *
     * @param sgMap the sgMap to set
     */
    public void setSgMap(Map<Integer, List<Object[]>> sgMap) {
        this.sgMap = sgMap;
    }

    /**
     * Gets strategic plan service.
     *
     * @return the strategicPlanService
     */
    public IStrategicPlanService getStrategicPlanService() {
        return strategicPlanService;
    }

    /**
     * Sets strategic plan service.
     *
     * @param strategicPlanService the strategicPlanService to set
     */
    public void setStrategicPlanService(IStrategicPlanService strategicPlanService) {
        this.strategicPlanService = strategicPlanService;
    }

}

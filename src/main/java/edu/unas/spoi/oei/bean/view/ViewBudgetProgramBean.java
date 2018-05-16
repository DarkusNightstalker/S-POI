/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.view;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.generic.interfac.IGenericService;
import gkfire.web.bean.AViewBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

/**
 * The type View budget program bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ViewBudgetProgramBean extends AViewBean<BudgetProgram, Integer, IBudgetProgramService> {

    /**
     * The Main service.
     */
    @ManagedProperty(value = "#{budgetProgramService}")
    protected IBudgetProgramService mainService;
    /**
     * The Product budget program service.
     */
    @ManagedProperty(value = "#{productBudgetProgramService}")
    protected IProductBudgetProgramService productBudgetProgramService;
    /**
     * The Session bean.
     */
    @ManagedProperty(value = "#{sessionBean}")
    protected SessionBean sessionBean;

    /**
     * The Pbp data.
     */
    protected List<Object[]> pbpData;

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        permissionDisabled = "R_BP";
    }
    
    @Override
    protected void update(boolean widthDisabled) {
        super.update(widthDisabled);
        AliasList aliasList = new AliasList();
        aliasList.add("budgetPrograms", "bp");
        CriterionList criterionList = new CriterionList()
                ._add(Restrictions.eq("bp.id", selected.getId()))
                ._add(Restrictions.eq("active", true));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.id())
                .add(Projections.property("code"))
                .add(Projections.property("name"))
                .add(Projections.sqlProjection("(SELECT COUNT(*) FROM activity_budget_program abp WHERE abp.id_product_budget_program = {alias}.id ) as count", new String[]{"count"}, new Type[]{IntegerType.INSTANCE}));
        pbpData = productBudgetProgramService.addRestrictionsVariant(aliasList,criterionList,projectionList);
    }

    /**
     * @return the mainService
     */
    @Override
    public IBudgetProgramService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IBudgetProgramService mainService) {
        this.mainService = mainService;
    }

    /**
     * Gets product budget program service.
     *
     * @return the productBudgetProgramService
     */
    public IProductBudgetProgramService getProductBudgetProgramService() {
        return productBudgetProgramService;
    }

    /**
     * Sets product budget program service.
     *
     * @param productBudgetProgramService the productBudgetProgramService to set
     */
    public void setProductBudgetProgramService(IProductBudgetProgramService productBudgetProgramService) {
        this.productBudgetProgramService = productBudgetProgramService;
    }

    /**
     * @return the sessionBean
     */
    @Override
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    @Override
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets pbp data.
     *
     * @return the pbpData
     */
    public List<Object[]> getPbpData() {
        return pbpData;
    }

    /**
     * Sets pbp data.
     *
     * @param pbpData the pbpData to set
     */
    public void setPbpData(List<Object[]> pbpData) {
        this.pbpData = pbpData;
    }
}

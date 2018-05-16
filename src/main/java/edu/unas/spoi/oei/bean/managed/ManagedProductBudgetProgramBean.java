/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.managed;

import gkfire.auditory.Auditory;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed product budget program bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ManagedProductBudgetProgramBean extends AManagedBean<ProductBudgetProgram, IProductBudgetProgramService> implements ILoadable {

    @ManagedProperty(value = "#{productBudgetProgramService}")
    private IProductBudgetProgramService mainService;
    @ManagedProperty(value = "#{budgetProgramService}")
    private IBudgetProgramService budgetProgramService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{managedBudgetProgramBean}")
    private ManagedBudgetProgramBean managedBudgetProgramBean;
    
    private List<BudgetProgram> budgetPrograms;
    private BudgetProgramSearcher budgetProgramSearcher;
    private String code;
    private String name;
    private BudgetProgram budgetProgram;

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        budgetProgramSearcher = new BudgetProgramSearcher();
    }
    
    @Override
    public void onLoad(boolean allowAjax) {
    }

    @Override
    public boolean save() {
        if (mainService.existCode(code,selected.getId())) {
            new SmartMessage("Codigo Invalido", "El codigo '"+code+"' ya pertenece a otro producto/proyecto presupuestario", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un producto/proyecto presupuestario" : "Se ha creado un producto/proyecto presupuestario";
            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return result;
        }
    }
    
    @Override
    protected void fillFields() {
        code = selected.getCode();
        name = selected.getName();
        createAgain = selected.getId() == null;
        if(selected.getId() != null){
            AliasList aliasList = new AliasList();
            aliasList.add("productBudgetPrograms", "pbp");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("pbp.id", selected.getId()));
            budgetPrograms = getBudgetProgramService().addRestrictionsVariant(criterionList,aliasList);
        }else{
            budgetPrograms = new ArrayList();
        }
        budgetProgramSearcher.update();
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        selected.setBudgetPrograms(new ArrayList());
        for(BudgetProgram item : budgetProgramSearcher.data){
            if(budgetProgramSearcher.selecteds.get(item.getId())){
                selected.getBudgetPrograms().add(item);
            }
        }
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * @return the mainService
     */
    @Override
    public IProductBudgetProgramService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IProductBudgetProgramService mainService) {
        this.mainService = mainService;
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
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets budget program service.
     *
     * @return the budgetProgramService
     */
    public IBudgetProgramService getBudgetProgramService() {
        return budgetProgramService;
    }

    /**
     * Sets budget program service.
     *
     * @param budgetProgramService the budgetProgramService to set
     */
    public void setBudgetProgramService(IBudgetProgramService budgetProgramService) {
        this.budgetProgramService = budgetProgramService;
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    /**
     * @param navigationBean the navigationBean to set
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Gets budget program searcher.
     *
     * @return the budgetProgramSearcher
     */
    public BudgetProgramSearcher getBudgetProgramSearcher() {
        return budgetProgramSearcher;
    }

    /**
     * Sets budget program searcher.
     *
     * @param budgetProgramSearcher the budgetProgramSearcher to set
     */
    public void setBudgetProgramSearcher(BudgetProgramSearcher budgetProgramSearcher) {
        this.budgetProgramSearcher = budgetProgramSearcher;
    }

    /**
     * Gets managed budget program bean.
     *
     * @return the managedBudgetProgramBean
     */
    public ManagedBudgetProgramBean getManagedBudgetProgramBean() {
        return managedBudgetProgramBean;
    }

    /**
     * Sets managed budget program bean.
     *
     * @param managedBudgetProgramBean the managedBudgetProgramBean to set
     */
    public void setManagedBudgetProgramBean(ManagedBudgetProgramBean managedBudgetProgramBean) {
        this.managedBudgetProgramBean = managedBudgetProgramBean;
    }

    /**
     * The type Budget program searcher.
     */
    public class BudgetProgramSearcher implements java.io.Serializable {

        private List<BudgetProgram> data;
        private Map<Integer,Boolean>  selecteds;

        /**
         * Update.
         */
        public void update() {
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            data = getBudgetProgramService().addRestrictionsVariant(Arrays.asList(criterionList, orderList));
            selecteds = new HashMap();
            for(BudgetProgram item : data){
                selecteds.put(item.getId(), budgetPrograms.contains(item));
            }
            for(BudgetProgram item : budgetPrograms){
                if(!data.contains(item)){
                    data.add(item);
                    selecteds.put(item.getId(), true);
                }
            }
        }

        /**
         * Gets data.
         *
         * @return the data
         */
        public List<BudgetProgram> getData() {
            return data;
        }

        /**
         * Sets data.
         *
         * @param data the data to set
         */
        public void setData(List<BudgetProgram> data) {
            this.data = data;
        }

        /**
         * Gets selecteds.
         *
         * @return the selecteds
         */
        public Map<Integer,Boolean> getSelecteds() {
            return selecteds;
        }

        /**
         * Sets selecteds.
         *
         * @param selecteds the selecteds to set
         */
        public void setSelecteds(Map<Integer,Boolean> selecteds) {
            this.selecteds = selecteds;
        }
        
    }
}

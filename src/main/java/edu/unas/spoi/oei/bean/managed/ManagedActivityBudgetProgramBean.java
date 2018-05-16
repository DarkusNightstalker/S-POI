/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.managed;

import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.bean.ActivityBudgetProgramBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.model.UoM;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IUoMService;
import edu.unas.spoi.util.SmartMessage;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed activity budget program bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ManagedActivityBudgetProgramBean extends AManagedBean<ActivityBudgetProgram, IActivityBudgetProgramService> implements ILoadable {

    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService mainService;
    @ManagedProperty(value = "#{productBudgetProgramService}")
    private IProductBudgetProgramService productBudgetProgramService;
    @ManagedProperty(value = "#{budgetProgramService}")
    private IBudgetProgramService budgetProgramService;
    @ManagedProperty(value = "#{uomService}")
    private IUoMService uomService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{activityBudgetProgramBean}")
    private ActivityBudgetProgramBean activityBudgetProgramBean;
    @ManagedProperty(value = "#{managedProductBudgetProgramBean}")
    private ManagedProductBudgetProgramBean managedProductBudgetProgramBean;
    @ManagedProperty(value = "#{managedBudgetProgramBean}")
    private ManagedBudgetProgramBean managedBudgetProgramBean;
    @ManagedProperty(value = "#{managedUoMBean}")
    private ManagedUoMBean managedUoMBean;

    private ProductBudgetProgramSearcher productBudgetProgramSearcher;
    private BudgetProgramSearcher budgetProgramSearcher;
    private UoMSearcher uomSearcher;

    private String code;
    private String name;
    private ProductBudgetProgram productBudgetProgram;
    private BudgetProgram budgetProgram;
    private String function;
    private String functionalDivision;
    private String functionalGroup;
    private String goal;
    private String functionalSequence;
    private UoM uom;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        productBudgetProgramSearcher = new ProductBudgetProgramSearcher();
        budgetProgramSearcher = new BudgetProgramSearcher();
        uomSearcher = new UoMSearcher();
    }

    @Override
    public void onLoad(boolean allowAjax) {
    }

    @Override
    public boolean save() {
        if (mainService.existCode(code.trim(), selected.getId())) {
            new SmartMessage("Codigo Invalido", "El codigo '" + code + "' ya pertenece a otra actividad presupuestal", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado una actividad presupuestal" : "Se ha creado una actividad presupuestal";
            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return result;
        }
    }

    @Override
    protected void fillFields() {
        code = selected.getCode();
        name = selected.getName();
        function = selected.getFunction();
        functionalDivision = selected.getFunctionalDivision();
        functionalGroup = selected.getFunctionalGroup();
        goal = selected.getGoal();
        createAgain = true;
        functionalSequence = selected.getFunctionalSequence();
        if (selected.getId() != null) {
            
            budgetProgram = (BudgetProgram) mainService.listHQL("SELECT abp.budgetProgram FROM ActivityBudgetProgram abp WHERE abp.id = " + selected.getId()).get(0);
            productBudgetProgram = (ProductBudgetProgram) mainService.listHQL("SELECT abp.productBudgetProgram FROM ActivityBudgetProgram abp WHERE abp.id = " + selected.getId()).get(0);
            try {
                uom = (UoM) mainService.listHQL("SELECT abp.uom FROM ActivityBudgetProgram abp WHERE abp.id = " + selected.getId()).get(0);
            } catch (Exception e) {
                uom = null;
            }
        } else {
            productBudgetProgram = null;
            budgetProgram = null;
            uom = null;
        }
        budgetProgramSearcher.update();
        productBudgetProgramSearcher.update();
        uomSearcher.update();
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {

        selected.setCode(code.trim());
        selected.setName(name.trim());

        selected.setFunction(function.trim().length() == 0 ? null : function.trim());
        selected.setFunctionalDivision(functionalDivision.trim().length() == 0 ? null : functionalDivision.trim());
        selected.setFunctionalGroup(functionalGroup.trim().length() == 0 ? null : functionalGroup.trim());
        selected.setGoal(goal.trim().length() == 0 ? null : goal.trim());
        selected.setFunctionalSequence(functionalSequence.trim().length() == 0 ? null : functionalSequence.trim());

        selected.setBudgetProgram(budgetProgram);
        selected.setProductBudgetProgram(productBudgetProgram);
        selected.setUom(uom);
        Auditory.make(selected, sessionBean.getCurrentUser());
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
     * Gets uom service.
     *
     * @return the uomService
     */
    public IUoMService getUomService() {
        return uomService;
    }

    /**
     * Sets uom service.
     *
     * @param uomService the uomService to set
     */
    public void setUomService(IUoMService uomService) {
        this.uomService = uomService;
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
     * Gets managed product budget program bean.
     *
     * @return the managedProductBudgetProgramBean
     */
    public ManagedProductBudgetProgramBean getManagedProductBudgetProgramBean() {
        return managedProductBudgetProgramBean;
    }

    /**
     * Sets managed product budget program bean.
     *
     * @param managedProductBudgetProgramBean the managedProductBudgetProgramBean to set
     */
    public void setManagedProductBudgetProgramBean(ManagedProductBudgetProgramBean managedProductBudgetProgramBean) {
        this.managedProductBudgetProgramBean = managedProductBudgetProgramBean;
    }

    /**
     * Gets managed uo m bean.
     *
     * @return the managedUoMBean
     */
    public ManagedUoMBean getManagedUoMBean() {
        return managedUoMBean;
    }

    /**
     * Sets managed uo m bean.
     *
     * @param managedUoMBean the managedUoMBean to set
     */
    public void setManagedUoMBean(ManagedUoMBean managedUoMBean) {
        this.managedUoMBean = managedUoMBean;
    }

    /**
     * Gets product budget program searcher.
     *
     * @return the productBudgetProgramSearcher
     */
    public ProductBudgetProgramSearcher getProductBudgetProgramSearcher() {
        return productBudgetProgramSearcher;
    }

    /**
     * Sets product budget program searcher.
     *
     * @param productBudgetProgramSearcher the productBudgetProgramSearcher to set
     */
    public void setProductBudgetProgramSearcher(ProductBudgetProgramSearcher productBudgetProgramSearcher) {
        this.productBudgetProgramSearcher = productBudgetProgramSearcher;
    }

    /**
     * Gets uom searcher.
     *
     * @return the uomSearcher
     */
    public UoMSearcher getUomSearcher() {
        return uomSearcher;
    }

    /**
     * Sets uom searcher.
     *
     * @param uomSearcher the uomSearcher to set
     */
    public void setUomSearcher(UoMSearcher uomSearcher) {
        this.uomSearcher = uomSearcher;
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
     * Gets function.
     *
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets function.
     *
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * Gets functional division.
     *
     * @return the functionalDivision
     */
    public String getFunctionalDivision() {
        return functionalDivision;
    }

    /**
     * Sets functional division.
     *
     * @param functionalDivision the functionalDivision to set
     */
    public void setFunctionalDivision(String functionalDivision) {
        this.functionalDivision = functionalDivision;
    }

    /**
     * Gets functional group.
     *
     * @return the functionalGroup
     */
    public String getFunctionalGroup() {
        return functionalGroup;
    }

    /**
     * Sets functional group.
     *
     * @param functionalGroup the functionalGroup to set
     */
    public void setFunctionalGroup(String functionalGroup) {
        this.functionalGroup = functionalGroup;
    }

    /**
     * Gets goal.
     *
     * @return the goal
     */
    public String getGoal() {
        return goal;
    }

    /**
     * Sets goal.
     *
     * @param goal the goal to set
     */
    public void setGoal(String goal) {
        this.goal = goal;
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
     * @return the navigationBean
     */
    @Override
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    /**
     * @param navigationBean the navigationBean to set
     */
    @Override
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Gets activity budget program bean.
     *
     * @return the activityBudgetProgramBean
     */
    public ActivityBudgetProgramBean getActivityBudgetProgramBean() {
        return activityBudgetProgramBean;
    }

    /**
     * Sets activity budget program bean.
     *
     * @param activityBudgetProgramBean the activityBudgetProgramBean to set
     */
    public void setActivityBudgetProgramBean(ActivityBudgetProgramBean activityBudgetProgramBean) {
        this.activityBudgetProgramBean = activityBudgetProgramBean;
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
     * Gets functional sequence.
     *
     * @return the functionalSequence
     */
    public String getFunctionalSequence() {
        return functionalSequence;
    }

    /**
     * Sets functional sequence.
     *
     * @param functionalSequence the functionalSequence to set
     */
    public void setFunctionalSequence(String functionalSequence) {
        this.functionalSequence = functionalSequence;
    }

    /**
     * The type Budget program searcher.
     */
    public class BudgetProgramSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private boolean saved;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            data = getBudgetProgramService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }

        /**
         * Change.
         */
        public void change() {
            setProductBudgetProgram(null);
            getProductBudgetProgramSearcher().update();
        }

        /**
         * Save and select.
         */
        public void saveAndSelect() {
            saved = getManagedBudgetProgramBean().save();
            if (saved) {
                update();
                setBudgetProgram(getManagedBudgetProgramBean().getSelected());
            }
        }

        /**
         * Refresh.
         */
        public void refresh() {
            getManagedProductBudgetProgramBean().create();
            saved = false;
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Integer id) {
            try {
                setBudgetProgram(getBudgetProgramService().getById(id));
            } catch (Exception e) {
                setProductBudgetProgram(null);
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            try {
                return getBudgetProgram().getId();
            } catch (Exception e) {
                return null;
            }
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
         * Is saved boolean.
         *
         * @return the saved
         */
        public boolean isSaved() {
            return saved;
        }

        /**
         * Sets saved.
         *
         * @param saved the saved to set
         */
        public void setSaved(boolean saved) {
            this.saved = saved;
        }
    }

    /**
     * The type Product budget program searcher.
     */
    public class ProductBudgetProgramSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private boolean saved;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            data = getProductBudgetProgramService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }

        /**
         * Save and select.
         */
        public void saveAndSelect() {
            saved = getManagedProductBudgetProgramBean().save();
            if (saved) {
                update();
                setProductBudgetProgram(getManagedProductBudgetProgramBean().getSelected());
            }
        }

        /**
         * Refresh.
         */
        public void refresh() {
            getManagedProductBudgetProgramBean().create();
            saved = false;
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Long id) {
            try {
                setProductBudgetProgram(getProductBudgetProgramService().getById(id));
            } catch (Exception e) {
                setProductBudgetProgram(null);
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Long getSelected() {
            try {
                return getProductBudgetProgram().getId();
            } catch (Exception e) {
                return null;
            }
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
         * Is saved boolean.
         *
         * @return the saved
         */
        public boolean isSaved() {
            return saved;
        }

        /**
         * Sets saved.
         *
         * @param saved the saved to set
         */
        public void setSaved(boolean saved) {
            this.saved = saved;
        }
    }

    /**
     * The type Uo m searcher.
     */
    public class UoMSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private boolean saved;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            data = getUomService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }

        /**
         * Save and select.
         */
        public void saveAndSelect() {
            saved = getManagedUoMBean().save();
            if (saved) {
                update();
                setUom(getManagedUoMBean().getSelected());
            }
        }

        /**
         * Refresh.
         */
        public void refresh() {
            getManagedUoMBean().create();
            saved = false;
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Integer id) {
            try {
                setUom(getUomService().getById(id));
            } catch (Exception e) {
                setUom(null);
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            try {
                return getUom().getId();
            } catch (Exception e) {
                return null;
            }
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
         * Is saved boolean.
         *
         * @return the saved
         */
        public boolean isSaved() {
            return saved;
        }

        /**
         * Sets saved.
         *
         * @param saved the saved to set
         */
        public void setSaved(boolean saved) {
            this.saved = saved;
        }
    }
}

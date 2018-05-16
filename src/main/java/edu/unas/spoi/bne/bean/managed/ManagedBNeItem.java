/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.bean.managed;

import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bne.model.BNeItem;
import edu.unas.spoi.bne.model.BNeSchedule;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
import edu.unas.spoi.bne.service.interfac.IBNeScheduleService;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.bne.bean.view.ViewBNeedBean;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.poi.service.interfac.IPOIActivityService;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.IClassifierTypeService;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import edu.unas.spoi.ppto.service.interfac.INecessaryItemService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import lombok.Data;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed b ne item.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ManagedBNeItem extends AManagedBean<BNeItem, IBNeItemService> implements java.io.Serializable {

    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{necessaryItemService}")
    private INecessaryItemService necessaryItemService;
    @ManagedProperty(value = "#{classifierTypeService}")
    private IClassifierTypeService classifierTypeService;
    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService fundingSourceService;
    @ManagedProperty(value = "#{budgetCeilingService}")
    private IBudgetCeilingService budgetCeilingService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{poiActivityService}")
    private IPOIActivityService poiActivityService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{bneItemService}")
    private IBNeItemService mainService;
    @ManagedProperty(value = "#{bneScheduleService}")
    private IBNeScheduleService bneScheduleService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{viewBNeedBean}")
    private ViewBNeedBean viewBNeedBean;

    private StrategicPlan strategicPlan;

    private Dependency dependency;
    private Classifier classifier;
    private FundingSource fundingSource;
    private NecessaryItem necessaryItem;
    private POIActivity poiActivity;
    private BudgetCeiling budgetCeiling;
    private Map<Month, BNeSchedule> schedules;
    private List<Month> months;
    private String productCode;
    private String productName;
    private Double unitPrice;

    private Double quantityTotal;

    private NecessaryItemSearcher necessaryItemSearcher;
    private GenericClassifierSearcher genericClassifierSearcher;
    private POIActivitySearcher poiActivitySearcher;
    private FundingSourceSearcher fundingSourceSearcher;

    private Double realAmount;
    private BigDecimal currentAmount;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        fundingSourceSearcher = new FundingSourceSearcher();
        necessaryItemSearcher = new NecessaryItemSearcher();
        genericClassifierSearcher = new GenericClassifierSearcher();
        poiActivitySearcher = new POIActivitySearcher();
        months = Arrays.asList(Month.values());
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado una actividad del POI" : "Se ha creado una actividad del POI";
        boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
        for (Month month : this.months) {
            BNeSchedule item = this.schedules.get(month);
            if (item.getQuantity() == null) {
                item.setQuantity(0.0);
            }
            bneScheduleService.saveOrUpdate(item);
        }
        new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        return result;
    }

    @Override
    public void doSave(String page, ILoadable loadable) {
        if (save()) {
            if (!createAgain) {
                setKeepManaged(false);
                setSelected(null);
            } else {
                setKeepManaged(true);
                super.create();
            }
        }
        viewBNeedBean.updateItems();
    }

    @Override
    public void create() {
        poiActivity = null;
        fundingSource = null;
        genericClassifierSearcher.selected = null;
        super.create();
    }

    /**
     * Gets current balance.
     *
     * @return the current balance
     */
    public BigDecimal getCurrentBalance() {
        try {
            BigDecimal value = budgetCeiling.getQuantity().setScale(2, RoundingMode.HALF_UP).subtract(currentAmount);
            return value;
        } catch (NullPointerException npe) {
            return BigDecimal.ZERO.setScale(2);
        }
    }

    @Override
    protected void fillFields() {
        dependency = viewBNeedBean.getCurrentDependency();
        classifier = selected.getClassifier();
        genericClassifierSearcher.update();

        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        this.months = Arrays.asList(Month.values());
        this.schedules = new EnumMap(Month.class);

        if (selected.getId() != null) {
            genericClassifierSearcher.setSelected(selected.getClassifier().getPath().substring(0, 2));
            necessaryItemSearcher.setGenericPath(genericClassifierSearcher.getSelected());
            for (Month month : this.months) {
                BNeSchedule schedule = bneScheduleService.getBy(month, selected.getId());
                if (schedule == null) {
                    schedule = new BNeSchedule();
                    schedule.setBneItem(selected);
                    schedule.setMonth(month);
                    schedule.setQuantity(0.0);
                }
                this.schedules.put(month, schedule);
            }
            fundingSource = selected.getFundingSource();
            poiActivity = selected.getPoiActivity();
        } else {
            for (Month month : this.months) {
                BNeSchedule schedule = new BNeSchedule();
                schedule.setMonth(month);
                schedule.setBneItem(selected);
                this.schedules.put(month, schedule);
            }
        }
        createAgain = selected.getId() == null;
        necessaryItemSearcher.update();
        necessaryItemSearcher.changedSpecific();
        changePOIFunding();
        necessaryItem = selected.getNecessaryItem();
        fundingSourceSearcher.update();
        poiActivitySearcher.update();
    }

    @Override
    protected void clearFields() {
        classifier = null;
        fundingSource = null;
        necessaryItemSearcher.changedSpecific();
    }

    @Override
    protected void fillSelected() {
        selected.setClassifier(necessaryItem.getClassifier());
        selected.setFundingSource(fundingSource);
        selected.setNecessaryItem(necessaryItem);
        selected.setPoiActivity(poiActivity);
        selected.setProductCode(necessaryItem.getCode());
        selected.setProductName(necessaryItem.getName());
        selected.setUnitPrice(necessaryItem.getUnitPrice());

        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * Change poi funding.
     */
    public void changePOIFunding() {
        if (fundingSource != null && poiActivity != null && genericClassifierSearcher.selected != null && genericClassifierSearcher.selected.length() != 0) {
            AliasList aliasList = new AliasList();
            aliasList.add("classifier", "c");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("c.path", genericClassifierSearcher.selected));
            criterionList.add(Restrictions.eq("fundingSource", fundingSource));
            criterionList.add(Restrictions.eq("dependency", poiActivity.getPoi().getDependency()));
            criterionList.add(Restrictions.eq("year", sessionBean.getOperationYear()));
            try {
                budgetCeiling = (BudgetCeiling) budgetCeilingService.addRestrictionsVariant(criterionList, aliasList).get(0);
            } catch (Exception e) {
                new SmartMessage("INCONSISTENCIA", "No se ha encontrado un techo presupuestario adecuado", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shaked animated").execute();
                return;
            }
            BigDecimal currentAmount = BigDecimal.ZERO;
            ProjectionList projectionLisr = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("unitPrice"));
            aliasList = new AliasList();
            aliasList.add("poiActivity", "poia");
            aliasList.add("classifier", "c");
            aliasList.add("poia.poi", "poi");
            criterionList.clear();
            criterionList.add(Restrictions.like("c.path", genericClassifierSearcher.selected, MatchMode.START));
            criterionList.add(Restrictions.eq("fundingSource", fundingSource));
            criterionList.add(Restrictions.eq("poi.dependency", poiActivity.getPoi().getDependency()));
            criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
            criterionList.add(Restrictions.eq("active", true));
            if (selected.getId() != null) {
                criterionList.add(Restrictions.ne("id", selected.getId()));
            }
            List<Object[]> data = mainService.addRestrictionsVariant(projectionLisr, aliasList, criterionList);
            aliasList = new AliasList();
            aliasList.add("bneItem", "bi");
            for (Object[] item : data) {
                projectionLisr = Projections.projectionList()
                        .add(Projections.sum("quantity"));
                criterionList.clear();
                criterionList.add(Restrictions.eq("bi.id", item[0]));
                Double i = ((Double) bneScheduleService.addRestrictionsVariant(projectionLisr, criterionList, aliasList).get(0));
                currentAmount = currentAmount.add(new BigDecimal(((Number) item[1]).doubleValue()).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(i)));
            }
            this.currentAmount = currentAmount.setScale(2, RoundingMode.HALF_UP);
        } else {
            budgetCeiling = null;
        }
    }

    /**
     * Gets price item.
     *
     * @return the price item
     */
    public BigDecimal getPriceItem() {
        try {
            return necessaryItem.getUnitPrice();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets price item.
     *
     * @param princeItem the prince item
     */
    public void setPriceItem(Double princeItem) {
    }

    /**
     * Gets schedules.
     *
     * @return the schedules
     */
    public Map<Month, BNeSchedule> getSchedules() {
        return schedules;
    }

    /**
     * Sets schedules.
     *
     * @param schedules the schedules to set
     */
    public void setSchedules(Map<Month, BNeSchedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * Gets months.
     *
     * @return the months
     */
    public List<Month> getMonths() {
        return months;
    }

    /**
     * Sets months.
     *
     * @param months the months to set
     */
    public void setMonths(List<Month> months) {
        this.months = months;
    }

    @Override
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    @Override
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    @Override
    public SessionBean getSessionBean() {
        return this.sessionBean;
    }

    @Override
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @Override
    public IBNeItemService getMainService() {
        return mainService;
    }

    @Override
    public void setMainService(IBNeItemService mainService) {
        this.mainService = mainService;
    }

    /**
     * Gets budget ceiling.
     *
     * @return the budgetCeiling
     */
    public BudgetCeiling getBudgetCeiling() {
        return budgetCeiling;
    }

    /**
     * Sets budget ceiling.
     *
     * @param budgetCeiling the budgetCeiling to set
     */
    public void setBudgetCeiling(BudgetCeiling budgetCeiling) {
        this.budgetCeiling = budgetCeiling;
    }

    /**
     * Gets classifier service.
     *
     * @return the classifierService
     */
    public IClassifierService getClassifierService() {
        return classifierService;
    }

    /**
     * Sets classifier service.
     *
     * @param classifierService the classifierService to set
     */
    public void setClassifierService(IClassifierService classifierService) {
        this.classifierService = classifierService;
    }

    /**
     * Gets necessary item service.
     *
     * @return the necessaryItemService
     */
    public INecessaryItemService getNecessaryItemService() {
        return necessaryItemService;
    }

    /**
     * Sets necessary item service.
     *
     * @param necessaryItemService the necessaryItemService to set
     */
    public void setNecessaryItemService(INecessaryItemService necessaryItemService) {
        this.necessaryItemService = necessaryItemService;
    }

    /**
     * Gets classifier type service.
     *
     * @return the classifierTypeService
     */
    public IClassifierTypeService getClassifierTypeService() {
        return classifierTypeService;
    }

    /**
     * Sets classifier type service.
     *
     * @param classifierTypeService the classifierTypeService to set
     */
    public void setClassifierTypeService(IClassifierTypeService classifierTypeService) {
        this.classifierTypeService = classifierTypeService;
    }

    /**
     * Gets funding source service.
     *
     * @return the fundingSourceService
     */
    public IFundingSourceService getFundingSourceService() {
        return fundingSourceService;
    }

    /**
     * Sets funding source service.
     *
     * @param fundingSourceService the fundingSourceService to set
     */
    public void setFundingSourceService(IFundingSourceService fundingSourceService) {
        this.fundingSourceService = fundingSourceService;
    }

    /**
     * Gets budget ceiling service.
     *
     * @return the budgetCeilingService
     */
    public IBudgetCeilingService getBudgetCeilingService() {
        return budgetCeilingService;
    }

    /**
     * Sets budget ceiling service.
     *
     * @param budgetCeilingService the budgetCeilingService to set
     */
    public void setBudgetCeilingService(IBudgetCeilingService budgetCeilingService) {
        this.budgetCeilingService = budgetCeilingService;
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
     * Gets poi activity service.
     *
     * @return the poiActivityService
     */
    public IPOIActivityService getPoiActivityService() {
        return poiActivityService;
    }

    /**
     * Sets poi activity service.
     *
     * @param poiActivityService the poiActivityService to set
     */
    public void setPoiActivityService(IPOIActivityService poiActivityService) {
        this.poiActivityService = poiActivityService;
    }

    /**
     * Sets classifier.
     *
     * @param classifier the classifier to set
     */
    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    /**
     * Sets funding source.
     *
     * @param fundingSource the fundingSource to set
     */
    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    /**
     * Sets necessary item.
     *
     * @param necessaryItem the necessaryItem to set
     */
    public void setNecessaryItem(NecessaryItem necessaryItem) {
        this.necessaryItem = necessaryItem;
    }

    /**
     * Sets poi activity.
     *
     * @param poiActivity the poiActivity to set
     */
    public void setPoiActivity(POIActivity poiActivity) {
        this.poiActivity = poiActivity;
    }

    /**
     * Gets necessary item searcher.
     *
     * @return the necessaryItemSearcher
     */
    public NecessaryItemSearcher getNecessaryItemSearcher() {
        return necessaryItemSearcher;
    }

    /**
     * Sets necessary item searcher.
     *
     * @param necessaryItemSearcher the necessaryItemSearcher to set
     */
    public void setNecessaryItemSearcher(NecessaryItemSearcher necessaryItemSearcher) {
        this.necessaryItemSearcher = necessaryItemSearcher;
    }

    /**
     * Gets generic classifier searcher.
     *
     * @return the genericClassifierSearcher
     */
    public GenericClassifierSearcher getGenericClassifierSearcher() {
        return genericClassifierSearcher;
    }

    /**
     * Sets generic classifier searcher.
     *
     * @param genericClassifierSearcher the genericClassifierSearcher to set
     */
    public void setGenericClassifierSearcher(GenericClassifierSearcher genericClassifierSearcher) {
        this.genericClassifierSearcher = genericClassifierSearcher;
    }

    /**
     * Gets poi activity searcher.
     *
     * @return the poiActivitySearcher
     */
    public POIActivitySearcher getPoiActivitySearcher() {
        return poiActivitySearcher;
    }

    /**
     * Sets poi activity searcher.
     *
     * @param poiActivitySearcher the poiActivitySearcher to set
     */
    public void setPoiActivitySearcher(POIActivitySearcher poiActivitySearcher) {
        this.poiActivitySearcher = poiActivitySearcher;
    }

    /**
     * Gets funding source searcher.
     *
     * @return the fundingSourceSearcher
     */
    public FundingSourceSearcher getFundingSourceSearcher() {
        return fundingSourceSearcher;
    }

    /**
     * Sets funding source searcher.
     *
     * @param fundingSourceSearcher the fundingSourceSearcher to set
     */
    public void setFundingSourceSearcher(FundingSourceSearcher fundingSourceSearcher) {
        this.fundingSourceSearcher = fundingSourceSearcher;
    }

    /**
     * Gets real amount.
     *
     * @return the realAmount
     */
    public Double getRealAmount() {
        return realAmount;
    }

    /**
     * Sets real amount.
     *
     * @param realAmount the realAmount to set
     */
    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * Gets classifier.
     *
     * @return the classifier
     */
    public Classifier getClassifier() {
        return classifier;
    }

    /**
     * Gets poi activity.
     *
     * @return the poiActivity
     */
    public POIActivity getPoiActivity() {
        return poiActivity;
    }

    /**
     * Gets funding source.
     *
     * @return the fundingSource
     */
    public FundingSource getFundingSource() {
        return fundingSource;
    }

    /**
     * Gets necessary item.
     *
     * @return the necessaryItem
     */
    public NecessaryItem getNecessaryItem() {
        return necessaryItem;
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

    /**
     * Gets strategic axis service.
     *
     * @return the strategicAxisService
     */
    public IStrategicAxisService getStrategicAxisService() {
        return strategicAxisService;
    }

    /**
     * Sets strategic axis service.
     *
     * @param strategicAxisService the strategicAxisService to set
     */
    public void setStrategicAxisService(IStrategicAxisService strategicAxisService) {
        this.strategicAxisService = strategicAxisService;
    }

    /**
     * Gets bne schedule service.
     *
     * @return the bneScheduleService
     */
    public IBNeScheduleService getBneScheduleService() {
        return bneScheduleService;
    }

    /**
     * Sets bne schedule service.
     *
     * @param bneScheduleService the bneScheduleService to set
     */
    public void setBneScheduleService(IBNeScheduleService bneScheduleService) {
        this.bneScheduleService = bneScheduleService;
    }

    /**
     * Gets strategic plan.
     *
     * @return the strategicPlan
     */
    public StrategicPlan getStrategicPlan() {
        return strategicPlan;
    }

    /**
     * Sets strategic plan.
     *
     * @param strategicPlan the strategicPlan to set
     */
    public void setStrategicPlan(StrategicPlan strategicPlan) {
        this.strategicPlan = strategicPlan;
    }

    /**
     * Gets dependency.
     *
     * @return the dependency
     */
    public Dependency getDependency() {
        return dependency;
    }

    /**
     * Sets dependency.
     *
     * @param dependency the dependency to set
     */
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    /**
     * Gets product code.
     *
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets product code.
     *
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Gets product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets unit price.
     *
     * @return the unitPrice
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets unit price.
     *
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets quantity total.
     *
     * @return the quantityTotal
     */
    public Double getQuantityTotal() {
        return quantityTotal;
    }

    /**
     * Sets quantity total.
     *
     * @param quantityTotal the quantityTotal to set
     */
    public void setQuantityTotal(Double quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    /**
     * Gets view b need bean.
     *
     * @return the viewBNeedBean
     */
    public ViewBNeedBean getViewBNeedBean() {
        return viewBNeedBean;
    }

    /**
     * Sets view b need bean.
     *
     * @param viewBNeedBean the viewBNeedBean to set
     */
    public void setViewBNeedBean(ViewBNeedBean viewBNeedBean) {
        this.viewBNeedBean = viewBNeedBean;
    }

    /**
     * Gets current amount.
     *
     * @return the currentAmount
     */
    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Sets current amount.
     *
     * @param currentAmount the currentAmount to set
     */
    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * The type Generic classifier searcher.
     */
    public class GenericClassifierSearcher implements java.io.Serializable {

        private List<Object[]> data;

        private String selected;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("path"))
                    .add(Projections.property("name"));
            AliasList aliasList = new AliasList();
            aliasList.add("classifierType", "c");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("c.codeDigit", new Short("2")));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("name"));
            data = getClassifierService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList, orderList));
        }

        /**
         * Changed generic.
         */
        public void changedGeneric() {
            necessaryItemSearcher.setGenericPath(selected);
            necessaryItemSearcher.update();
            necessaryItemSearcher.changedSpecific();
            changePOIFunding();
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
         * Gets selected.
         *
         * @return the selected
         */
        public String getSelected() {
            return selected;
        }

        /**
         * Sets selected.
         *
         * @param selected the selected to set
         */
        public void setSelected(String selected) {
            this.selected = selected;
        }
    }

    /**
     * The type Necessary item searcher.
     */
    public class NecessaryItemSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private String filter;
        private String genericPath;
        private String classifierInfo;

        /**
         * Update.
         */
        public void update() {
            if (genericPath == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            if (genericPath.isEmpty()) {
                data = Collections.EMPTY_LIST;
                return;
            }
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            AliasList aliasList = new AliasList();
            aliasList.add("classifier", "c");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.like("c.path", genericPath, MatchMode.START));
            if (filter != null && filter.length() != 0) {
                criterionList.add(Restrictions.like("name", filter, MatchMode.ANYWHERE).ignoreCase());
            }
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("name"));
            data = necessaryItemService.addRestrictionsVariant(aliasList, criterionList, projectionList, orderList);
        }

        /**
         * Gets selected.
         *
         * @return the necessaryItem
         */
        public Integer getSelected() {
            try {
                return getNecessaryItem().getId();
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Sets selected.
         *
         * @param selected the selected
         */
        public void setSelected(Integer selected) {
            try {
                setNecessaryItem(getNecessaryItemService().getById(selected));
            } catch (Exception e) {
                setNecessaryItem(null);
            }
        }

        /**
         * Changed specific.
         */
        public void changedSpecific() {
            filter = "";
            filterItems();
        }

        /**
         * Filter items.
         */
        public void filterItems() {
            necessaryItem = null;
            necessaryItemSearcher.update();
            changedItem();
        }

        /**
         * Changed item.
         */
        public void changedItem() {
            classifierInfo = "";
            if (necessaryItem != null) {
                Stack<String> htmlStack = new Stack();
                makeInfo(htmlStack, necessaryItem.getClassifier().getPath());
                while (!htmlStack.isEmpty()) {
                    classifierInfo = classifierInfo + htmlStack.pop();
                }
            }
        }

        private void makeInfo(Stack<String> htmlStack, String path) {
            Object[] info = getClassifierService().getParentInfo(path);
            if (info != null) {
                htmlStack.push(
                        "     <strong>" + info[2] + " : </strong>"
                        + "     <label> (" + info[1] + ")&nbsp;" + info[3] + "</label>.");
                makeInfo(htmlStack, (String) info[1]);
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
         * Gets filter.
         *
         * @return the filter
         */
        public String getFilter() {
            return filter;
        }

        /**
         * Sets filter.
         *
         * @param filter the filter to set
         */
        public void setFilter(String filter) {
            this.filter = filter;
        }

        /**
         * Gets generic path.
         *
         * @return the genericPath
         */
        public String getGenericPath() {
            return genericPath;
        }

        /**
         * Sets generic path.
         *
         * @param genericPath the genericPath to set
         */
        public void setGenericPath(String genericPath) {
            this.genericPath = genericPath;
        }

        /**
         * Gets classifier info.
         *
         * @return the classifierInfo
         */
        public String getClassifierInfo() {
            return classifierInfo;
        }

        /**
         * Sets classifier info.
         *
         * @param classifierInfo the classifierInfo to set
         */
        public void setClassifierInfo(String classifierInfo) {
            this.classifierInfo = classifierInfo;
        }
    }

    /**
     * The type Funding source searcher.
     */
    @Data
    public class FundingSourceSearcher implements java.io.Serializable {

        private List<Object[]> data;

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

            data = getFundingSourceService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }

        /**
         * Gets selected.
         *
         * @return the fundingSource
         */
        public Integer getSelected() {
            try {
                return getFundingSource().getId();
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Sets selected.
         *
         * @param selected the selected
         */
        public void setSelected(Integer selected) {
            try {
                setFundingSource(getFundingSourceService().getById(selected));
            } catch (Exception e) {
                setFundingSource(null);
            }
        }
    }

    /**
     * The type Poi activity searcher.
     */
    @Data
    public class POIActivitySearcher implements java.io.Serializable {

        // private List<Object[]> data;
        private List<SelectItemGroup> data;

        /**
         * Update.
         */
        public void update() {
            if (getDependency() == null) {
                data = Collections.EMPTY_LIST;
                return;
            }

            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("sa.id")))
                    .add(Projections.property("sa.code"))
                    .add(Projections.property("sa.name"));
            dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("poi", "poi");
            aliasList.add("specificActivity", "sac");
            aliasList.add("sac.specificGoal", "spg");
            aliasList.add("spg.strategicGoal", "stg");
            aliasList.add("stg.strategicAxis", "sa");
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("poi.dependency", getDependency()));
            criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
            criterionList.add(Restrictions.eq("sa.strategicPlan", getStrategicPlan()));
            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("sa.code"));
            List<Object[]> groups = poiActivityService.getListByRestrictions(projectionList, criterionList, orderList, aliasList);

            projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("detail"));
            aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("poi", "poi");
            aliasList.add("specificActivity", "sac");
            aliasList.add("sac.specificGoal", "spg");
            aliasList.add("spg.strategicGoal", "stg");
            aliasList.add("stg.strategicAxis", "sa");

            orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("detail"));
            data = new ArrayList();
            for (Object[] group : groups) {
                SelectItemGroup selectItemGroup = new SelectItemGroup((String) group[2], (String) group[1], false, new SelectItem[]{});
                criterionList.clear();
                criterionList = new dn.core3.hibernate.CriterionList();
                criterionList.add(Restrictions.eq("active", true));
                criterionList.add(Restrictions.eq("poi.dependency", getDependency()));
                criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
                criterionList.add(Restrictions.eq("sa.id", group[0]));
                List<Object[]> items = poiActivityService.getListByRestrictions(projectionList, criterionList, orderList, aliasList);
                List<SelectItem> selectItems = new ArrayList(items.size());
                for (Object[] item : items) {
                    selectItems.add(new SelectItem(item[0], item[1].toString()));
                }
                selectItemGroup.setSelectItems(selectItems.toArray(new SelectItem[]{}));

                data.add(selectItemGroup);
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Long getSelected() {
            try {
                return getPoiActivity().getId();
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Sets selected.
         *
         * @param selected the selected
         */
        public void setSelected(Long selected) {
            try {
                setPoiActivity(getPoiActivityService().getById(selected));
            } catch (Exception e) {
                setPoiActivity(null);
            }
        }

    }

    /**
     * The type Reports.
     */
    public class Reports implements java.io.Serializable {

    }
}

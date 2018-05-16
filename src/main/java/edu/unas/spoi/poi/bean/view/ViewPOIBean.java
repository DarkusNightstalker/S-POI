/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.bean.view;

import dn.core3.util.Pagination;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bne.bean.managed.ManagedBNeItem;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.oei.service.interfac.ISpecificActivityService;
import edu.unas.spoi.oei.service.interfac.ISpecificGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.poi.model.POI;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.poi.service.interfac.IPOIActivityService;
import edu.unas.spoi.poi.service.interfac.IPOIScheduleService;
import edu.unas.spoi.poi.service.interfac.IPOIService;
import edu.unas.spoi.poi.service.interfac.IPOIUnityService;
import edu.unas.spoi.poi.service.interfac.IPeriodicityItemService;
import edu.unas.spoi.poi.service.interfac.IPeriodicityService;
import edu.unas.spoi.report.StaticReport;
import edu.unas.spoi.report.enumerated.ContentType;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderFactory;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AViewBean;
import gkfire.web.util.BeanUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.jasperreports.engine.JRExporter;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type View poi bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
@EqualsAndHashCode(callSuper = true)
public class ViewPOIBean extends AViewBean<POI, Long, IPOIService> {

    @ManagedProperty(value = "#{poiService}")
    private IPOIService mainService;
    @ManagedProperty(value = "#{poiActivityService}")
    private IPOIActivityService poiActivityService;
    @ManagedProperty(value = "#{poiScheduleService}")
    private IPOIScheduleService poiScheduleService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{specificGoalService}")
    private ISpecificGoalService specificGoalService;
    @ManagedProperty(value = "#{poiUnityService}")
    private IPOIUnityService poiUnityService;
    @ManagedProperty(value = "#{specificActivityService}")
    private ISpecificActivityService specificActivityService;
    @ManagedProperty(value = "#{periodicityService}")
    private IPeriodicityService periodicityService;
    @ManagedProperty(value = "#{periodicityItemService}")
    private IPeriodicityItemService periodicityItemService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{managedBNeItem}")
    private ManagedBNeItem managedBNeItem;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private Integer currentDependencyId;
    private Report report;
    private StrategicAxisSearcher strategicAxisSearcher;
    private SpecificActivitySearcher specificActivitySearcher;
    private MetaSearcher metaSearcher;
    private POIUnitySearcher poiUnitySearcher;
    private String summaryWrapper;
    private Long selectedId;
    private List<PeriodicityItem> quarters;
    private StrategicPlan strategicPlan;
    private Pagination<Object[]> pagination;
    private OrderFactory orderFactory;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        permissionDisabled = "";
        report = new Report();
        strategicAxisSearcher = new StrategicAxisSearcher();
        specificActivitySearcher = new SpecificActivitySearcher();
        metaSearcher = new MetaSearcher();
        poiUnitySearcher = new POIUnitySearcher();

        orderFactory = new OrderFactory(new OrderList());
        orderFactory.setDefaultOrder(Order.asc("id"));

        pagination = new Pagination() {
            @Override
            protected Long countTotalRecords() {
                return poiActivityService.countListForMainView(
                        selected.getId(),
                        strategicAxisSearcher.selected,
                        metaSearcher.selected,
                        specificActivitySearcher.selected,
                        poiUnitySearcher.selected,
                        !sessionBean.authorize("R_APOI") ? true : null);

            }

            @Override
            protected List searchRecords(int page, int recordsPerPage) {
                return poiActivityService.getListLazyForMainView(
                        page,
                        recordsPerPage,
                        selected.getId(),
                        quarters,
                        strategicAxisSearcher.selected,
                        metaSearcher.selected,
                        specificActivitySearcher.selected,
                        poiUnitySearcher.selected,
                        !sessionBean.authorize("R_APOI") ? true : null,
                        null);
            }
        };
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        begin(currentDependencyId);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        strategicAxisSearcher.selected = null;
        specificActivitySearcher.selected = null;
        metaSearcher.selected = null;
        poiUnitySearcher.selected = null;
        updateActivities();
        strategicAxisSearcher.update();
        specificActivitySearcher.update();
        metaSearcher.update();
        poiUnitySearcher.update();
    }

    /**
     * Begin.
     *
     * @param dependencyId the dependency id
     */
    public void begin(Integer dependencyId) {
        currentDependencyId = dependencyId;
        selected = mainService.getBy(sessionBean.getOperationYear(), dependencyId);

        if (selected == null) {
            selected = new POI();
            selected.setDependency(dependencyService.getById(dependencyId));
            selected.setYear(sessionBean.getOperationYear());
            if (sessionBean.getOperationYear() <= 2017) {
                selected.setPeriodicity(periodicityService.getById((short) 1));
            } else {
                selected.setPeriodicity(periodicityService.getById((short) 2));
            }
            Auditory.make(selected, sessionBean.getCurrentUser());
            mainService.saveOrUpdate(selected);
        }
        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());

        strategicAxisSearcher.selected = null;
        specificActivitySearcher.selected = null;
        metaSearcher.selected = null;
        poiUnitySearcher.selected = null;

        updateActivities();

        strategicAxisSearcher.update();
        specificActivitySearcher.update();
        metaSearcher.update();
        poiUnitySearcher.update();
    }

    @Override
    protected void update(boolean widthDisabled) {
        //super.update(widthDisabled); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Update activities.
     */
    public void updateActivities() {
        
        quarters = periodicityItemService.getListBy(selected.getPeriodicity());
        pagination.search(1);
        summaryWrapper = "";
        if (strategicAxisSearcher.selected != null) {
            summaryWrapper = "Eje estr.";
            if (metaSearcher.selected != null) {
                summaryWrapper += summaryWrapper.length() == 0 ? "Meta" : " / Meta";
            }
            if (specificActivitySearcher.selected != null) {
                summaryWrapper = summaryWrapper.length() == 0 ? "Actividad Especifica" : " / Actividad Especifica";
            }
        }
        if (poiUnitySearcher.selected != null) {
            summaryWrapper = summaryWrapper.length() == 0 ? "Unidad POI" : " / Unidad POI";
        }
        if (summaryWrapper.length() == 0) {
            summaryWrapper = "<i>Ningún filtro</i>";
        } else {
            summaryWrapper = "<strong>Filtrado por :</strong> " + summaryWrapper;
        }
    }

    /**
     * The type Report.
     */
    public class Report implements java.io.Serializable {

        private List<StaticReport> staticReports;
        private StaticReport selected;
        private String parameters;
        private ContentType contentType;

        /**
         * Instantiates a new Report.
         */
        public Report() {
            staticReports = new ArrayList<>();
            StaticReport report = new StaticReport("1", "Plan Operativo-Informes y Estado", "POIActivity.jasper");
            staticReports.add(report);
            report = new StaticReport("2", "Plan Operativo - Programación financiera", "/poi/financial-programming/operative-plan-financial-programming.jasper") {
                @Override
                public Map<String, Object> getBasicParameters() {
                    Map<String, Object> params = super.getBasicParameters();
                    params.put("PERIODICITY-VALUE", StaticReport.loadFromWebPages("/poi/financial-programming/operative-plan-financial-programming-periodicity-value.jasper"));
                    params.put("PERIODICITY-H", StaticReport.loadFromWebPages("/poi/financial-programming/operative-plan-financial-programming-periodicity-h.jasper"));
                    params.put("UNAS", ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                    params.put("id_dependency", ViewPOIBean.this.selected.getDependency().getId());
                    params.put("year", sessionBean.getOperationYear());
                    return params;
                }
            };
            staticReports.add(report);
            report = new StaticReport("3", "Plan Operativo - Programación fisica", "/poi/physical-programming/operative-plan-physical-programming.jasper") {
                @Override
                public Map<String, Object> getBasicParameters() {
                    Map<String, Object> params = super.getBasicParameters();
                    params.put("PERIODICITY-VALUE", StaticReport.loadFromWebPages("/poi/physical-programming/operative-plan-physical-programming-periodicity-value.jasper"));
                    params.put("PERIODICITY-H", StaticReport.loadFromWebPages("/poi/physical-programming/operative-plan-physical-programming-periodicity-h.jasper"));
                    params.put("UNAS", ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                    params.put("id_dependency", ViewPOIBean.this.selected.getDependency().getId());
                    params.put("year", sessionBean.getOperationYear());
                    return params;
                }
            };
            staticReports.add(report);
        }

        /**
         * Refresh.
         */
        public void refresh() {
            selected = null;
            contentType = null;
            parameters = null;
        }

        /**
         * Execute string.
         *
         * @return the string
         */
        public String execute() {
            Map<String, Object> map = selected.getBasicParameters();
            map.put("dependency_id", ViewPOIBean.this.selected.getDependency().getId());
            map.put("dependency_code", ViewPOIBean.this.selected.getDependency().getCode());
            map.put("dependency_name", ViewPOIBean.this.selected.getDependency().getName());
            map.put("year", ViewPOIBean.this.selected.getYear());
            String[] maps = parameters.split(";");
            for (String m : maps) {
                String[] entry = m.split("=");
                if (entry.length == 1) {
                    map.put(entry[0], null);
                } else {
                    map.put(entry[0], entry[1]);
                }
            }
            return selected.execute((JRExporter) contentType.getExporter(), map, contentType, getSessionBean());
        }

        /**
         * Sets selected code.
         *
         * @param code the code
         */
        public void setSelectedCode(String code) {
            for (StaticReport report : staticReports) {
                if (Objects.equals(report.getCode(), code)) {
                    selected = report;
                    return;
                }
            }
            selected = null;
        }

        /**
         * Gets selected code.
         *
         * @return the selected code
         */
        public String getSelectedCode() {
            try {
                return selected.getCode();
            } catch (NullPointerException npe) {
                return null;
            }
        }

        /**
         * Sets content type.
         *
         * @param c the c
         */
        public void setContentType(String c) {
            try {
                contentType = ContentType.valueOf(c.toUpperCase());
            } catch (Exception e) {

            }
        }

        /**
         * Gets content type.
         *
         * @return the content type
         */
        public String getContentType() {
            try {
                return contentType.name();
            } catch (NullPointerException npe) {
                return null;
            }
        }

        /**
         * Gets static reports.
         *
         * @return the staticReports
         */
        public List<StaticReport> getStaticReports() {
            return staticReports;
        }

        /**
         * Sets static reports.
         *
         * @param staticReports the staticReports to set
         */
        public void setStaticReports(List<StaticReport> staticReports) {
            this.staticReports = staticReports;
        }

        /**
         * Gets parameters.
         *
         * @return the parameters
         */
        public String getParameters() {
            return parameters;
        }

        /**
         * Sets parameters.
         *
         * @param parameters the parameters to set
         */
        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public StaticReport getSelected() {
            return selected;
        }

        /**
         * Sets selected.
         *
         * @param selected the selected to set
         */
        public void setSelected(StaticReport selected) {
            this.selected = selected;
        }
    }

    /**
     * The type Meta searcher.
     */
//<editor-fold defaultstate="collapsed" desc="Searchers">
    @Data
    public class MetaSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Long selected;

        /**
         * Update.
         */
        public void update() {
//            if (getSpecificGoalSearcher().selected == null) {
            data = Collections.EMPTY_LIST;
//                return;
//            }
//            ProjectionList projectionsList = Projections.projectionList()
//                    .add(Projections.property("id"))
//                    .add(Projections.property("functionalSequence"));
//            AliasList aliasList = new AliasList();
//            aliasList.add("strategicActivities", "sg");
//            aliasList.add("sg.dependencies", "d");
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//            criterionList.add(Restrictions.eq("sg.id", getSpecificGoalSearcher().selected));
//            criterionList.add(Restrictions.eq("d.id", getSessionBean().getCurrentDependency().getId()));
//
//            OrderList orderList = new OrderList();
//            orderList.add(Order.asc("code"));
//            setData((List<Object[]>) getActivityBudgetProgramService().addRestrictionsVariant(aliasList, criterionList, projectionsList, orderList));
        }
    }

    /**
     * The type Strategic axis searcher.
     */
    @Data
    public class StrategicAxisSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Integer selected;

        /**
         * Update.
         */
        public void update() {
            if (getStrategicPlan() == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            ProjectionList projectionsList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("sa.id")))
                    .add(Projections.property("sa.code"))
                    .add(Projections.property("sa.name"));
            dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("specificActivity", "sac");
            aliasList.add("sac.specificGoal", "sg");
            aliasList.add("sg.strategicGoal", "st");
            aliasList.add("st.strategicAxis", "sa");
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("sa.strategicPlan", getStrategicPlan()));
            criterionList.add(Restrictions.eq("poi", ViewPOIBean.this.selected));

            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("sa.code"));
            setData((List<Object[]>) poiActivityService.getListByRestrictions(aliasList, criterionList, projectionsList, orderList));
        }

        /**
         * Change.
         */
        public void change() {
            updateActivities();
        }
    }

    /**
     * The type Specific activity searcher.
     */
    @Data
    public class SpecificActivitySearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Integer selected;

        /**
         * Update.
         */
        public void update() {
            if (getSessionBean().getCurrentDependency() == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("specificActivity", "sac");
            aliasList.add("sac.activityBudgetPrograms", "abp");
            aliasList.add("sac.specificGoal", "sg");
            aliasList.add("sg.strategicGoal", "st");
            aliasList.add("st.strategicAxis", "sa");
            aliasList.add("abp.dependencies", "d");
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("sa.id", strategicAxisSearcher.selected));
            criterionList.add(Restrictions.eq("d.id", getSessionBean().getCurrentDependency().getId()));
            criterionList.add(Restrictions.eq("poi", ViewPOIBean.this.selected));
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("sac.id")))
                    .add(Projections.property("sac.code"))
                    .add(Projections.property("sac.name"));
            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("sac.code"));
            data = poiActivityService.getListByRestrictions(aliasList, projectionList, criterionList, orderList);
        }
    }

    /**
     * The type Poi unity searcher.
     */
    @Data
    public class POIUnitySearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Integer selected;

        /**
         * Update.
         */
        public void update() {
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("id")))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            data = getPoiUnityService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }
    }

//</editor-fold>
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.bean.managed;

import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.util.AManagedBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.SpecificActivity;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.oei.service.interfac.ISpecificActivityService;
import edu.unas.spoi.oei.service.interfac.ISpecificGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.poi.bean.view.ViewPOIBean;
import edu.unas.spoi.poi.model.POI;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.poi.model.POISchedule;
import edu.unas.spoi.poi.model.POIUnity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.poi.service.interfac.IPOIActivityService;
import edu.unas.spoi.poi.service.interfac.IPOIScheduleService;
import edu.unas.spoi.poi.service.interfac.IPOIService;
import edu.unas.spoi.poi.service.interfac.IPOIUnityService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed poi bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
@EqualsAndHashCode(callSuper = true)
public class ManagedPOIBean extends AManagedBean<POIActivity, IPOIActivityService> implements java.io.Serializable {

    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{specificActivityService}")
    private ISpecificActivityService specificActivityService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{poiScheduleService}")
    private IPOIScheduleService poiScheduleService;
    @ManagedProperty(value = "#{poiService}")
    private IPOIService poiService;
    @ManagedProperty(value = "#{poiUnityService}")
    private IPOIUnityService poiUnityService;
    @ManagedProperty(value = "#{poiActivityService}")
    private IPOIActivityService poiActivityService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{viewPOIBean}")
    private ViewPOIBean viewPOIBean;

    private DependencySearcher dependencySearcher;
    private ABPSearcher abpSearcher;
    private SpecificGoalSearcher specificGoalSearcher;
    private SpecificActivitySearcher specificActivitySearcher;
    private POIUnitySearcher poiUnitySearcher;

    private POI poi;
    private Dependency dependencyResponsible;
    private ActivityBudgetProgram activityBudgetProgram;
    private SpecificActivity specificActivity;
    private String detail;
    private Short priority;
    private Map<Integer, POISchedule> schedules;
    private POIUnity poiUnity;
    private Boolean allowInfoDependency;
    private Object[] infoDependency;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        dependencySearcher = new DependencySearcher();
        abpSearcher = new ABPSearcher();
        specificActivitySearcher = new SpecificActivitySearcher();
        poiUnitySearcher = new POIUnitySearcher();

        specificGoalSearcher = new SpecificGoalSearcher();
    }

    @Override
    protected void fillFields() {
        if (!keepManaged) {
            this.dependencyResponsible = selected.getResponsibleDependency();
            this.activityBudgetProgram = selected.getActivityBudgetProgram();
            this.specificActivity = selected.getSpecificActivity();
            this.poi = selected.getPoi();
            this.poiUnity = selected.getPoiUnity();
        }
        if (this.poi == null) {
            this.poi = viewPOIBean.getSelected();
        }
        this.detail = selected.getDetail();
        this.priority = selected.getPriority();
        createAgain = selected.getId() == null;
        if (selected.getId() == null) {
            this.schedules = new HashMap();
            for (PeriodicityItem periodicityItem : viewPOIBean.getQuarters()) {
                POISchedule schedule = new POISchedule();
                schedule.setPeriodicityItem(periodicityItem);
                schedule.setPoiActivity(selected);
                this.schedules.put(periodicityItem.getId(), schedule);
            }
            this.specificGoalSearcher.selected = null;
        } else {
            this.specificGoalSearcher.selected = selected.getSpecificActivity().getSpecificGoal().getId();
            this.schedules = new HashMap();
            for (PeriodicityItem periodicityItem : viewPOIBean.getQuarters()) {
                POISchedule schedule = poiScheduleService.getBy(periodicityItem.getId(), selected.getId());
                if (schedule == null) {
                    schedule = new POISchedule();
                    schedule.setPoiActivity(selected);
                    schedule.setQuantity(0);
                    schedule.setPeriodicityItem(periodicityItem);
                }
                this.schedules.put(periodicityItem.getId(), schedule);
            }
        }
        allowInfoDependency = null;
        changeDependencyRegister();

        poiUnitySearcher.update();
    }

    /**
     * Update info dependency.
     */
    public void updateInfoDependency() {
//        Integer currentYear = sessionBean.getOperationYear();
//        AliasList aliasList = new AliasList();
//        aliasList.add("strategicGoal", "sg");
//        aliasList.add("sg.strategicAxis", "sa");
//        aliasList.add("sa.strategicPlan", "sp");
//        aliasList.add("dependencies", "d");
//        CriterionList criterionList = new CriterionList();
//        criterionList.add(Restrictions.eq("active", true));
//        criterionList.add(Restrictions.ge("sp.endYear", currentYear));
//        criterionList.add(Restrictions.le("sp.startYear", currentYear));
//        criterionList.add(Restrictions.eq("d.id", poi.getDependency().getId()));
//        ProjectionList projectionList = Projections.projectionList()
//                .add(Projections.property("sa.name"))
//                .add(Projections.property("sg.name"))
//                .add(Projections.property("name"));
//        List<Object[]> data = specificGoalService.addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList));
//        if (!data.isEmpty()) {
//            infoDependency = data.get(0);
//            allowInfoDependency = true;
//        } else {
//            allowInfoDependency = false;
//            infoDependency = null;
//        }
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setPoi(this.poi);
        selected.setPriority(priority);

        //selected.setResponsibleDependency(this.dependencyResponsible);
        selected.setActivityBudgetProgram(this.activityBudgetProgram);
        selected.setSpecificActivity(this.specificActivity);
        selected.setDetail(this.detail.trim());
        selected.setPoiUnity(poiUnity);
        selected.setResponsibleDependency(this.poi.getDependency());
        for (PeriodicityItem periodicityItem : viewPOIBean.getQuarters()) {
            POISchedule schedule = this.schedules.get(periodicityItem.getId());
            Integer quantity = schedule.getQuantity();
            if (quantity == null) {
                quantity = 0;
            }
            schedule.setQuantity(quantity);
        }
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado una actividad del POI" : "Se ha creado una actividad del POI";
        if (poi.getId() == null) {
            Auditory.make(poi, sessionBean.getCurrentUser());
            poiService.saveOrUpdate(poi);
        }
        if (selected.getId() == null) {
            selected.setCode(poiActivityService.getNextCode(poi.getId()));
        }
        boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.

        for (PeriodicityItem periodicityItem : viewPOIBean.getQuarters()) {
            poiScheduleService.saveOrUpdate(this.schedules.get(periodicityItem.getId()));
        }
        new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        return result;
    }

    @Override
    public void doSave(String page, ILoadable loadable) {
        //onLoad(true);
        if (save()) {
            if (!createAgain) {
                setKeepManaged(false);
                setSelected(null);
            } else {
                setKeepManaged(true);
                create();
            }
        }
        viewPOIBean.updateActivities();

    }

    /**
     * Change dependency register.
     */
    public void changeDependencyRegister() {
        dependencySearcher.update();
        specificGoalSearcher.setDependency(poi == null ? null : poi.getDependency());
        specificGoalSearcher.update();
        abpSearcher.setDependency(poi == null ? null : poi.getDependency());
        abpSearcher.update();
        specificActivitySearcher.setDependency(poi == null ? null : poi.getDependency());
        specificActivitySearcher.update();

        if (poi != null && poi.getDependency() != null) {
            updateInfoDependency();
        }
    }

    @Override
    public IPOIActivityService getMainService() {
        return getPoiActivityService();
    }

    @Override
    public void setMainService(IPOIActivityService mainService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets dependency creator.
     *
     * @return the dependencyCreator
     */
    public Integer getDependencyCreator() {
        try {
            return this.poi.getDependency().getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets dependency creator.
     *
     * @param dependencyCreator the dependencyCreator to set
     */
    public void setDependencyCreator(Integer dependencyCreator) {
        try {
            updatePOI(dependencyCreator);
        } catch (Exception e) {
            this.poi = null;
        }
    }

    /**
     * Update poi.
     *
     * @param dependencyId the dependency id
     */
    public void updatePOI(Integer dependencyId) {
        AliasList aliasList = new AliasList();
        aliasList.add("dependency", "d");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("year", sessionBean.getOperationYear()));
        criterionList.add(Restrictions.eq("d.id", dependencyId));
        List<POI> pois = getPoiService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList));
        if (pois.isEmpty()) {
            poi = new POI();
            poi.setDependency(getDependencyService().getById(dependencyId));
            poi.setYear(sessionBean.getOperationYear());
        } else {
            poi = pois.get(0);
        }
    }

    /**
     * Gets dependency responsible.
     *
     * @return the dependencyResponsible
     */
    public Integer getDependencyResponsible() {
        try {
            return this.dependencyResponsible.getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets dependency responsible.
     *
     * @param dependencyResponsible the dependencyResponsible to set
     */
    public void setDependencyResponsible(Integer dependencyResponsible) {
        try {
            this.dependencyResponsible = getDependencyService().getById(dependencyResponsible);
        } catch (Exception e) {
            this.dependencyResponsible = null;
        }
    }

    /**
     * Gets activity budget program.
     *
     * @return the activityBudgetProgram
     */
    public Long getActivityBudgetProgram() {
        try {
            return this.activityBudgetProgram.getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets activity budget program.
     *
     * @param activityBudgetProgram the activityBudgetProgram to set
     */
    public void setActivityBudgetProgram(Long activityBudgetProgram) {
        try {
            this.activityBudgetProgram = getActivityBudgetProgramService().getById(activityBudgetProgram);
        } catch (Exception e) {
            this.dependencyResponsible = null;
        }
    }

    /**
     * Gets specific activity.
     *
     * @return the specificActivity
     */
    public Integer getSpecificActivity() {
        try {
            return this.specificActivity.getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets specific activity.
     *
     * @param specificActivity the specificActivity to set
     */
    public void setSpecificActivity(Integer specificActivity) {
        try {
            this.specificActivity = getSpecificActivityService().getById(specificActivity);
        } catch (Exception e) {
            this.specificActivity = null;
        }
    }

    /**
     * Gets poi unity.
     *
     * @return the poiUnity
     */
    public Integer getPoiUnity() {
        try {
            return poiUnity.getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets poi unity.
     *
     * @param poiUnity the poiUnity to set
     */
    public void setPoiUnity(Integer poiUnity) {
        try {
            this.poiUnity = poiUnityService.getById(poiUnity);
        } catch (Exception e) {
            this.poiUnity = null;
        }

    }

    /**
     * The type Dependency searcher.
     */
    @Data
    public class DependencySearcher implements java.io.Serializable {

        private List<Object[]> data;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("path"))
                    .add(Projections.property("name"));
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("operational", true));
            if (!sessionBean.isSuperAdmin()) {
                criterionList.add(Restrictions.like("path", getSessionBean().getCurrentDependency().getPath(), MatchMode.START));
            }
            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
            orderList.add(Order.asc("path"));
            data = getDependencyService().getListByRestrictions(projectionList, criterionList, orderList);
        }

        /**
         * Make code string.
         *
         * @param code the code
         * @return the string
         */
        public String makeCode(String code) {
            return StringUtils.rightPad(code + " ", 15, ".");
        }
    }

    /**
     * The type Specific goal searcher.
     */
    @Data
    public class SpecificGoalSearcher implements java.io.Serializable {

        private List<SelectItemGroup> data;
        private Integer selected;
        private Dependency dependency;

        /**
         * Update.
         */
        public void update() {
            if (dependency == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            AliasList aliasList = new AliasList();
            aliasList.add("specificGoal", "sg");
            aliasList.add("sg.strategicGoal", "st");
            aliasList.add("st.strategicAxis", "sa");
            aliasList.add("sa.strategicPlan", "sp");
            aliasList.add("activityBudgetPrograms", "abp");
            aliasList.add("abp.dependencies", "d");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.ge("sp.endYear", getSessionBean().getOperationYear()));
            criterionList.add(Restrictions.le("sp.startYear", getSessionBean().getOperationYear()));
            criterionList.add(Restrictions.eq("d.id", dependency.getId()));

            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("sa.id")))
                    .add(Projections.property("sa.code"))
                    .add(Projections.property("sa.name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("sa.code"));
            data = new ArrayList();
            List<Object[]> groups = specificActivityService.addRestrictionsVariant(aliasList, criterionList, projectionList, orderList);
            projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("sg.id")))
                    .add(Projections.property("sg.name"));

            for (Object[] group : groups) {
                SelectItemGroup selectItemGroup = new SelectItemGroup((String) group[2], (String) group[1], false, new SelectItem[]{});
                criterionList.clear();
                criterionList.add(Restrictions.eq("active", true));
                criterionList.add(Restrictions.ge("sp.startYear", getSessionBean().getOperationYear()));
                criterionList.add(Restrictions.le("sp.endYear", getSessionBean().getOperationYear()));
                criterionList.add(Restrictions.eq("d.id", dependency.getId()));
                criterionList.add(Restrictions.eq("sa.id", group[0]));
                List<Object[]> items = specificActivityService.addRestrictionsVariant(aliasList, criterionList, projectionList);
                List<SelectItem> selectItems = new ArrayList(items.size());
                for (Object[] item : items) {
                    selectItems.add(new SelectItem(item[0], item[1].toString()));
                }
                selectItemGroup.setSelectItems(selectItems.toArray(new SelectItem[]{}));
                data.add(selectItemGroup);
            }
        }

        /**
         * Change.
         */
        public void change() {
            activityBudgetProgram = null;
            abpSearcher.update();
            specificActivity = null;
            specificActivitySearcher.update();
        }

    }

    /**
     * The type Poi unity searcher.
     */
    @Data
    public class POIUnitySearcher implements java.io.Serializable {

        private List<Object[]> data;

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
            orderList.add(Order.asc("name"));
            data = getPoiUnityService().addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
        }
    }

    /**
     * The type Abp searcher.
     */
    @Data
    public class ABPSearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Dependency dependency;

        /**
         * Update.
         */
        public void update() {
            if (dependency == null || specificGoalSearcher.getSelected() == null || specificActivity == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            AliasList aliasList = new AliasList();
            aliasList.add("dependencies", "d");
            aliasList.add("strategicActivities", "sa");
            aliasList.add("sa.specificGoal", "sg");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("sa.id", specificActivity.getId()));
            criterionList.add(Restrictions.eq("sg.id", getSpecificGoalSearcher().getSelected()));
            criterionList.add(Restrictions.eq("d.id", dependency.getId()));
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("id")))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            data = getActivityBudgetProgramService().addRestrictionsVariant(Arrays.asList(aliasList, projectionList, criterionList, orderList));
        }

    }

    /**
     * The type Specific activity searcher.
     */
    @Data
    public class SpecificActivitySearcher implements java.io.Serializable {

        private List<Object[]> data;
        private Dependency dependency;

        /**
         * Update.
         */
        public void update() {
            if (dependency == null || getSpecificGoalSearcher().getSelected() == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            AliasList aliasList = new AliasList();
            aliasList.add("specificGoal", "sg");
            aliasList.add("activityBudgetPrograms", "abp");
            aliasList.add("abp.dependencies", "d");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("sg.id", specificGoalSearcher.getSelected()));
            criterionList.add(Restrictions.eq("d.id", dependency.getId()));
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.distinct(Projections.property("id")))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            data = getSpecificActivityService().addRestrictionsVariant(Arrays.asList(aliasList, projectionList, criterionList, orderList));
        }
    }
}

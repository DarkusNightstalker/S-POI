/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * The type Poi activity bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class POIActivityBean implements java.io.Serializable {

//    @ManagedProperty(value = "#{dependencyService}")
//    private IDependencyService dependencyService;
//    @ManagedProperty(value = "#{strategicPlanService}")
//    private IStrategicPlanService strategicPlanService;
//    @ManagedProperty(value = "#{activityBudgetProgramService}")
//    private IActivityBudgetProgramService activityBudgetProgramService;
//    @ManagedProperty(value = "#{specificActivityService}")
//    private ISpecificActivityService specificActivityService;
//    @ManagedProperty(value = "#{specificGoalService}")
//    private ISpecificGoalService specificGoalService;
//    @ManagedProperty(value = "#{poiScheduleService}")
//    private IPOIScheduleService poiScheduleService;
//    @ManagedProperty(value = "#{poiService}")
//    private IPOIService poiService;
//    @ManagedProperty(value = "#{poiUnityService}")
//    private IPOIUnityService poiUnityService;
//    @ManagedProperty(value = "#{poiActivityService}")
//    private IPOIActivityService poiActivityService;
//    @ManagedProperty(value = "#{sessionBean}")
//    private SessionBean sessionBean;
//
//    private DependencySearcher dependencySearcher;
//    private ABPSearcher abpSearcher;
//    private SpecificActivitySearcher specificActivitySearcher;
//    private POIUnitySearcher poiUnitySearcher;
//    private Managed managed;
//
//    private List<Object[]> data;
//    private Integer dependencyId;
//    private Integer activityBudgetProgramId;
//    private Integer specificActivityId;
//    private String searchName;
//
//    @PostConstruct
//    public void init() {
//        dependencySearcher = new DependencySearcher();
//        abpSearcher = new ABPSearcher();
//        specificActivitySearcher = new SpecificActivitySearcher();
//        managed = new Managed();
//        poiUnitySearcher = new POIUnitySearcher();
//    }
//
//    @Override
//    public void onLoad(boolean allowAjax) {
//        if (BeanUtil.isAjaxRequest() && !allowAjax) {
//            return;
//        }
//        managed.setKeepManaged(false);
//        dependencySearcher.update();
//        abpSearcher.update();
//        managed.setSelected(null);
//        specificActivitySearcher.update();
//        if (managed.getSelected() != null) {
//            try {
//                managed.updatePOI(managed.getPoi().getDependency().getId());
//            } catch (Exception e) {
//                managed.setPoi(null);
//            }
//        }
//        refresh();
//    }
//
//    public void refresh() {
//        dependencyId = sessionBean.getCurrentDependency() == null ? null : sessionBean.getCurrentDependency().getId();
//        activityBudgetProgramId = null;
//        specificActivityId = null;
//        searchName = "";
//        search();
//    }
//
//    public void search() {
//        searchName = searchName.trim();
//        ProjectionList projectionList = Projections.projectionList()
//                .add(Projections.property("id"))
//                .add(Projections.property("code"))
//                .add(Projections.property("detail"))
//                .add(Projections.property("createDate"))
//                .add(Projections.property("editDate"))
//                .add(Projections.property("id"))
//                .add(Projections.property("id"))
//                .add(Projections.property("sa.code"))
//                .add(Projections.property("sa.name"))
//                .add(Projections.property("st.code"))
//                .add(Projections.property("st.name"))
//                .add(Projections.property("sg.code"))
//                .add(Projections.property("sg.name"))
//                .add(Projections.property("sac.code"))
//                .add(Projections.property("sac.name"))
//                .add(Projections.property("abp.code"))
//                .add(Projections.property("abp.name"))
//                .add(Projections.property("abp.functionalSequence"))
//                .add(Projections.property("pu.code"))
//                .add(Projections.property("pu.name"))
//                .add(Projections.property("active"));
//
//        AliasList aliasList = new AliasList();
//        aliasList.add("poi", "poi");
//        aliasList.add("poi.dependency", "d");
//        aliasList.add("poiUnity", "pu");
//        aliasList.add("specificActivity", "sac");
//        aliasList.add("sac.specificGoal", "sg");
//        aliasList.add("sg.strategicGoal", "st");
//        aliasList.add("st.strategicAxis", "sa");
//        aliasList.add("activityBudgetProgram", "abp");
//
//        CriterionList criterionList = new CriterionList();
//        criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
//        if (searchName.length() != 0) {
//            criterionList.add(Restrictions.like("detail", searchName, MatchMode.ANYWHERE));
//        }
//        if (dependencyId != null) {
//            criterionList.add(Restrictions.eq("d.id", dependencyId));
//        }
//        if (activityBudgetProgramId != null) {
//            criterionList.add(Restrictions.eq("abp.id", activityBudgetProgramId));
//        }
//        if (specificActivityId != null) {
//            criterionList.add(Restrictions.eq("sac.id", specificActivityId));
//        }
//        OrderList orderList = new OrderList();
//        orderList.add(Order.desc("id"));
//        data = poiActivityService.addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList, orderList));
//        aliasList.clear();
//        aliasList.add("poiActivity", "poia");
//        for (Object[] item : data) {
//            Map<Quarter, Integer> map = new HashMap();
//            projectionList = Projections.projectionList()
//                    .add(Projections.property("quantity"));
//            Integer total = 0;
//            for (Quarter quarter : quarters) {
//                criterionList.clear();
//                criterionList.add(Restrictions.eq("poia.id", item[0]));
//                criterionList.add(Restrictions.eq("quarter", quarter));
//                Integer current = (Integer) poiScheduleService.addRestrictionsVariant(Arrays.asList(criterionList, projectionList, aliasList)).get(0);
//                total += current;
//                map.put(quarter, current);
//            }
//            item[5] = map;
//            item[6] = total;
//        }
//    }
//
//    /**
//     * @return the dependencyService
//     */
//    public IDependencyService getDependencyService() {
//        return dependencyService;
//    }
//
//    /**
//     * @param dependencyService the dependencyService to set
//     */
//    public void setDependencyService(IDependencyService dependencyService) {
//        this.dependencyService = dependencyService;
//    }
//
//    /**
//     * @return the strategicPlanService
//     */
//    public IStrategicPlanService getStrategicPlanService() {
//        return strategicPlanService;
//    }
//
//    /**
//     * @param strategicPlanService the strategicPlanService to set
//     */
//    public void setStrategicPlanService(IStrategicPlanService strategicPlanService) {
//        this.strategicPlanService = strategicPlanService;
//    }
//
//    /**
//     * @return the activityBudgetProgramService
//     */
//    public IActivityBudgetProgramService getActivityBudgetProgramService() {
//        return activityBudgetProgramService;
//    }
//
//    /**
//     * @param activityBudgetProgramService the activityBudgetProgramService to
//     * set
//     */
//    public void setActivityBudgetProgramService(IActivityBudgetProgramService activityBudgetProgramService) {
//        this.activityBudgetProgramService = activityBudgetProgramService;
//    }
//
//    /**
//     * @return the specificActivityService
//     */
//    public ISpecificActivityService getSpecificActivityService() {
//        return specificActivityService;
//    }
//
//    /**
//     * @param specificActivityService the specificActivityService to set
//     */
//    public void setSpecificActivityService(ISpecificActivityService specificActivityService) {
//        this.specificActivityService = specificActivityService;
//    }
//
//    /**
//     * @return the poiScheduleService
//     */
//    public IPOIScheduleService getPoiScheduleService() {
//        return poiScheduleService;
//    }
//
//    /**
//     * @param poiScheduleService the poiScheduleService to set
//     */
//    public void setPoiScheduleService(IPOIScheduleService poiScheduleService) {
//        this.poiScheduleService = poiScheduleService;
//    }
//
//    /**
//     * @return the sessionBean
//     */
//    public SessionBean getSessionBean() {
//        return sessionBean;
//    }
//
//    /**
//     * @param sessionBean the sessionBean to set
//     */
//    public void setSessionBean(SessionBean sessionBean) {
//        this.sessionBean = sessionBean;
//    }
//
//    /**
//     * @return the dependencySearcher
//     */
//    public DependencySearcher getDependencySearcher() {
//        return dependencySearcher;
//    }
//
//    /**
//     * @param dependencySearcher the dependencySearcher to set
//     */
//    public void setDependencySearcher(DependencySearcher dependencySearcher) {
//        this.dependencySearcher = dependencySearcher;
//    }
//
//    /**
//     * @return the abpSearcher
//     */
//    public ABPSearcher getAbpSearcher() {
//        return abpSearcher;
//    }
//
//    /**
//     * @param abpSearcher the abpSearcher to set
//     */
//    public void setAbpSearcher(ABPSearcher abpSearcher) {
//        this.abpSearcher = abpSearcher;
//    }
//
//    /**
//     * @return the specificActivitySearcher
//     */
//    public SpecificActivitySearcher getSpecificActivitySearcher() {
//        return specificActivitySearcher;
//    }
//
//    /**
//     * @param specificActivitySearcher the specificActivitySearcher to set
//     */
//    public void setSpecificActivitySearcher(SpecificActivitySearcher specificActivitySearcher) {
//        this.specificActivitySearcher = specificActivitySearcher;
//    }
//
//    /**
//     * @return the managed
//     */
//    public Managed getManaged() {
//        return managed;
//    }
//
//    /**
//     * @param managed the managed to set
//     */
//    public void setManaged(Managed managed) {
//        this.managed = managed;
//    }
//
//    /**
//     * @return the specificGoalService
//     */
//    public ISpecificGoalService getSpecificGoalService() {
//        return specificGoalService;
//    }
//
//    /**
//     * @param specificGoalService the specificGoalService to set
//     */
//    public void setSpecificGoalService(ISpecificGoalService specificGoalService) {
//        this.specificGoalService = specificGoalService;
//    }
//
//    /**
//     * @return the poiActivityService
//     */
//    public IPOIActivityService getPoiActivityService() {
//        return poiActivityService;
//    }
//
//    /**
//     * @param poiActivityService the poiActivityService to set
//     */
//    public void setPoiActivityService(IPOIActivityService poiActivityService) {
//        this.poiActivityService = poiActivityService;
//    }
//
//    /**
//     * @return the quarters
//     */
//    public List<Quarter> getQuarters() {
//        return quarters;
//    }
//
//    /**
//     * @param quarters the quarters to set
//     */
//    public void setQuarters(List<Quarter> quarters) {
//        this.quarters = quarters;
//    }
//
//    /**
//     * @return the data
//     */
//    public List<Object[]> getData() {
//        return data;
//    }
//
//    /**
//     * @param data the data to set
//     */
//    public void setData(List<Object[]> data) {
//        this.data = data;
//    }
//
//    /**
//     * @return the dependencyId
//     */
//    public Integer getDependencyId() {
//        return dependencyId;
//    }
//
//    /**
//     * @param dependencyId the dependencyId to set
//     */
//    public void setDependencyId(Integer dependencyId) {
//        this.dependencyId = dependencyId;
//    }
//
//    /**
//     * @return the activityBudgetProgramId
//     */
//    public Integer getActivityBudgetProgramId() {
//        return activityBudgetProgramId;
//    }
//
//    /**
//     * @param activityBudgetProgramId the activityBudgetProgramId to set
//     */
//    public void setActivityBudgetProgramId(Integer activityBudgetProgramId) {
//        this.activityBudgetProgramId = activityBudgetProgramId;
//    }
//
//    /**
//     * @return the specificActivityId
//     */
//    public Integer getSpecificActivityId() {
//        return specificActivityId;
//    }
//
//    /**
//     * @param specificActivityId the specificActivityId to set
//     */
//    public void setSpecificActivityId(Integer specificActivityId) {
//        this.specificActivityId = specificActivityId;
//    }
//
//    /**
//     * @return the searchName
//     */
//    public String getSearchName() {
//        return searchName;
//    }
//
//    /**
//     * @param searchName the searchName to set
//     */
//    public void setSearchName(String searchName) {
//        this.searchName = searchName;
//    }
//
//    public void changeDependency() {
//        Dependency d = dependencyService.getById(dependencyId);
//        getDependencySearcher().update();
//        getAbpSearcher().setDependency(d);
//        getAbpSearcher().update();
//        getSpecificActivitySearcher().setDependency(d);
//        getSpecificActivitySearcher().update();
//    }
//
//    /**
//     * @return the poiService
//     */
//    public IPOIService getPoiService() {
//        return poiService;
//    }
//
//    /**
//     * @param poiService the poiService to set
//     */
//    public void setPoiService(IPOIService poiService) {
//        this.poiService = poiService;
//    }
//
//    /**
//     * @return the poiUnityService
//     */
//    public IPOIUnityService getPoiUnityService() {
//        return poiUnityService;
//    }
//
//    /**
//     * @param poiUnityService the poiUnityService to set
//     */
//    public void setPoiUnityService(IPOIUnityService poiUnityService) {
//        this.poiUnityService = poiUnityService;
//    }
//
//    /**
//     * @return the poiUnitySearcher
//     */
//    public POIUnitySearcher getPoiUnitySearcher() {
//        return poiUnitySearcher;
//    }
//
//    /**
//     * @param poiUnitySearcher the poiUnitySearcher to set
//     */
//    public void setPoiUnitySearcher(POIUnitySearcher poiUnitySearcher) {
//        this.poiUnitySearcher = poiUnitySearcher;
//    }
//
//    public class Managed extends AManagedBean<POIActivity, IPOIActivityService> implements java.io.Serializable {
//
//        private POI poi;
//        private Dependency dependencyResponsible;
//        private ActivityBudgetProgram activityBudgetProgram;
//        private SpecificActivity specificActivity;
//        private String detail;
//        private Map<Quarter, POISchedule> schedules;
//        private List<Quarter> quarters;
//        private POIUnity poiUnity;
//        private Boolean allowInfoDependency;
//        private Object[] infoDependency;
//
//        @Override
//        protected void fillFields() {
//            if (!keepManaged) {
//                this.dependencyResponsible = selected.getResponsibleDependency();
//                this.activityBudgetProgram = selected.getActivityBudgetProgram();
//                this.specificActivity = selected.getSpecificActivity();
//                this.poi = selected.getPoi();
//                this.poiUnity = selected.getPoiUnity();
//            }
//            if (this.poi == null && sessionBean.getCurrentDependency() != null) {
//                updatePOI(sessionBean.getCurrentDependency().getId());
//            }
//            if (sessionBean.getCurrentDependency() != null) {
//                this.dependencyResponsible = sessionBean.getCurrentDependency();
//            }
//            this.detail = selected.getDetail();
//            createAgain = selected.getId() == null;
//            if (selected.getId() == null) {
//                this.quarters = Arrays.asList(Quarter.values());
//                this.schedules = new HashMap();
//                for (Quarter quarter : this.quarters) {
//                    POISchedule schedule = new POISchedule();
//                    schedule.setQuarter(quarter);
//                    schedule.setPoiActivity(selected);
//                    this.schedules.put(quarter, schedule);
//                }
//            } else {
//                this.quarters = Arrays.asList(Quarter.values());
//                this.schedules = new HashMap();
//                for (Quarter quarter : this.quarters) {
//                    POISchedule schedule = getPoiScheduleService().getBy(quarter, selected.getId());
//                    this.schedules.put(quarter, schedule);
//                }
//            }
//            allowInfoDependency = null;
//            changeDependencyRegister();
//            poiUnitySearcher.update();
//        }
//
//        public void updateInfoDependency() {
//            Integer currentYear = sessionBean.getOperationYear();
//            AliasList aliasList = new AliasList();
//            aliasList.add("strategicGoal", "sg");
//            aliasList.add("sg.strategicAxis", "sa");
//            aliasList.add("sa.strategicPlan", "sp");
//            aliasList.add("dependencies", "d");
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//            criterionList.add(Restrictions.ge("sp.endYear", currentYear));
//            criterionList.add(Restrictions.le("sp.startYear", currentYear));
//            criterionList.add(Restrictions.eq("d.id", poi.getDependency().getId()));
//            ProjectionList projectionList = Projections.projectionList()
//                    .add(Projections.property("sa.name"))
//                    .add(Projections.property("sg.name"))
//                    .add(Projections.property("name"));
//            List<Object[]> data = getSpecificGoalService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList));
//            if (!data.isEmpty()) {
//                infoDependency = data.get(0);
//                allowInfoDependency = true;
//            } else {
//                allowInfoDependency = false;
//                infoDependency = null;
//            }
//        }
//
//        @Override
//        protected void clearFields() {
//        }
//
//        @Override
//        protected void fillSelected() {
//            selected.setPoi(this.poi);
//            selected.setResponsibleDependency(this.dependencyResponsible);
//            selected.setActivityBudgetProgram(this.activityBudgetProgram);
//            selected.setSpecificActivity(this.specificActivity);
//            selected.setDetail(this.detail.trim());
//            selected.setPoiUnity(poiUnity);
//            for (Quarter quarter : this.quarters) {
//                POISchedule schedule = this.schedules.get(quarter);
//                Integer quantity = schedule.getQuantity();
//                if (quantity == null) {
//                    quantity = 0;
//                }
//                schedule.setQuantity(quantity);
//            }
//            Auditory.make(selected, sessionBean.getCurrentUser());
//        }
//
//        @Override
//        public boolean save() {
//            String content = getSelected().getId() != null ? "Se ha actualizado una actividad del POI" : "Se ha creado una actividad del POI";
//            if (poi.getId() == null) {
//                Auditory.make(poi, sessionBean.getCurrentUser());
//                getPoiService().saveOrUpdate(poi);
//            }
//            if (selected.getId() == null) {
//                selected.setCode(poiActivityService.getNextCode(poi.getId()));
//            }
//            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
//            for (Quarter quarter : this.quarters) {
//                poiScheduleService.saveOrUpdate(this.schedules.get(quarter));
//            }
//            new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
//            return result;
//        }
//
//        @Override
//        public void doSave(String page, ILoadable loadable) {
//            //onLoad(true);
//            if (save()) {
//                if (!createAgain) {
//                    setKeepManaged(false);
//                    setSelected(null);
//                } else {
//                    setKeepManaged(true);
//                    create();
//                }
//            }
//
//            POIActivityBean.this.refresh();
//        }
//
//        public void changeDependencyRegister() {
//            getDependencySearcher().update();
//            getAbpSearcher().setDependency(poi == null ? null : poi.getDependency());
//            getAbpSearcher().update();
//            getSpecificActivitySearcher().setDependency(poi == null ? null : poi.getDependency());
//            getSpecificActivitySearcher().update();
//
//            if (poi != null && poi.getDependency() != null) {
//                updateInfoDependency();
//            }
//        }
//
//        @Override
//        public NavigationBean getNavigationBean() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void setNavigationBean(NavigationBean navigationBean) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public SessionBean getSessionBean() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void setSessionBean(SessionBean sessionBean) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public IPOIActivityService getMainService() {
//            return getPoiActivityService();
//        }
//
//        @Override
//        public void setMainService(IPOIActivityService mainService) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        /**
//         * @return the dependencyCreator
//         */
//        public Integer getDependencyCreator() {
//            try {
//                return this.poi.getDependency().getId();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        /**
//         * @param dependencyCreator the dependencyCreator to set
//         */
//        public void setDependencyCreator(Integer dependencyCreator) {
//            try {
//                updatePOI(dependencyCreator);
//            } catch (Exception e) {
//                this.poi = null;
//            }
//        }
//
//        public void updatePOI(Integer dependencyId) {
//            AliasList aliasList = new AliasList();
//            aliasList.add("dependency", "d");
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("year", sessionBean.getOperationYear()));
//            criterionList.add(Restrictions.eq("d.id", dependencyId));
//            List<POI> pois = getPoiService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList));
//            if (pois.isEmpty()) {
//                poi = new POI();
//                poi.setDependency(getDependencyService().getById(dependencyId));
//                poi.setYear(sessionBean.getOperationYear());
//            } else {
//                poi = pois.get(0);
//            }
//        }
//
//        /**
//         * @return the dependencyResponsible
//         */
//        public Integer getDependencyResponsible() {
//            try {
//                return this.dependencyResponsible.getId();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        /**
//         * @param dependencyResponsible the dependencyResponsible to set
//         */
//        public void setDependencyResponsible(Integer dependencyResponsible) {
//            try {
//                this.dependencyResponsible = getDependencyService().getById(dependencyResponsible);
//            } catch (Exception e) {
//                this.dependencyResponsible = null;
//            }
//        }
//
//        /**
//         * @return the activityBudgetProgram
//         */
//        public Long getActivityBudgetProgram() {
//            try {
//                return this.activityBudgetProgram.getId();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        /**
//         * @param activityBudgetProgram the activityBudgetProgram to set
//         */
//        public void setActivityBudgetProgram(Long activityBudgetProgram) {
//            try {
//                this.activityBudgetProgram = getActivityBudgetProgramService().getById(activityBudgetProgram);
//            } catch (Exception e) {
//                this.dependencyResponsible = null;
//            }
//        }
//
//        /**
//         * @return the specificActivity
//         */
//        public Integer getSpecificActivity() {
//            try {
//                return this.specificActivity.getId();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        /**
//         * @param specificActivity the specificActivity to set
//         */
//        public void setSpecificActivity(Integer specificActivity) {
//            try {
//                this.specificActivity = getSpecificActivityService().getById(specificActivity);
//            } catch (Exception e) {
//                this.specificActivity = null;
//            }
//        }
//
//        /**
//         * @return the detail
//         */
//        public String getDetail() {
//            return detail;
//        }
//
//        /**
//         * @param detail the detail to set
//         */
//        public void setDetail(String detail) {
//            this.detail = detail;
//        }
//
//        /**
//         * @return the schedules
//         */
//        public Map<Quarter, POISchedule> getSchedules() {
//            return schedules;
//        }
//
//        /**
//         * @param schedules the schedules to set
//         */
//        public void setSchedules(Map<Quarter, POISchedule> schedules) {
//            this.schedules = schedules;
//        }
//
//        /**
//         * @return the quarters
//         */
//        public List<Quarter> getQuarters() {
//            return quarters;
//        }
//
//        /**
//         * @param quarters the quarters to set
//         */
//        public void setQuarters(List<Quarter> quarters) {
//            this.quarters = quarters;
//        }
//
//        /**
//         * @return the createAgain
//         */
//        public boolean isCreateAgain() {
//            return createAgain;
//        }
//
//        /**
//         * @param createAgain the createAgain to set
//         */
//        public void setCreateAgain(boolean createAgain) {
//            this.createAgain = createAgain;
//        }
//
//        /**
//         * @return the allowInfoDependency
//         */
//        public Boolean getAllowInfoDependency() {
//            return allowInfoDependency;
//        }
//
//        /**
//         * @param allowInfoDependency the allowInfoDependency to set
//         */
//        public void setAllowInfoDependency(Boolean allowInfoDependency) {
//            this.allowInfoDependency = allowInfoDependency;
//        }
//
//        /**
//         * @return the infoDependency
//         */
//        public Object[] getInfoDependency() {
//            return infoDependency;
//        }
//
//        /**
//         * @param infoDependency the infoDependency to set
//         */
//        public void setInfoDependency(Object[] infoDependency) {
//            this.infoDependency = infoDependency;
//        }
//
//        /**
//         * @return the poi
//         */
//        public POI getPoi() {
//            return poi;
//        }
//
//        /**
//         * @param poi the poi to set
//         */
//        public void setPoi(POI poi) {
//            this.poi = poi;
//        }
//
//        /**
//         * @return the poiUnity
//         */
//        public Integer getPoiUnity() {
//            try {
//                return poiUnity.getId();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        /**
//         * @param poiUnity the poiUnity to set
//         */
//        public void setPoiUnity(Integer poiUnity) {
//            try {
//                this.poiUnity = poiUnityService.getById(poiUnity);
//            } catch (Exception e) {
//                this.poiUnity = null;
//            }
//        }
//    }
//
//    public class DependencySearcher implements java.io.Serializable {
//
//        private List<Object[]> data;
//
//        public void update() {
//            ProjectionList projectionList = Projections.projectionList()
//                    .add(Projections.property("id"))
//                    .add(Projections.property("path"))
//                    .add(Projections.property("name"));
//            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//            criterionList.add(Restrictions.eq("operational", true));
//            if (!sessionBean.isSuperAdmin()) {
//                criterionList.add(Restrictions.like("path", getSessionBean().getCurrentDependency().getPath(), MatchMode.START));
//            }
//            dn.core3.hibernate.OrderList orderList = new dn.core3.hibernate.OrderList();
//            orderList.add(Order.asc("path"));
//            data = getDependencyService().getListByRestrictions(projectionList, criterionList, orderList);
//        }
//
//        public String makeCode(String code) {
//            return StringUtils.rightPad(code + " ", 15, ".");
//        }
//
//        /**
//         * @return the data
//         */
//        public List<Object[]> getData() {
//            return data;
//        }
//
//        /**
//         * @param data the data to set
//         */
//        public void setData(List<Object[]> data) {
//            this.data = data;
//        }
//
//    }
//
//    public class POIUnitySearcher implements java.io.Serializable {
//
//        private List<Object[]> data;
//
//        public void update() {
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//
//            ProjectionList projectionList = Projections.projectionList()
//                    .add(Projections.distinct(Projections.property("id")))
//                    .add(Projections.property("code"))
//                    .add(Projections.property("name"));
//            OrderList orderList = new OrderList();
//            orderList.add(Order.asc("name"));
//            data = poiUnityService.addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
//        }
//
//        /**
//         * @return the data
//         */
//        public List<Object[]> getData() {
//            return data;
//        }
//
//        /**
//         * @param data the data to set
//         */
//        public void setData(List<Object[]> data) {
//            this.data = data;
//        }
//
//    }
//
//    public class ABPSearcher implements java.io.Serializable {
//
//        private List<Object[]> data;
//        private Dependency dependency;
//
//        public void update() {
//            if (dependency == null) {
//                data = Collections.EMPTY_LIST;
//                return;
//            }
//            AliasList aliasList = new AliasList();
//            aliasList.add("specificGoals", "sg");
//            aliasList.add("sg.strategicGoal", "st");
//            aliasList.add("st.strategicAxis", "sa");
//            aliasList.add("sa.strategicPlan", "sp");
//            aliasList.add("sg.dependencies", "d");
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//            criterionList.add(Restrictions.ge("sp.endYear", getSessionBean().getOperationYear()));
//            criterionList.add(Restrictions.le("sp.startYear", getSessionBean().getOperationYear()));
//            criterionList.add(Restrictions.eq("d.id", dependency.getId()));
//            ProjectionList projectionList = Projections.projectionList()
//                    .add(Projections.distinct(Projections.property("id")))
//                    .add(Projections.property("code"))
//                    .add(Projections.property("name"));
//            OrderList orderList = new OrderList();
//            orderList.add(Order.asc("code"));
//            data = getActivityBudgetProgramService().addRestrictionsVariant(Arrays.asList(aliasList, projectionList, criterionList, orderList));
//        }
//
//        /**
//         * @return the data
//         */
//        public List<Object[]> getData() {
//            return data;
//        }
//
//        /**
//         * @param data the data to set
//         */
//        public void setData(List<Object[]> data) {
//            this.data = data;
//        }
//
//        /**
//         * @return the dependency
//         */
//        public Dependency getDependency() {
//            return dependency;
//        }
//
//        /**
//         * @param dependency the dependency to set
//         */
//        public void setDependency(Dependency dependency) {
//            this.dependency = dependency;
//        }
//
//    }
//
//    public class SpecificActivitySearcher implements java.io.Serializable {
//
//        private List<Object[]> data;
//        private Dependency dependency;
//
//        public void update() {
//            if (dependency == null) {
//                data = Collections.EMPTY_LIST;
//                return;
//            }
//            AliasList aliasList = new AliasList();
//            aliasList.add("specificGoal", "sg");
//            aliasList.add("sg.strategicGoal", "st");
//            aliasList.add("st.strategicAxis", "sa");
//            aliasList.add("sa.strategicPlan", "sp");
//            aliasList.add("sg.dependencies", "d");
//            CriterionList criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("active", true));
//            criterionList.add(Restrictions.ge("sp.endYear", getSessionBean().getOperationYear()));
//            criterionList.add(Restrictions.le("sp.startYear", getSessionBean().getOperationYear()));
//            criterionList.add(Restrictions.eq("d.id", dependency.getId()));
//            ProjectionList projectionList = Projections.projectionList()
//                    .add(Projections.distinct(Projections.property("id")))
//                    .add(Projections.property("code"))
//                    .add(Projections.property("name"));
//            OrderList orderList = new OrderList();
//            orderList.add(Order.asc("code"));
//            data = getSpecificActivityService().addRestrictionsVariant(Arrays.asList(aliasList, projectionList, criterionList, orderList));
//        }
//
//        /**
//         * @return the data
//         */
//        public List<Object[]> getData() {
//            return data;
//        }
//
//        /**
//         * @param data the data to set
//         */
//        public void setData(List<Object[]> data) {
//            this.data = data;
//        }
//
//        /**
//         * @return the dependency
//         */
//        public Dependency getDependency() {
//            return dependency;
//        }
//
//        /**
//         * @param dependency the dependency to set
//         */
//        public void setDependency(Dependency dependency) {
//            this.dependency = dependency;
//        }
//
//    }
}

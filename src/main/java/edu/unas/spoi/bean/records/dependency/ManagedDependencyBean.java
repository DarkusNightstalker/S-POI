/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.dependency;

import dn.core3.model.util.Auditory;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.util.AManagedBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.oei.service.interfac.ISpecificGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import edu.unas.spoi.util.SeleccionableSearcher;
import edu.unas.spoi.util.SmartMessage;
import gkfire.web.util.BeanUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import org.apache.commons.collections.ListUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinType;

/**
 * The type Managed dependency bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
@Data
public class ManagedDependencyBean extends AManagedBean<Dependency, IDependencyService> implements ILoadable {

    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService mainService;
    @ManagedProperty(value = "#{budgetProgramService}")
    private IBudgetProgramService budgetProgramService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{budgetCeilingService}")
    private IBudgetCeilingService budgetCeilingService;
    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService fundingSourceService;
    @ManagedProperty(value = "#{specificGoalService}")
    private ISpecificGoalService specificGoalService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private HierarchiSearcher hierarchiSearcher;
    private DependencySearcher dependencySearcher;
    private FundingSourceSearcher fundingSourceSearcher;
    private ABPSearcher abpSearcher;
    private String path;
    private String code;
    private String name;
    private Integer operationYear;
    private Boolean operational;
    private StrategicPlan strategicPlan;
    private Map<Integer, List<Map<String, Object>>> budgetCeilings;
    private List<Map<String, Object>> fundingSources;
    private List<Map<String, Object>> abps;
    private List<Classifier> genericClassifiers;
    private List<Map<String, Object>> currentBudgetCeilings;

    private Dependency parent;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        abpSearcher = new ABPSearcher();
        hierarchiSearcher = new HierarchiSearcher();
        dependencySearcher = new DependencySearcher();
        fundingSourceSearcher = new FundingSourceSearcher();
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        StrategicPlan strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        if (strategicPlan == null || this.strategicPlan == null) {
        }
        if (selected.getId() != null) {
            currentBudgetCeilings = operational ? budgetCeilingService.getListBasicData(selected.getId(), sessionBean.getOperationYear()) : Collections.EMPTY_LIST;
        } else {
            currentBudgetCeilings = new ArrayList();
        }
        updateBudgetCeilings();
        this.strategicPlan = strategicPlan;
        if (selected.getId() != null) {
            dependencySearcher.validateCode();
        }
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado un sec. funcional" : "Se ha creado un sec. funcional";
        fillSelected();
        mainService.saveOrUpdate(selected, budgetCeilings);
        boolean result = super.save();
        SmartMessage.saveMessage(content);
        return result;
    }

    /**
     * Update budget ceilings.
     */
    public void updateBudgetCeilings() {

        AliasList aliasList = new AliasList();
        aliasList.add("classifierType", "ct");
        CriterionList criterionList = new CriterionList();
        criterionList._add(Restrictions.eq("active", true))
                ._add(Restrictions.eq("ct.codeDigit", new Short("2")));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("path"));
        genericClassifiers = classifierService.addRestrictionsVariant(aliasList, criterionList, orderList);

        budgetCeilings.clear();
        List<Map<String, Object>> allFundingSources = ListUtils.union(fundingSources, fundingSourceSearcher.getData());

        for (Map<String, Object> fs : allFundingSources) {
            List<Map<String, Object>> budgetCeilingList = new ArrayList<>();

            List<Long> accept = new ArrayList();
            for (Map<String, Object> bc : currentBudgetCeilings) {
                if (Objects.equals(((Integer)fs.get("id")).intValue(), bc.get("fundingSourceId"))) {
                    budgetCeilingList.add(bc);
                    accept.add((Long) bc.get("classifierId"));
                }
            }
            for (Classifier classifier : genericClassifiers) {
                if (!accept.contains(classifier.getId())) {
                    Map<String, Object> map = new HashMap();
                    map.put("fundingSourceId", fs.get("id"));
                    map.put("quantity", BigDecimal.ZERO);
                    map.put("classifierId", classifier.getId());
                    map.put("year", sessionBean.getOperationYear());
                    budgetCeilingList.add(map);
                }
            }
            budgetCeilings.put((Integer) fs.get("id"), budgetCeilingList);
        }
        System.gc();
    }

    /**
     * Gets by funding source.
     *
     * @param data       the data
     * @param classifier the classifier
     * @return the by funding source
     */
    public Map<String,Object> getByFundingSource(List<Map<String,Object>> data, Classifier classifier) {
        for (Map<String,Object> budgetCeiling : data) {
            if (Objects.equals(budgetCeiling.get("classifierId"), classifier.getId())) {
                return budgetCeiling;
            }
        }
        throw new IllegalArgumentException("Empty Data!!");
    }

    @Override
    protected void fillFields() {
        path = selected.getPath();
        code = selected.getCode();
        name = selected.getName();
        operational = selected.getOperational();
        createAgain = selected.getId() == null;
        operationYear = selected.getOperationYear();
        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        genericClassifiers = new ArrayList();
        if (selected.getId() != null) {
            dependencySearcher.validateCode();
            currentBudgetCeilings = operational ? budgetCeilingService.getListBasicData(selected.getId(), sessionBean.getOperationYear())
                    : Collections.EMPTY_LIST;
            abps = activityBudgetProgramService.getListForAssigmentDependency(selected.getId());
            fundingSources = budgetCeilingService.getListNotEmptyFundingSourceBasicData(selected.getId(), sessionBean.getOperationYear());
        } else {
            dependencySearcher.treeView = "";
            dependencySearcher.validCode = false;
            currentBudgetCeilings = new ArrayList();
            fundingSources = new ArrayList();
            abps = new ArrayList();
        }
        budgetCeilings = new HashMap();
        fundingSourceSearcher.update();
        updateBudgetCeilings();
        abpSearcher.update();
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setPath(path.trim());
        selected.setName(name.trim());
        selected.setParent(parent);
        selected.setOperational(operational);
        selected.setOperationYear(operationYear == null ? sessionBean.getOperationYear() : operationYear);
        try {
            selected.setCode(selected.getPath().substring(parent.getPath().length()));
        } catch (NullPointerException e) {
            selected.setCode(selected.getPath());
        }
        selected.setActivityBudgetPrograms(new ArrayList());

        if (operational && selected.getId() != null) {
            for (Map<String, Object> abp : abps) {
                selected.getActivityBudgetPrograms().add(new ActivityBudgetProgram((Long) abp.get("id")));
            }
        }
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * The type Hierarchi searcher.
     */
    @Data
    public class HierarchiSearcher implements java.io.Serializable {

        private Integer currentLevel;
        private Map<Integer, List<Object[]>> dependencyByLevel;
        private Map<Integer, String> selectedByLevel;
        private String prefixCode;
        private String code;
        private Boolean validCode;

        /**
         * Instantiates a new Hierarchi searcher.
         */
        public HierarchiSearcher() {
            currentLevel = 0;
            dependencyByLevel = new HashMap();
            selectedByLevel = new HashMap();
        }

        /**
         * Refresh.
         */
        public void refresh() {
            if (ManagedDependencyBean.this.getParent() == null) {
                currentLevel = 0;
                dependencyByLevel = new HashMap();
                selectedByLevel = new HashMap();
                loadLevel();
            } else {
                loadHierarchi();
            }
            makeCode();
        }

        /**
         * Load hierarchi.
         */
        public void loadHierarchi() {
            List<Integer> selecteds = new ArrayList();
            selecteds.add(getParent().getId());
            try {
                while (true) {
                    selecteds.add((Integer) getMainService().listHQL("SELECT d.parent.id FROM Dependency d WHERE d.id = " + selecteds.get(selecteds.size() - 1)).get(0));
                }
            } catch (NullPointerException e) {
            }
            currentLevel = -1;
            for (int i = 0; i < selecteds.size(); i++) {
                currentLevel = i;
                loadLevel();
                selectedByLevel.put(currentLevel, selecteds.get(selecteds.size() - i - 1) + "");
            }
        }

        /**
         * Gets levels.
         *
         * @return the levels
         */
        public List getLevels() {
            return new ArrayList(dependencyByLevel.keySet());
        }

        /**
         * Load level.
         */
        public void loadLevel() {
            String lastSelected = selectedByLevel.get(currentLevel - 1);

            dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
            aliasList.add("parent", "p", JoinType.LEFT_OUTER_JOIN);
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("path"))
                    .add(Projections.property("name"))
                    .add(Projections.property("p.id"));
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            criterionList.add(Restrictions.eq("year", getSessionBean().getOperationYear()));
            if (lastSelected == null) {
                criterionList.add(Restrictions.isNull("parent"));
            } else {
                criterionList.add(Restrictions.eq("p.path", lastSelected));
            }
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("name"));
            dependencyByLevel.put(currentLevel, mainService.getListByRestrictions(aliasList, criterionList, projectionList, orderList));
            selectedByLevel.put(currentLevel, "");
        }

        /**
         * Change.
         *
         * @param level the level
         */
        public void change(Integer level) {
            String selectedInLevel = selectedByLevel.get(level);
            for (int i = level + 1; i <= currentLevel; i++) {
                dependencyByLevel.remove(i);
            }
            currentLevel = level + 1;
            if (selectedInLevel != "") {
                loadLevel();
            }
            makeCode();
        }

        private void makeCode() {
            prefixCode = "";
            for (int i = 0; i < currentLevel; i++) {
                prefixCode = selectedByLevel.get(i);
            }
        }

        private void endSearch() {
        }

    }

    /**
     * The type Dependency searcher.
     */
    @Data
    public class DependencySearcher implements java.io.Serializable {

        private String treeView;
        private Boolean validCode;

        /**
         * Validate code.
         */
        public void validateCode() {
            validCode = true;
            if (mainService.validatePath(path, sessionBean.getOperationYear(), selected.getId())) {
                String tempPath = ManagedDependencyBean.this.getPath().trim();
                String code = "";
                ProjectionList projectionList;
                Dependency currentParent = null;
                for (int i = ManagedDependencyBean.this.getPath().length(); i > 3; i--) {
                    code += tempPath.charAt(tempPath.length() - 1);
                    tempPath = tempPath.substring(0, tempPath.length() - 1);
                    currentParent = mainService.getBy(tempPath, sessionBean.getOperationYear());
                    if (currentParent != null) {
                        break;
                    }
                }
                if (currentParent != null) {
                    Integer id = currentParent.getId();
                    treeView = "<ul>"
                            + "<li>"
                            + "<span> " + currentParent.getPath() + " - " + currentParent.getName() + " </span>"
                            + "</li>"
                            + "</ul>";
                    while (true) {
                        try {
                            Dependency upParent = mainService.getParent(id);
                            id = upParent.getId();
                            treeView = "<ul>"
                                    + "<li>"
                                    + "<span>" + upParent.getPath() + " - " + upParent.getName() + "</span>"
                                    + treeView
                                    + "</li>"
                                    + "</ul>";
                        } catch (Exception e) {
                            break;
                        }
                    }
                    parent = currentParent;
                } else {
                    parent = null;
                    treeView = "<span class='text-muted font-lg'>Ninguna dependencia superior</span>";
                }
            } else {
                validCode = false;
                SmartMessage.errorMessage("El codigo introducido ya pertenece a otro u otra dependencia ya comienza con este codigo");
            }
        }

    }
    //<editor-fold defaultstate="collapsed" desc="ActivityBudgetProgramSearcher">

    /**
     * The type Abp searcher.
     */
    public class ABPSearcher extends SeleccionableSearcher<Map<String, Object>, Long> {

        @Override
        public void update() {
            List ids = abps.stream().map(new Function<Map<String, Object>, Long>() {
                @Override
                public Long apply(Map<String, Object> item) {
                    return (Long) item.get("id");
                }
            }).collect(Collectors.toList());
            data = activityBudgetProgramService.getListForGiveDependency(ids);
        }

        /**
         * Add.
         */
        public void add() {
            try {
                for (Map<String, Object> item : data) {
                    if (Objects.equals(((Long) item.get("id")), selected)) {
                        abps.add(item);
                        selected = null;
                        update();
                        return;
                    }
                }
            } catch (Exception e) {
                SmartMessage.errorMessage("Se debe seleccionar una actividad primero");
            }
        }

        /**
         * Remove.
         *
         * @param abp the abp
         */
        public void remove(Map<String, Object> abp) {
            abps.remove(abp);
            update();
        }
    }

    /**
     * The type Funding source searcher.
     */
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="FundingSourceSearcher">
    public class FundingSourceSearcher extends SeleccionableSearcher<Map<String, Object>, Integer> {

        @Override
        public void update() {
            List ids = fundingSources.stream().map(new Function<Map<String, Object>, Integer>() {
                @Override
                public Integer apply(Map<String, Object> item) {
                    return (Integer) item.get("id");
                }
            }).collect(Collectors.toList());
            data = fundingSourceService.getListBasicData(ids);
        }

        /**
         * Add.
         */
        public void add() {
            for (Map<String, Object> item : data) {
                if (Objects.equals(selected, item.get("id"))) {
                    getFundingSources().add(item);
                    update();
                    return;
                }
            }
        }

        /**
         * Remove.
         *
         * @param item the item
         */
        public void remove(Map<String, Object> item) {
            fundingSources.remove(item);
            for (Map<String, Object> bc : budgetCeilings.get((Integer) item.get("id"))) {
                bc.put("quantity", BigDecimal.ZERO);
            }
            update();
        }
    }

//</editor-fold>
}

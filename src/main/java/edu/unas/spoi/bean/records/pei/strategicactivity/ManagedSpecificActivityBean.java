/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicactivity;

import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.SpecificActivity;
import edu.unas.spoi.oei.model.SpecificGoal;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.ISpecificActivityService;
import edu.unas.spoi.oei.service.interfac.ISpecificGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.util.SeleccionableSearcher;
import edu.unas.spoi.util.SmartMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;

/**
 * The type Managed specific activity bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
public class ManagedSpecificActivityBean extends AManagedBean<SpecificActivity, ISpecificActivityService> implements ILoadable {

    @ManagedProperty(value = "#{specificActivityService}")
    private ISpecificActivityService mainService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{specificGoalService}")
    private ISpecificGoalService specificGoalService;
    @ManagedProperty(value = "#{strategicGoalService}")
    private IStrategicGoalService strategicGoalService;
    @ManagedProperty(value = "#{budgetProgramService}")
    private IBudgetProgramService budgetProgramService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private StrategicAxisSearcher strategicAxisSearcher;
    private SpecificGoalSearcher specificGoalSearcher;
    private ABPSearcher abpSearcher;
    private String code;
    private String name;
    private List<Map<String, Object>> activities;
    private Integer strategicPlanId;
    private Integer strategicAxisId;
    private Integer specificGoalId;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        strategicAxisSearcher = new StrategicAxisSearcher();
        specificGoalSearcher = new SpecificGoalSearcher();
        abpSearcher = new ABPSearcher();
    }

    @Override
    public boolean save() {
        if (!mainService.validateCode(code.trim(), strategicPlanId, selected.getId())) {

            SmartMessage.errorMessage("El codigo '" + code.trim() + "' ya fue asignado a una actividad estrategica en el plan del año " + sessionBean.getOperationYear());
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado una actividad especifica" : "Se ha creado una actividad especifica";
            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return result;
        }
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
        
    }

    @Override
    protected void fillFields() {
        code = selected.getCode();
        name = selected.getName();
        try {
            specificGoalId = selected.getSpecificGoal().getId();
            strategicAxisId = selected.getSpecificGoal().getStrategicGoal().getStrategicAxis().getId();
            strategicPlanId = selected.getSpecificGoal().getStrategicGoal().getStrategicAxis().getStrategicPlan().getId();
        } catch (NullPointerException e) {
            specificGoalId = null;
            strategicAxisId = null;
            strategicPlanId = null;
        }

        if (selected.getId() == null) {
            activities = new ArrayList();
        } else {
            activities = activityBudgetProgramService.getListForAssigmentStrategicActivity(selected.getId());
        }
        if (strategicPlanId == null) {
            try {
                strategicPlanId = strategicPlanService.getBy(sessionBean.getOperationYear()).getId();
            } catch (NullPointerException e) {
            }
        }
        strategicAxisSearcher.update();
        specificGoalSearcher.update();
        abpSearcher.update();
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        selected.setSpecificGoal(new SpecificGoal(specificGoalId));
        selected.setActivityBudgetPrograms(new ArrayList<ActivityBudgetProgram>());
        for (Map<String, Object> activity : activities) {
            selected.getActivityBudgetPrograms().add(new ActivityBudgetProgram((Long) activity.get("id")));
        }
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * The type Strategic axis searcher.
     */
    @Data
    public class StrategicAxisSearcher implements java.io.Serializable {

        private List<Object[]> data;

        /**
         * Update.
         */
        public void update() {
            if (strategicPlanId == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            data = strategicAxisService.getListBasicData(strategicPlanId);
        }

        /**
         * Change.
         */
        public void change() {
            specificGoalId = null;
            specificGoalSearcher.update();
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Integer id) {
            strategicAxisId = id;
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            return strategicAxisId;
        }
    }

    /**
     * The type Specific goal searcher.
     */
    @Data
    public class SpecificGoalSearcher implements java.io.Serializable {

        private List<Object[]> data;

        /**
         * Update.
         */
        public void update() {
            if (strategicAxisId == null) {
                data = Collections.EMPTY_LIST;
                return;
            }
            data = specificGoalService.getListBasicDataByStrategicAxis(strategicAxisId);
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Integer id) {
            specificGoalId = id;
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            return specificGoalId;
        }

    }

    /**
     * The type Abp searcher.
     */
    public class ABPSearcher extends SeleccionableSearcher<Map<String, Object>, Long> {

        @Override
        public void update() {
            List ids = activities.stream().map(new Function<Map<String, Object>, Long>() {
                @Override
                public Long apply(Map<String, Object> o) {
                    return (Long) o.get("id");
                }
            }).collect(Collectors.toList());

            data = activityBudgetProgramService.getListForGiveStrategicActivity(ids);
        }

        /**
         * Add.
         */
        public void add() {
            try {
                for (Map<String, Object> item : data) {
                    if (((Long) item.get("id")).longValue() == selected.longValue()) {
                        ManagedSpecificActivityBean.this.activities.add(item);
                        selected = null;
                        update();
                        return;
                    }
                }
            } catch (NullPointerException npe) {
                SmartMessage.errorMessage("No se ha seleccionado una actividad presupuestal");
            }
        }

        /**
         * Remove.
         *
         * @param o the o
         */
        public void remove(Map o) {
            ManagedSpecificActivityBean.this.activities.remove(o);
            update();
        }
    }
}

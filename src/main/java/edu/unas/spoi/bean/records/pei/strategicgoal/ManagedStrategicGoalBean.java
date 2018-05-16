/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicgoal;

import dn.core3.model.util.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.util.AManagedBean;
import edu.unas.spoi.oei.model.StrategicAxis;
import edu.unas.spoi.oei.model.StrategicGoal;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.util.SimpleSearcher;
import edu.unas.spoi.util.SmartMessage;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Managed strategic goal bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
@EqualsAndHashCode(callSuper = true)
public class ManagedStrategicGoalBean extends AManagedBean<StrategicGoal, IStrategicGoalService> implements ILoadable {

    @ManagedProperty(value = "#{strategicGoalService}")
    private IStrategicGoalService mainService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private SimpleSearcher strategicAxisSearcher;

    private StrategicPlan strategicPlan;
    private Integer strategicAxisId;
    private String code;
    private String name;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        strategicAxisSearcher = new SimpleSearcher() {
            @Override
            public void update() {
                if (strategicPlan == null) {
                    data = Collections.EMPTY_LIST;
                } else {
                    data = strategicAxisService.getListBasicData(strategicPlan.getId());
                }
            }
        };
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        strategicAxisSearcher.update();
        if (strategicPlan == null) {
            navigationBean.setContent("/pages/records/strategic_goal.xhtml");
        }
    }

    @Override
    public boolean save() {
        if (!mainService.validateCode(code.trim(), strategicPlan.getId(), selected.getId())) {
            SmartMessage.errorMessage("El codigo '" + code.trim() + "' ya esta definido dentro del plan '" + strategicPlan.getName() + "'");
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un objetivo extrategico" : "Se ha creado un objetivo extrategico";
            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return result;
        }
    }

    @Override
    protected void fillFields() {
        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        code = selected.getCode();
        name = selected.getName();
        try {
            strategicAxisId = selected.getStrategicAxis().getId();
        } catch (NullPointerException e) {
            strategicAxisId = null;
        }
        strategicAxisSearcher.update();
        createAgain = selected.getId() == null;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        selected.setStrategicAxis(new StrategicAxis(strategicAxisId));
        Auditory.make(selected, sessionBean.getCurrentUser());
    }
}

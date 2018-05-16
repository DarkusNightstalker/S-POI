/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicaxis;

import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.records.pei.strategicplan.StrategicPlanBean;
import edu.unas.spoi.oei.model.StrategicAxis;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.util.SmartMessage;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Managed strategic axis bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
@EqualsAndHashCode(callSuper = true)
public class ManagedStrategicAxisBean extends AManagedBean<StrategicAxis, IStrategicAxisService> implements ILoadable {
    
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService mainService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{strategicAxisBean}")
    private StrategicAxisBean strategicAxisBean;
    
    private String code;
    private String name;
    private String description;
    private StrategicPlan strategicPlan;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
    }
    
    @Override
    public boolean save() {        
        if (!mainService.validateCode(code.trim(), strategicPlan.getId(), selected.getId())) {
            SmartMessage.errorMessage("El codigo '" + code.trim() + "' ya fue asignado a un eje estrategico en el plan " + strategicPlan.getName());
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un eje estrategico" : "Se ha creado un eje estregico";
            boolean result = super.save(); 
            SmartMessage.saveMessage(content);
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
        strategicPlan = selected.getStrategicPlan();
        if (strategicPlan == null) {
            strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
            if (strategicPlan == null) {
                navigationBean.setContent("/pages/records/pei/strategic-plan/strategic-plan-view.xhtml");
                sessionBean.setLoadable(strategicAxisBean);
                BeanUtil.exceuteJS("location.reload()");
                sessionBean.getMessages().add(
                        new SmartMessage("Error", "El año operativo " + sessionBean.getOperationYear() + "no tiene un plan estratégico configurado", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated"));
                return;
            }
        }
        code = selected.getCode();
        name = selected.getName();
        description = selected.getDescription();
        createAgain = true;
    }
    
    @Override
    protected void clearFields() {
    }
    
    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        selected.setDescription(description.trim());
        selected.setStrategicPlan(strategicPlan);
        Auditory.make(selected, sessionBean.getCurrentUser());
    }
    
}

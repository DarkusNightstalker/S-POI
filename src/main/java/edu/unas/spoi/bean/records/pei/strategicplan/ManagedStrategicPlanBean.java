/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicplan;

import gkfire.web.bean.ILoadable;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.util.AManagedBean;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.web.util.BeanUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Managed strategic plan bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
@EqualsAndHashCode(callSuper = true)
public class ManagedStrategicPlanBean extends AManagedBean<StrategicPlan, IStrategicPlanService> implements ILoadable {

    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService mainService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private Integer startYear;
    private String name;
    private Integer endYear;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
    }

    @Override
    public boolean save() {
        boolean result = false;
        if (selected.getId() == null && !mainService.validateYear(sessionBean.getOperationYear(), selected.getId())) {
            SmartMessage.errorMessage("El año actual ya cuenta con un plan estratégico");
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un plan estrategico" : "Se ha creado un plan estregico";
            result = super.save(); 
            SmartMessage.saveMessage(content);          
        }
        return result;
    }

    @Override
    protected void fillFields() {
        startYear = selected.getStartYear();
        endYear = selected.getEndYear();
        name = selected.getName();
        createAgain = true;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        if (startYear == null || endYear == null) {
            selected.setStartYear(sessionBean.getOperationYear());
            selected.setEndYear(sessionBean.getOperationYear());
        }
        selected.setName(getName());
        Auditory.make(selected, getSessionBean().getCurrentUser());
    }
}

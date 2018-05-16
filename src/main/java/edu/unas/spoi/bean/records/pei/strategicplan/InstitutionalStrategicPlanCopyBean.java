/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicplan;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.util.SmartMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Institutional strategic plan copy bean.
 *
 * @author Darkus Nightmare
 */
@ManagedBean
@SessionScoped
@Data
public class InstitutionalStrategicPlanCopyBean implements java.io.Serializable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;

    /**
     * Load previous data.
     */
    public void loadPreviousData() {
        if (strategicPlanService.validateYear(sessionBean.getOperationYear(),null)) {
            StrategicPlan strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear() - 1);
            strategicPlan.setEndYear(sessionBean.getOperationYear());
            strategicPlanService.update(strategicPlan);
            SmartMessage.successMessage("Se ha traslado los datos del año " + (sessionBean.getOperationYear() - 1) + " al " + sessionBean.getOperationYear());
        }else{
            SmartMessage.errorMessage("El año actual ya cuenta con un plan estratégico");
        }
    }
}

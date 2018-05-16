/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.dependency;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import edu.unas.spoi.util.SmartMessage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;

/**
 * The type Dependency copy bean.
 *
 * @author Darkus Nightmare
 */
@ManagedBean
@SessionScoped
@Data
public class DependencyCopyBean implements Serializable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;

    private List<Map<String, Object>> data;
    private List<Map<String, Object>> remove;
    private boolean allowCopy;
    private boolean saved;

    /**
     * Refresh.
     */
    public void refresh() {
        allowCopy = dependencyService.getAllowCopy(sessionBean.getOperationYear());
        data = Collections.EMPTY_LIST;
    }

    /**
     * Load previous data.
     */
    public void loadPreviousData() {
        remove = new ArrayList();
        saved= false;
        data = dependencyService.getListForCopyPreviousYear(sessionBean.getOperationYear() - 1);
    }

    /**
     * Remove.
     *
     * @param item the item
     */
    public void remove(Map item) {
        remove.add(item);
    }

    /**
     * Save.
     */
    public void save() {
        for (Map<String, Object> map : remove) {
            data.remove(map);
        }
        dependencyService.copyDataToYear(data, sessionBean.getOperationYear(), sessionBean.getCurrentUser());
        SmartMessage.successMessage("Se ha copiado las dependencias del año pasado satisfactoriamente");
        saved = true;
        allowCopy = false;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.bean;

import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.poi.bean.view.ViewPOIBean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;

/**
 * The type Poi bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean(name = "poiBean")
@SessionScoped
@Data
public class POIBean implements java.io.Serializable {

    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{viewPOIBean}")
    private ViewPOIBean viewPOIBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private List dependencies;
    private Integer selected;

    /**
     * Refresh.
     */
    public void refresh() {
        dependencies = dependencyService.getListOperationalBasicData(sessionBean.getOperationYear());
    }

    /**
     * Begin.
     */
    public void begin() {
        viewPOIBean.begin(selected);
        navigationBean.setContent("/pages/poi/poi_view.xhtml");
    }

}

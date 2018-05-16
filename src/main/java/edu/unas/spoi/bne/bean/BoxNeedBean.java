/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.bean;

import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bne.bean.view.ViewBNeedBean;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import gkfire.hibernate.CriterionList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Box need bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
public class BoxNeedBean implements java.io.Serializable {
   @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{viewBNeedBean}")
    private ViewBNeedBean viewBNeedBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    
    private List dependencies;
    private Integer selected;

    /**
     * Refresh.
     */
    public void refresh(){
        dependencies = dependencyService.getListOperationalBasicData(sessionBean.getOperationYear());
    }

    /**
     * Begin.
     */
    public void begin(){
        viewBNeedBean.begin(selected);
        navigationBean.setContent("/pages/box_need/box_need_view.xhtml");
    }
}

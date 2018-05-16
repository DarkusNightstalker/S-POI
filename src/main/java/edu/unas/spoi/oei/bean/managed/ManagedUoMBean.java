/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean.managed;

import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.model.UoM;
import edu.unas.spoi.oei.service.interfac.IUoMService;
import edu.unas.spoi.util.SmartMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * The type Managed uo m bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ManagedUoMBean  extends AManagedBean<UoM, IUoMService> implements ILoadable {

    @ManagedProperty(value = "#{uomService}")
    private IUoMService mainService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private String code;
    private String name;
    
    @Override
    public boolean save() {
        if (mainService.existCode(code.trim(),selected.getId())) {
            new SmartMessage("Codigo Invalido", "El codigo '"+code+"' ya pertenece a otra unidad presupuestaria", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado una unidad presupuestaria" : "Se ha creado una unidad presupuestaria";
            boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return result;
        }
    }


    @Override
    public void onLoad(boolean allowAjax) {
       
    }
    
    @Override
    protected void fillFields() {
        code = selected.getCode();
        name = selected.getName();
        createAgain = true;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * @return the mainService
     */
    @Override
    public IUoMService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IUoMService mainService) {
        this.mainService = mainService;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    /**
     * @param navigationBean the navigationBean to set
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    
}

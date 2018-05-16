/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.bean.managed;

import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.ppto.bean.FundingSourceBean;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import edu.unas.spoi.util.SmartMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * The type Managed funding source bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ManagedFundingSourceBean extends AManagedBean<FundingSource, IFundingSourceService> implements ILoadable {

    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService mainService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{fundingSourceBean}")
    private FundingSourceBean fundingSourceBean;

    private String code;
    private String name;
    private String abbr;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
    }

    @Override
    public boolean save() {
        if (mainService.existCode(code,selected.getId())) {
            new SmartMessage("Datos guardadados", "El codigo '"+code+"' ya pertenece a otro rubro", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un rubro" : "Se ha creado un rubro";
            super.save(); //To change body of generated methods, choose Tools | Templates.
            new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            return true;
        }
    }

    @Override
    protected void fillFields() {
        code = getSelected().getCode();
        name = getSelected().getName();
        abbr = selected.getAbbr();
        onLoad(true);
        createAgain = selected.getId() == null;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code);
        selected.setName(name);
        selected.setAbbr(abbr);
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * @return the mainService
     */
    public IFundingSourceService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    public void setMainService(IFundingSourceService mainService) {
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

    /**
     * Gets funding source bean.
     *
     * @return the fundingSourceBean
     */
    public FundingSourceBean getFundingSourceBean() {
        return fundingSourceBean;
    }

    /**
     * Sets funding source bean.
     *
     * @param fundingSourceBean the fundingSourceBean to set
     */
    public void setFundingSourceBean(FundingSourceBean fundingSourceBean) {
        this.fundingSourceBean = fundingSourceBean;
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
     * Gets abbr.
     *
     * @return the abbr
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * Sets abbr.
     *
     * @param abbr the abbr to set
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

}

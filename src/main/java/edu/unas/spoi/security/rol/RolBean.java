/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unas.spoi.security.rol;

import edu.unas.spoi.security.model.Rol;
import edu.unas.spoi.security.service.interfac.IRolService;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Rol bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class RolBean implements ILoadable,java.io.Serializable {
    
    @ManagedProperty(value = "#{rolService}")
    private IRolService rolService;
    private List<Object[]> data;
    private String code;
    private String name;
    private Integer id;
    
    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
    }

    /**
     * Refresh.
     */
    public void refresh() {
        code = "";
        name = "";
        search();
    }

    /**
     * Search.
     */
    public void search() {
        code =  code.trim();
        name = name.trim();
        ProjectionList  projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("code"))
                .add(Projections.property("name"))
                .add(Projections.property("active"));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("active"));
        orderList.add(Order.asc("code"));
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        if (code.length() != 0) {
            criterionList.add(Restrictions.like("code", code,MatchMode.ANYWHERE));
        }
        if (name.length() != 0) {
            criterionList.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
        }
        data = rolService.addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList));
    }

    /**
     * Gets rol service.
     *
     * @return the rolService
     */
    public IRolService getRolService() {
        return rolService;
    }

    /**
     * Sets rol service.
     *
     * @param rolService the rolService to set
     */
    public void setRolService(IRolService rolService) {
        this.rolService = rolService;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<Object[]> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data to set
     */
    public void setData(List<Object[]> data) {
        this.data = data;
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
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
}

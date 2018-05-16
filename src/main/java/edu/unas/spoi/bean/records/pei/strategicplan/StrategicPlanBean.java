/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicplan;

import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Strategic plan bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
public class StrategicPlanBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;

    private List<Object[]> data;
    private Integer year;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
    }

    /**
     * Gets years.
     *
     * @return the years
     */
    public List<Integer> getYears() {
        List<Integer> years = new ArrayList();
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= currentYear; i++) {
            years.add(i);
        }
        return years;
    }

    /**
     * Refresh.
     */
    public void refresh() {
        year = null;
        search();
    }

    /**
     * Search.
     */
    public void search() {
        data = strategicPlanService.getListLazyForMainView(1,strategicPlanService.countListLazyForMainView(year).intValue(),year);
    }

}

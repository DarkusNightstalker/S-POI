/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.dependency;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bean.util.AViewBean;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
import edu.unas.spoi.bne.service.interfac.IBNeScheduleService;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type View dependency bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ViewDependencyBean extends AViewBean<Dependency, Integer, IDependencyService> {

    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService mainService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService fundingSourceService;
    @ManagedProperty(value = "#{budgetCeilingService}")
    private IBudgetCeilingService budgetCeilingService;
    @ManagedProperty(value = "#{bneItemService}")
    private IBNeItemService bneItemService;
    @ManagedProperty(value = "#{bneScheduleService}")
    private IBNeScheduleService bneScheduleService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private DependencySearcher dependencySearcher;

    private List<Object[]> abps;
    private Map<Long, List<Object[]>> budgetCeiling;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        dependencySearcher = new DependencySearcher();
    }

    @Override
    protected void update(boolean widthDisabled) {
        super.update(widthDisabled);
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.distinct(Projections.id()))
                .add(Projections.property("functionalSequence"));
        AliasList aliasList = new AliasList();
        aliasList.add("specificGoals", "sg");
        aliasList.add("sg.dependencies", "d");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        criterionList.add(Restrictions.eq("d.id", selected.getId()));

        abps = activityBudgetProgramService.addRestrictionsVariant(projectionList, aliasList, criterionList);

        budgetCeiling = new HashMap();

        projectionList = Projections.projectionList()
                .add(Projections.distinct(Projections.id()))
                .add(Projections.property("code"))
                .add(Projections.property("name"));
        criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("code"));
        List<Object[]> fs = fundingSourceService.addRestrictionsVariant(projectionList, criterionList, orderList);
        for (Object[] abp : abps) {
            List<Object[]> data = new ArrayList();
            for (Object[] fundingSource : fs) {
                projectionList = Projections.projectionList()
                        .add(Projections.property("quantity"));
                aliasList = new AliasList();
                aliasList.add("fundingSource", "fs");
                aliasList.add("activityBudgetProgram", "abp");
                aliasList.add("dependency", "d");
                criterionList = new CriterionList();
                criterionList.add(Restrictions.eq("year", sessionBean.getOperationYear()));
                criterionList.add(Restrictions.eq("fs.id", fundingSource[0]));
                criterionList.add(Restrictions.eq("abp.id", abp[0]));
                criterionList.add(Restrictions.eq("d.id", selected.getId()));
                Double quantity = null;
                try {
                    quantity = (Double) budgetCeilingService.addRestrictionsVariant(projectionList, aliasList, criterionList).get(0);
                } catch (Exception e) {

                }
                if (quantity == null) {
                    quantity = 0.0;
                }

                Object[] d = new Object[]{fundingSource[0], fundingSource[1], fundingSource[2], quantity, calculateBN((Long) abp[0], (Integer)fundingSource[0]),0.0};
                data.add(d);
            }
            budgetCeiling.put((Long) abp[0], data);
        }
        dependencySearcher.validateCode();

//        projectionList = Projections.projectionList()
//                .add(Projections.distinct(Projections.property("year")));
//        criterionList = new CriterionList();
//        criterionList.add(Restrictions.ne("year", sessionBean.getOperationYear()));
//        OrderList orderList = new OrderList();
//        orderList.add(Order.asc("year"));
//        years = budgetCeilingService.addRestrictionsVariant(projectionList, criterionList, orderList);
//        otherBudgetCeiling = new HashMap();
//        projectionList = Projections.projectionList()
//                .add(Projections.property("fs.code"))
//                .add(Projections.property("fs.name"))
//                .add(Projections.property("abp.code"))
//                .add(Projections.property("abp.name"))
//                .add(Projections.property("quantity"));
//        for (Integer year : years) {
//            criterionList = new CriterionList();
//            criterionList.add(Restrictions.eq("year", year));
//            criterionList.add(Restrictions.eq("d.id", selected.getId()));
//            otherBudgetCeiling.put(year, budgetCeilingService.addRestrictionsVariant(projectionList, aliasList, criterionList));
//        }
    }

    /**
     * Chart string.
     *
     * @param abp the abp
     * @return the string
     */
    public String chart(Long abp){
        String sData = "";
        List<Object[]> data = budgetCeiling.get(abp);
        
        sData +="var data_TP = [];";
        sData +="var data_PS = [];";
        sData +="var data_PU = [];";
        for(Object[] item : data){
            sData +="data_TP.push(["+item[0]+", "+item[3]+"]);";
            sData +="data_PS.push(["+item[0]+", "+item[4]+"]);";
            sData +="data_PU.push(["+item[0]+", "+item[5]+"]);";
        }
        sData +="ds.push({";
        sData +="    data: data_TP,";
        sData +="    bars: {";
        sData +="        show: true,";
        sData +="        barWidth: 0.2,";
        sData +="        order: 1,";
        sData +="    }";
        sData +="});";
        sData +="ds.push({";
        sData +="    data: data_PS,";
        sData +="    bars: {";
        sData +="        show: true,";
        sData +="        barWidth: 0.2,";
        sData +="        order: 1,";
        sData +="    }";
        sData +="});";
        sData +="ds.push({";
        sData +="    data: data_PU,";
        sData +="    bars: {";
        sData +="        show: true,";
        sData +="        barWidth: 0.2,";
        sData +="        order: 1,";
        sData +="    }";
        sData +="});";
        sData +="$.plot($('#bar-chart-"+abp+"'), ds, {";
        sData +="   colors: ['#6595b4', '#7e9d3a', '#666', '#BBB'],";
        sData +="    grid: {";
        sData +="       show: true,";
        sData +="       hoverable: true,";
        sData +="       clickable: true,";
        sData +="       tickColor: '#efefef',";
        sData +="       borderWidth: 0,";
        sData +="       borderColor: '#efefef',";
        sData +="   },";
        sData +="   legend: true,";
        sData +="   tooltip: true,";
        sData +="   xaxis: {";
        for(Object[] item : data){
        sData +=" ";
        }
        sData +="   }";
        sData +="});";
        return sData;
    }

    /**
     * Calculate bn double.
     *
     * @param abp the abp
     * @param fs  the fs
     * @return the double
     */
    public Double calculateBN(Long abp, Integer fs) {
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("unitPrice"));
        AliasList aliasList = new AliasList();
        aliasList.add("poiActivity", "poia");
        aliasList.add("poia.poi", "poi");
        aliasList.add("poia.activityBudgetProgram", "abp");
        aliasList.add("fundingSource", "fs");
        aliasList.add("classifier", "c");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("poi.dependency", selected));
        criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
        criterionList.add(Restrictions.eq("fs.id", fs));
        criterionList.add(Restrictions.eq("abp.id", abp));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("poia.code"));
        List<Object[]> prices = bneItemService.addRestrictionsVariant(aliasList, criterionList, projectionList, orderList);

        aliasList.clear();
        aliasList.add("bneItem", "b");
        Double total = 0.0;
        for (Object[] item : prices) {
            Map<Month, Double> map = new HashMap();
            projectionList = Projections.projectionList()
                    .add(Projections.sum("quantity"));
            criterionList.clear();
            criterionList.add(Restrictions.eq("b.id", item[0]));
            Double subtotal = (Double) bneScheduleService.addRestrictionsVariant(Arrays.asList(criterionList, projectionList, aliasList)).get(0);

            total += (Double)item[1] * subtotal;
        }
        return total;
    }

    /**
     * @return the mainService
     */
    @Override
    public IDependencyService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IDependencyService mainService) {
        this.mainService = mainService;
    }

    /**
     * @return the sessionBean
     */
    @Override
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    @Override
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets activity budget program service.
     *
     * @return the activityBudgetProgramService
     */
    public IActivityBudgetProgramService getActivityBudgetProgramService() {
        return activityBudgetProgramService;
    }

    /**
     * Sets activity budget program service.
     *
     * @param activityBudgetProgramService the activityBudgetProgramService to set
     */
    public void setActivityBudgetProgramService(IActivityBudgetProgramService activityBudgetProgramService) {
        this.activityBudgetProgramService = activityBudgetProgramService;
    }

    /**
     * Gets funding source service.
     *
     * @return the fundingSourceService
     */
    public IFundingSourceService getFundingSourceService() {
        return fundingSourceService;
    }

    /**
     * Sets funding source service.
     *
     * @param fundingSourceService the fundingSourceService to set
     */
    public void setFundingSourceService(IFundingSourceService fundingSourceService) {
        this.fundingSourceService = fundingSourceService;
    }

    /**
     * Gets budget ceiling service.
     *
     * @return the budgetCeilingService
     */
    public IBudgetCeilingService getBudgetCeilingService() {
        return budgetCeilingService;
    }

    /**
     * Sets budget ceiling service.
     *
     * @param budgetCeilingService the budgetCeilingService to set
     */
    public void setBudgetCeilingService(IBudgetCeilingService budgetCeilingService) {
        this.budgetCeilingService = budgetCeilingService;
    }

    /**
     * Gets abps.
     *
     * @return the abps
     */
    public List<Object[]> getAbps() {
        return abps;
    }

    /**
     * Sets abps.
     *
     * @param abps the abps to set
     */
    public void setAbps(List<Object[]> abps) {
        this.abps = abps;
    }

    /**
     * Gets budget ceiling.
     *
     * @return the budgetCeiling
     */
    public Map<Long, List<Object[]>> getBudgetCeiling() {
        return budgetCeiling;
    }

    /**
     * Sets budget ceiling.
     *
     * @param budgetCeiling the budgetCeiling to set
     */
    public void setBudgetCeiling(Map<Long, List<Object[]>> budgetCeiling) {
        this.budgetCeiling = budgetCeiling;
    }

    /**
     * Gets dependency searcher.
     *
     * @return the dependencySearcher
     */
    public DependencySearcher getDependencySearcher() {
        return dependencySearcher;
    }

    /**
     * Sets dependency searcher.
     *
     * @param dependencySearcher the dependencySearcher to set
     */
    public void setDependencySearcher(DependencySearcher dependencySearcher) {
        this.dependencySearcher = dependencySearcher;
    }

    /**
     * Gets bne item service.
     *
     * @return the bneItemService
     */
    public IBNeItemService getBneItemService() {
        return bneItemService;
    }

    /**
     * Sets bne item service.
     *
     * @param bneItemService the bneItemService to set
     */
    public void setBneItemService(IBNeItemService bneItemService) {
        this.bneItemService = bneItemService;
    }

    /**
     * Gets bne schedule service.
     *
     * @return the bneScheduleService
     */
    public IBNeScheduleService getBneScheduleService() {
        return bneScheduleService;
    }

    /**
     * Sets bne schedule service.
     *
     * @param bneScheduleService the bneScheduleService to set
     */
    public void setBneScheduleService(IBNeScheduleService bneScheduleService) {
        this.bneScheduleService = bneScheduleService;
    }

    /**
     * The type Dependency searcher.
     */
    public class DependencySearcher implements java.io.Serializable {

        private String treeView;

        /**
         * Validate code.
         */
        public void validateCode() {
            dn.core3.hibernate.CriterionList criterionList = new dn.core3.hibernate.CriterionList();
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.count("id"));
            criterionList.add(Restrictions.like("path", selected.getPath(), MatchMode.EXACT));
            criterionList.add(Restrictions.ne("id", selected.getId()));

            criterionList.add(Restrictions.eq("active", true));
            int count = ((Number) getMainService().getListByRestrictions(projectionList, criterionList).get(0)).intValue();
            if (count == 0) {
                String tempPath = selected.getPath().trim();
                String code = "";
                projectionList = Projections.projectionList()
                        .add(Projections.id())
                        .add(Projections.property("path"))
                        .add(Projections.property("name"));
                Dependency parent = null;
                for (int i = selected.getPath().length(); i > 3; i--) {
                    criterionList.clear();
                    code += tempPath.charAt(tempPath.length() - 1);
                    tempPath = tempPath.substring(0, tempPath.length() - 1);
                    criterionList.add(Restrictions.like("path", tempPath, MatchMode.EXACT));
                    try {
                        List<Dependency> dependencys = getMainService().getListByRestrictions(new Object[]{criterionList});
                        parent = (Dependency) dependencys.get(0);
                        if (parent != null) {
                            break;
                        }
                    } catch (Exception e) {

                    }
                }
                if (parent != null) {
                    Integer id = parent.getId();
                    dn.core3.hibernate.AliasList aliasList = new dn.core3.hibernate.AliasList();
                    aliasList.add("childrens", "c");
                    treeView = "<ul>"
                            + "<li>"
                            + "<span> " + parent.getPath() + " - " + parent.getName() + " </span>"
                            + "</li>"
                            + "</ul>";
                    projectionList = Projections.projectionList()
                            .add(Projections.id())
                            .add(Projections.property("path"))
                            .add(Projections.property("name"));
                    while (true) {
                        criterionList.clear();
                        criterionList.add(Restrictions.eq("c.id", id));
                        try {
                            Object[] o = (Object[]) getMainService().getListByRestrictions(aliasList, projectionList, criterionList).get(0);
                            id = (Integer) o[0];
                            treeView = "<ul>"
                                    + "<li>"
                                    + "<span>" + o[1] + " - " + o[2] + "</span>"
                                    + treeView
                                    + "</li>"
                                    + "</ul>";
                        } catch (Exception e) {
                            break;
                        }
                    }
                } else {
                    treeView = "<span class='text-muted font-lg'>Ninguna dependencia superior</span>";
                }
            } else {
                new SmartMessage("ERROR :Codigo Invalido", "El codigo introducido ya pertenece a otro u otra dependencia ya comienza con este codigo", SmartMessage.SmartColor.DANGER, 4000L, "fa fa-warning shake animated").execute();
            }
        }

        /**
         * Gets tree view.
         *
         * @return the treeView
         */
        public String getTreeView() {
            return treeView;
        }

        /**
         * Sets tree view.
         *
         * @param treeView the treeView to set
         */
        public void setTreeView(String treeView) {
            this.treeView = treeView;
        }

    }
}

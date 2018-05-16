/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.bean.view;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
import edu.unas.spoi.bne.service.interfac.IBNeScheduleService;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.poi.service.interfac.IPOIActivityService;
import edu.unas.spoi.poi.service.interfac.IPOIScheduleService;
import edu.unas.spoi.poi.service.interfac.IPOIService;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import edu.unas.spoi.report.StaticReport;
import edu.unas.spoi.report.enumerated.ContentType;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderFactory;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.Pagination;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type View b need bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ViewBNeedBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{poiService}")
    private IPOIService mainService;
    @ManagedProperty(value = "#{poiActivityService}")
    private IPOIActivityService poiActivityService;
    @ManagedProperty(value = "#{poiScheduleService}")
    private IPOIScheduleService poiScheduleService;
    @ManagedProperty(value = "#{bneScheduleService}")
    private IBNeScheduleService bneScheduleService;
    @ManagedProperty(value = "#{bneItemService}")
    private IBNeItemService bneItemService;
    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService fundingSourceService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    private Pagination<Object[]> pagination;

    private Report report;
    private OrderFactory orderFactory;
    private FundingSourceSearcher fundingSourceSearcher;
    private String product;
    private Dependency currentDependency;
    private List<Month> months;

    private Integer fundingSource;
    private Long selectedId;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        months = Arrays.asList(Month.values());
        report = new Report();
        fundingSourceSearcher = new FundingSourceSearcher();
        orderFactory = new OrderFactory(new OrderList());
        orderFactory.setDefaultOrder(Order.asc("id"));

        pagination = new Pagination<Object[]>(bneItemService) {

            @Override
            public void search(int page, Object... variant) {
                super.search(page, variant); //To change body of generated methods, choose Tools | Templates.
                AliasList aliasList = new AliasList();
                ProjectionList projectionList;
                CriterionList criterionList = new CriterionList();
                aliasList.add("bneItem", "b");
                for (Object[] item : data) {
                    Map<Month, Double> map = new HashMap();
                    projectionList = Projections.projectionList()
                            .add(Projections.property("quantity"));
                    Double total = 0.0;
                    for (Month month : months) {
                        criterionList.clear();
                        criterionList.add(Restrictions.eq("b.id", item[0]));
                        criterionList.add(Restrictions.eq("month", month));
                        Double current;
                        try {
                            current = (Double) bneScheduleService.addRestrictionsVariant(Arrays.asList(criterionList, projectionList, aliasList)).get(0);
                        } catch (Exception e) {
                            current = 0.0;
                        }
                        total += current;
                        map.put(month, current);
                    }
                    item[13] = map;
                    item[11] = total;
                }
            }

        };
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        updateItems();
    }

    /**
     * Refresh.
     */
    public void refresh() {
        fundingSource = null;
        product = "";
        orderFactory.removeAll();
        updateItems();
        fundingSourceSearcher.update();
    }

    /**
     * Begin.
     *
     * @param dependencyId the dependency id
     */
    public void begin(Integer dependencyId) {
        currentDependency = dependencyService.getById(dependencyId);
        refresh();
    }

    /**
     * Update items.
     */
    public void updateItems() {
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("poia.id"))
                .add(Projections.property("poia.code"))
                .add(Projections.property("poia.detail"))
                .add(Projections.property("fs.code"))
                .add(Projections.property("fs.name"))
                .add(Projections.property("productCode"))
                .add(Projections.property("productName"))
                .add(Projections.property("c.path"))
                .add(Projections.property("c.name"))
                .add(Projections.property("unitPrice"))
                .add(Projections.property("id"))
                .add(Projections.property("active"))
                .add(Projections.property("id"));
        AliasList aliasList = new AliasList();
        aliasList.add("poiActivity", "poia");
        aliasList.add("poia.poi", "poi");
        aliasList.add("fundingSource", "fs");
        aliasList.add("classifier", "c");
        CriterionList criterionList = new CriterionList();
        if (!sessionBean.authorize("R_BN")) {
            criterionList.add(Restrictions.eq("active", true));
        }
        criterionList.add(Restrictions.eq("poi.dependency", currentDependency));
        criterionList.add(Restrictions.eq("poi.year", sessionBean.getOperationYear()));
        if (fundingSource != null) {
            criterionList.add(Restrictions.eq("fs.id", fundingSource));
        }
        int i = product.length();
        if (i != 0) {
            criterionList.add(Restrictions.like("productName", product, MatchMode.ANYWHERE).ignoreCase());
        }
        pagination.search(1, aliasList, criterionList, projectionList, orderFactory.make());
    }

    /**
     * Gets main service.
     *
     * @return the mainService
     */
    public IPOIService getMainService() {
        return mainService;
    }

    /**
     * Sets main service.
     *
     * @param mainService the mainService to set
     */
    public void setMainService(IPOIService mainService) {
        this.mainService = mainService;
    }

    /**
     * Gets session bean.
     *
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * Sets session bean.
     *
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Gets poi activity service.
     *
     * @return the poiActivityService
     */
    public IPOIActivityService getPoiActivityService() {
        return poiActivityService;
    }

    /**
     * Sets poi activity service.
     *
     * @param poiActivityService the poiActivityService to set
     */
    public void setPoiActivityService(IPOIActivityService poiActivityService) {
        this.poiActivityService = poiActivityService;
    }

    /**
     * Gets poi schedule service.
     *
     * @return the poiScheduleService
     */
    public IPOIScheduleService getPoiScheduleService() {
        return poiScheduleService;
    }

    /**
     * Sets poi schedule service.
     *
     * @param poiScheduleService the poiScheduleService to set
     */
    public void setPoiScheduleService(IPOIScheduleService poiScheduleService) {
        this.poiScheduleService = poiScheduleService;
    }

    /**
     * Gets dependency service.
     *
     * @return the dependencyService
     */
    public IDependencyService getDependencyService() {
        return dependencyService;
    }

    /**
     * Sets dependency service.
     *
     * @param dependencyService the dependencyService to set
     */
    public void setDependencyService(IDependencyService dependencyService) {
        this.dependencyService = dependencyService;
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
     * Gets current dependency.
     *
     * @return the currentDependency
     */
    public Dependency getCurrentDependency() {
        return currentDependency;
    }

    /**
     * Sets current dependency.
     *
     * @param currentDependency the currentDependency to set
     */
    public void setCurrentDependency(Dependency currentDependency) {
        this.currentDependency = currentDependency;
    }

    /**
     * Gets months.
     *
     * @return the months
     */
    public List<Month> getMonths() {
        return months;
    }

    /**
     * Sets months.
     *
     * @param months the months to set
     */
    public void setMonths(List<Month> months) {
        this.months = months;
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
     * Gets selected id.
     *
     * @return the selectedId
     */
    public Long getSelectedId() {
        return selectedId;
    }

    /**
     * Sets selected id.
     *
     * @param selectedId the selectedId to set
     */
    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
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
     * Gets report.
     *
     * @return the report
     */
    public Report getReport() {
        return report;
    }

    /**
     * Sets report.
     *
     * @param report the report to set
     */
    public void setReport(Report report) {
        this.report = report;
    }

    /**
     * Gets funding source searcher.
     *
     * @return the fundingSourceSearcher
     */
    public FundingSourceSearcher getFundingSourceSearcher() {
        return fundingSourceSearcher;
    }

    /**
     * Sets funding source searcher.
     *
     * @param fundingSourceSearcher the fundingSourceSearcher to set
     */
    public void setFundingSourceSearcher(FundingSourceSearcher fundingSourceSearcher) {
        this.fundingSourceSearcher = fundingSourceSearcher;
    }

    /**
     * Gets funding source.
     *
     * @return the fundingSource
     */
    public Integer getFundingSource() {
        return fundingSource;
    }

    /**
     * Sets funding source.
     *
     * @param fundingSource the fundingSource to set
     */
    public void setFundingSource(Integer fundingSource) {
        this.fundingSource = fundingSource;
    }

    /**
     * Gets pagination.
     *
     * @return the pagination
     */
    public Pagination<Object[]> getPagination() {
        return pagination;
    }

    /**
     * Sets pagination.
     *
     * @param pagination the pagination to set
     */
    public void setPagination(Pagination<Object[]> pagination) {
        this.pagination = pagination;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Gets order factory.
     *
     * @return the orderFactory
     */
    public OrderFactory getOrderFactory() {
        return orderFactory;
    }

    /**
     * Sets order factory.
     *
     * @param orderFactory the orderFactory to set
     */
    public void setOrderFactory(OrderFactory orderFactory) {
        this.orderFactory = orderFactory;
    }

    /**
     * The type Report.
     */
    public class Report implements java.io.Serializable {

        private List<StaticReport> staticReports;
        private StaticReport selected;
        private String parameters;
        private ContentType contentType;

        /**
         * Instantiates a new Report.
         */
        public Report() {
            staticReports = new ArrayList<>();
            StaticReport report = new StaticReport("1", "Cuad. de Nec. agrupado por fte. financ.", "BoxNeed_gClassi.jasper");
            report.addField(new StaticReport.FieldParameter() {
                @Override
                public String makeHtml() {
                    List<FundingSource> fundingSources = getFundingSourceService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("active", true)));
                    String html = "<div class='form-group'>"
                            + "<label class='control-label col-md-2'>Fte. finc.</label>"
                            + "<div class='col-md-10'>"
                            + "<select id='fs_id' name='fs_id' class='form-control parameter-report'>"
                            + "<option/>";
                    for (FundingSource fs : fundingSources) {
                        html += "<option value='" + fs.getId() + "'>" + fs.getName() + "</option>";
                    }
                    html += "</select></div></div>";
                    return html;
                }

                @Override
                public String makeJavascript() {
                    return "";
                }
            });
            staticReports.add(report);
            report = new StaticReport("2", "Cuad. de Nec. agrupado por actividades", "BoxNeed_Activity.jasper");
            report.addField(new StaticReport.FieldParameter() {
                @Override
                public String makeHtml() {
                    List<FundingSource> fundingSources = getFundingSourceService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("active", true)));
                    String html = "<div class='form-group'>"
                            + "<label class='control-label col-md-2'>Fte. finc.</label>"
                            + "<div class='col-md-10'>"
                            + "<select id='fs_id' name='fs_id' class='form-control parameter-report'>"
                            + "<option/>";
                    for (FundingSource fs : fundingSources) {
                        html += "<option value='" + fs.getId() + "'>" + fs.getName() + "</option>";
                    }
                    html += "</select></div></div>";
                    return html;
                }

                @Override
                public String makeJavascript() {
                    return "";
                }
            });
            staticReports.add(report);
            report = new StaticReport("3", "Informe a nivel estructura funcional programática", "/box-need/functional-programmatic/boxneed-functional-programmatic.jasper") {
                @Override
                public Map<String, Object> getBasicParameters() {
                    Map<String, Object> params = super.getBasicParameters(); //To change body of generated methods, choose Tools | Templates.
                    params.put("PERIODICITY-VALUE", StaticReport.loadFromWebPages("/box-need/functional-programmatic/boxneed-functional-programmatic-periodicity-value.jasper"));
                    params.put("PERIODICITY-H", StaticReport.loadFromWebPages("/box-need/functional-programmatic/boxneed-functional-programmatic-periodicity-h.jasper"));
                    params.put("UNAS", ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                    params.put("id_dependency", currentDependency.getId());
                    params.put("year", getSessionBean().getOperationYear());
                    return params;
                }

            };
            staticReports.add(report);
            report = new StaticReport("4", "Informe a nivel de especifica de gasto", "/box-need/expense-specification/boxneed-expense-specification.jasper") {
                @Override
                public Map<String, Object> getBasicParameters() {
                    Map<String, Object> params = super.getBasicParameters(); //To change body of generated methods, choose Tools | Templates.

                    params.put("PERIODICITY-VALUE", StaticReport.loadFromWebPages("/box-need/expense-specification/boxneed-expense-specification-periodicity-value.jasper"));
                    params.put("PERIODICITY-H", StaticReport.loadFromWebPages("/box-need/expense-specification/boxneed-expense-specification-periodicity-h.jasper"));
                    params.put("UNAS", ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                    params.put("id_dependency", currentDependency.getId());
                    params.put("year", getSessionBean().getOperationYear());
                    return params;
                }
            };
            staticReports.add(report);
            report = new StaticReport("5", "Informe a nivel de actividad operativa", "/box-need/operative-activity/boxneed-operative-activity.jasper") {
                @Override
                public Map<String, Object> getBasicParameters() {
                    Map<String, Object> params = super.getBasicParameters(); //To change body of generated methods, choose Tools | Templates.
                    params.put("PERIODICITY-VALUE", StaticReport.loadFromWebPages("/box-need/operative-activity/boxneed-operative-activity-periodicity-value.jasper"));
                    params.put("PERIODICITY-H", StaticReport.loadFromWebPages("/box-need/operative-activity/boxneed-operative-activity-periodicity-h.jasper"));
                    params.put("UNAS", ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                    params.put("id_dependency", currentDependency.getId());
                    params.put("year", getSessionBean().getOperationYear());
                    return params;
                }
            };
            staticReports.add(report);
        }

        /**
         * Refresh.
         */
        public void refresh() {
            selected = null;
            contentType = null;
            parameters = null;
        }

        /**
         * Execute string.
         *
         * @return the string
         */
        public String execute() {
            Map<String, Object> map = selected.getBasicParameters();
            map.put("dependency_id", getCurrentDependency().getId());
            map.put("year", getSessionBean().getOperationYear());
            String[] maps = parameters.split(";");
            for (String m : maps) {
                String[] entry = m.split("=");
                if (entry.length == 1) {
                    map.put(entry[0], null);
                } else {
                    map.put(entry[0], entry[1]);
                }
            }
            return selected.execute((JRExporter) contentType.getExporter(), map, contentType, getSessionBean());
        }

        /**
         * Sets selected code.
         *
         * @param code the code
         */
        public void setSelectedCode(String code) {
            for (StaticReport report : staticReports) {
                if (Objects.equals(report.getCode(), code)) {
                    selected = report;
                    return;
                }
            }
            selected = null;
        }

        /**
         * Gets selected code.
         *
         * @return the selected code
         */
        public String getSelectedCode() {
            try {
                return selected.getCode();
            } catch (NullPointerException npe) {
                return null;
            }
        }

        /**
         * Sets content type.
         *
         * @param c the c
         */
        public void setContentType(String c) {
            try {
                contentType = ContentType.valueOf(c.toUpperCase());
            } catch (Exception e) {

            }
        }

        /**
         * Gets content type.
         *
         * @return the content type
         */
        public String getContentType() {
            try {
                return contentType.name();
            } catch (NullPointerException npe) {
                return null;
            }
        }

        /**
         * Gets static reports.
         *
         * @return the staticReports
         */
        public List<StaticReport> getStaticReports() {
            return staticReports;
        }

        /**
         * Sets static reports.
         *
         * @param staticReports the staticReports to set
         */
        public void setStaticReports(List<StaticReport> staticReports) {
            this.staticReports = staticReports;
        }

        /**
         * Gets parameters.
         *
         * @return the parameters
         */
        public String getParameters() {
            return parameters;
        }

        /**
         * Sets parameters.
         *
         * @param parameters the parameters to set
         */
        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public StaticReport getSelected() {
            return selected;
        }

        /**
         * Sets selected.
         *
         * @param selected the selected to set
         */
        public void setSelected(StaticReport selected) {
            this.selected = selected;
        }
    }

    /**
     * The type Funding source searcher.
     */
    public class FundingSourceSearcher implements Serializable {

        private List<Object[]> data;
        private Integer selected;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("code"))
                    .add(Projections.property("name"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("code"));
            data = getFundingSourceService().addRestrictionsVariant(projectionList, criterionList, orderList);
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
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            return selected;
        }

        /**
         * Sets selected.
         *
         * @param selected the selected to set
         */
        public void setSelected(Integer selected) {
            this.selected = selected;
        }

    }
}

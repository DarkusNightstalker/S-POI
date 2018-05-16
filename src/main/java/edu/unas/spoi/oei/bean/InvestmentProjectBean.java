/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.service.interfac.IInvestmentProjectService;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.SmartMessage;
import edu.unas.spoi.dao.util.PGSqlUtil;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.Pagination;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Investment project bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class InvestmentProjectBean implements java.io.Serializable, ILoadable {

    private String search;
    private Integer dependency;
    private Boolean approved;
    private Export export;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{investmentProjectService}")
    private IInvestmentProjectService investmentProjectService;
    private Pagination<Object[]> pagination;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        pagination = new Pagination(investmentProjectService);
        export = new Export();
    }

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
        search = "";
        approved = null;
        dependency = null;
        search();
    }

    /**
     * Search.
     */
    public void search() {
        search = search.trim();
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("title"))
                .add(Projections.property("approved"))
                .add(Projections.property("d.path"))
                .add(Projections.property("d.name"))
                .add(Projections.property("createDate"))
                .add(Projections.property("editDate"))
                .add(Projections.property("active"));
        AliasList aliasList = new AliasList();
        aliasList.add("dependency", "d");
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("createDate"));
        CriterionList criterionList = new CriterionList()
                ._add(Restrictions.eq("active", true));
        if(!sessionBean.isSuperAdmin()){
            criterionList.add(Restrictions.eq("dependency", sessionBean.getCurrentDependency()));
        }
        if (dependency != null) {
            criterionList.add(Restrictions.eq("d.id", dependency));
        }
        
        if (search.length() != 0) {
            criterionList.add(Restrictions.like("title", search, MatchMode.ANYWHERE).ignoreCase());
        }
        if (approved != null) {
            criterionList.add(Restrictions.eq("operational", approved));
        }
        pagination.search(1, projectionList, orderList, criterionList,aliasList);
    }

    /**
     * Gets search.
     *
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * Sets search.
     *
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * Gets approved.
     *
     * @return the approved
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     * Sets approved.
     *
     * @param approved the approved to set
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
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
     * Gets investment project service.
     *
     * @return the investmentProjectService
     */
    public IInvestmentProjectService getInvestmentProjectService() {
        return investmentProjectService;
    }

    /**
     * Sets investment project service.
     *
     * @param investmentProjectService the investmentProjectService to set
     */
    public void setInvestmentProjectService(IInvestmentProjectService investmentProjectService) {
        this.investmentProjectService = investmentProjectService;
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
     * Gets export.
     *
     * @return the export
     */
    public Export getExport() {
        return export;
    }

    /**
     * Sets export.
     *
     * @param export the export to set
     */
    public void setExport(Export export) {
        this.export = export;
    }

    /**
     * Gets dependency.
     *
     * @return the dependency
     */
    public Integer getDependency() {
        return dependency;
    }

    /**
     * Sets dependency.
     *
     * @param dependency the dependency to set
     */
    public void setDependency(Integer dependency) {
        this.dependency = dependency;
    }

    /**
     * The type Export.
     */
    public class Export implements java.io.Serializable {

        private Boolean activeHeader;
        private Boolean allowDropped;
        private ContentType contentType;

        /**
         * To xlsx string.
         *
         * @return the string
         */
        public String toXlsx() {
            contentType = ContentType.XLSX;
            return execute(new JRXlsxExporter());
        }

        /**
         * To pdf string.
         *
         * @return the string
         */
        public String toPdf() {
            contentType = ContentType.PDF;
            return execute(new JRPdfExporter());
        }

        /**
         * To docx string.
         *
         * @return the string
         */
        public String toDocx() {
            contentType = ContentType.DOCX;
            return execute(new JRDocxExporter());
        }

        /**
         * To txt string.
         *
         * @return the string
         */
        public String toTxt() {
            contentType = ContentType.TXT;
            return execute(new JRTextExporter());
        }

        /**
         * To sy base string.
         *
         * @return the string
         */
        public String toSyBase() {
            return "index?faces-redirect=true";
        }

        /**
         * To html string.
         *
         * @return the string
         */
        public String toHtml() {
            contentType = ContentType.HTML;
            return execute(new JRHtmlExporter());
        }

        private String execute(JRExporter exporter) {
            try {
                Map<String, Object> param = new HashMap<>();
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/Dependency.jasper");
                param.put("UNAS", servletContext.getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                param.put("active", allowDropped);
                param.put("printHeader", activeHeader);
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, PGSqlUtil.getJdbcPostGresSQL().getConnection());
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType(contentType.getMimeType());
                response.addHeader("Content-disposition", "attachment; filename=Programas_presupuestales." + contentType.name().toLowerCase());
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporter.exportReport();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception ex) {
                getSessionBean().getMessages().add(new SmartMessage("ERROR EN REPORTE!", ex.toString(), SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shaked animated"));
                Logger.getLogger(BudgetProgramBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "index?faces-redirect=true";
        }

        /**
         * Gets active header.
         *
         * @return the activeHeader
         */
        public Boolean getActiveHeader() {
            return activeHeader;
        }

        /**
         * Sets active header.
         *
         * @param activeHeader the activeHeader to set
         */
        public void setActiveHeader(Boolean activeHeader) {
            this.activeHeader = activeHeader;
        }

        /**
         * Gets allow dropped.
         *
         * @return the allowDropped
         */
        public Boolean getAllowDropped() {
            return allowDropped;
        }

        /**
         * Sets allow dropped.
         *
         * @param allowDropped the allowDropped to set
         */
        public void setAllowDropped(Boolean allowDropped) {
            this.allowDropped = allowDropped;
        }

        /**
         * Gets content type.
         *
         * @return the contentType
         */
        public ContentType getContentType() {
            return contentType;
        }

        /**
         * Sets content type.
         *
         * @param contentType the contentType to set
         */
        public void setContentType(ContentType contentType) {
            this.contentType = contentType;
        }

    }
}

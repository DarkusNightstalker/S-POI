/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.bean;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.exception.ImportException;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.model.UoM;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IUoMService;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.ImportUtils;
import edu.unas.spoi.util.SmartMessage;
import edu.unas.spoi.dao.util.PGSqlUtil;
import gkfire.web.util.Pagination;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Activity budget program bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ActivityBudgetProgramBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{productBudgetProgramService}")
    private IProductBudgetProgramService productBudgetProgramService;
    @ManagedProperty(value = "#{budgetProgramService}")
    private IBudgetProgramService budgetProgramService;
    @ManagedProperty(value = "#{uomService}")
    private IUoMService uomService;

    private Import import_;
    private Export export;
    private Pagination<Object[]> pagination;
    private String code;
    private String name;
    private Long id;

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        import_ = new Import();
        export = new Export();
        pagination = new Pagination(activityBudgetProgramService);
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
        code = "";
        name = "";
        search();
    }

    /**
     * Search.
     */
    public void search() {
        code = code.trim();
        name = name.trim();
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("code"))
                .add(Projections.property("name"))
                .add(Projections.property("functionalSequence"));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("code"));
        AliasList aliasList = new AliasList();
        aliasList.add("budgetProgram", "bp");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        if (code.length() != 0) {
            criterionList.add(Restrictions.like("code", code, MatchMode.ANYWHERE));
        }
        if (name.length() != 0) {
            criterionList.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }

        pagination.search(1, aliasList, projectionList, criterionList, orderList);
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
     * Gets product budget program service.
     *
     * @return the productBudgetProgramService
     */
    public IProductBudgetProgramService getProductBudgetProgramService() {
        return productBudgetProgramService;
    }

    /**
     * Sets product budget program service.
     *
     * @param productBudgetProgramService the productBudgetProgramService to set
     */
    public void setProductBudgetProgramService(IProductBudgetProgramService productBudgetProgramService) {
        this.productBudgetProgramService = productBudgetProgramService;
    }

    /**
     * Gets budget program service.
     *
     * @return the budgetProgramService
     */
    public IBudgetProgramService getBudgetProgramService() {
        return budgetProgramService;
    }

    /**
     * Sets budget program service.
     *
     * @param budgetProgramService the budgetProgramService to set
     */
    public void setBudgetProgramService(IBudgetProgramService budgetProgramService) {
        this.budgetProgramService = budgetProgramService;
    }

    /**
     * Gets uom service.
     *
     * @return the uomService
     */
    public IUoMService getUomService() {
        return uomService;
    }

    /**
     * Sets uom service.
     *
     * @param uomService the uomService to set
     */
    public void setUomService(IUoMService uomService) {
        this.uomService = uomService;
    }

    /**
     * Gets import.
     *
     * @return the import_
     */
    public Import getImport_() {
        return import_;
    }

    /**
     * Sets import.
     *
     * @param import_ the import_ to set
     */
    public void setImport_(Import import_) {
        this.import_ = import_;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * The type Budget program searcher.
     */
    public class BudgetProgramSearcher implements java.io.Serializable {

    }

    /**
     * The type Import.
     */
    public class Import implements java.io.Serializable {

        private Part file;
        private Integer percentLoad;
        private String message;
        private Integer totalRecords;
        private ImportUtils.State state;
        private String content;

        /**
         * Refresh.
         */
        public void refresh() {
            file = null;
            message = null;
            totalRecords = 0;
            percentLoad = 0;
            state = ImportUtils.State.LOAD;
        }

        /**
         * Begin.
         */
        @Asynchronous
        public void begin() {
            List<ActivityBudgetProgram> list = new ArrayList();
            try {
                Object file = null;
                try {
                    file = ImportUtils.readFile(this.file);
                } catch (Exception e) {
                    throw new ImportException("ERROR : Formato de archivo desconocido", "Se ha producido un error al leer el archivo");
                }
                state = ImportUtils.State.READING;
                totalRecords = ImportUtils.countRows(file);
                for (int i = 1; i <= totalRecords; i++) {
                    Object[] o = null;
                    try {
                        o = ImportUtils.readRow(file, i, 10);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Contenido no legible", e.getMessage());
                    }
                    String code = null;
                    try {
                        code = o[0].toString().trim();
                        boolean exist = getActivityBudgetProgramService().existCode(code, null);
                        if (exist) {
                            content += rowAsError(o);
                            throw new ImportException("ERROR : Codigo Invalido", "El codigo ya existe");
                        }
                        for (ActivityBudgetProgram item : list) {
                            if (item.getCode().equalsIgnoreCase(code)) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", "El código se repite en el documento");
                            }
                        }
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Codigo Invalido", "El codigo no puede ser nulo o vacio");
                    }

                    ActivityBudgetProgram item = new ActivityBudgetProgram();
                    item.setCode(code);
                    item.setCreateDate(Calendar.getInstance().getTime());
                    item.setCreateUser(getSessionBean().getCurrentUser());
                    try {
                        item.setFunctionalSequence((String) o[1]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Secuencia Funcional Invalido", "La secuencia funcional debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Secuencia Funcional Invalido", "La Secuencia Funcional debe ser un texto");
                    }
                    if (o[1] == null || "".equalsIgnoreCase((String) o[1])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Secuencia Funcional Invalido", "La Secuencia Funcional no puede ser nulo o vacio");
                    }

                    try {
                        item.setName((String) o[2]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    }
                    if (o[2] == null || "".equalsIgnoreCase((String) o[2])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre no puede ser nulo o vacio");
                    }

                    try {
                        item.setFunction((String) o[5]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    }
                    if (o[5] == null || "".equalsIgnoreCase((String) o[2])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre no puede ser nulo o vacio");
                    }

                    try {
                        item.setFunctionalDivision((String) o[6]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    }
                    if (o[6] == null || "".equalsIgnoreCase((String) o[6])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre no puede ser nulo o vacio");
                    }

                    try {
                        item.setFunctionalGroup((String) o[7]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Grupo Funcional Invalido", "El Grupo Funcional debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Grupo Funcional Invalido", "El Grupo Funcional debe ser un texto");
                    }
                    if (o[7] == null || "".equalsIgnoreCase((String) o[7])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Grupo Funcional Invalido", "El Grupo Funcional no puede ser nulo o vacio");
                    }

                    try {
                        item.setGoal((String) o[8]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Finalidad Invalido", "El Finalidad debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Finalidad Invalido", "El Finalidad debe ser un texto");
                    }
                    if (o[8] == null || "".equalsIgnoreCase((String) o[8])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Finalidad Invalido", "El Finalidad no puede ser nulo o vacio");
                    }

                    String budgetProgram = (String) o[3];
                    String productBudgetProgram = (String) o[4];
                    String unity = (String) o[9];
                    try {
                        BudgetProgram bp = (BudgetProgram) getBudgetProgramService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("code", budgetProgram))).get(0);
                        ProductBudgetProgram pbp = (ProductBudgetProgram) getProductBudgetProgramService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("code", productBudgetProgram))).get(0);
                        UoM uom = (UoM) getUomService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("code", unity))).get(0);
                        
                        item.setBudgetProgram(bp);
                        item.setProductBudgetProgram(pbp);
                        item.setUom(uom);
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Datos Invalidos", "Algunos codigos son incorrectos");
                    }
                    content += "<tr class='animated fadeInRight'><td><span class='fa fa-check text-success'></span></td><td>" + item.getCode() + "</td><td>" + item.getFunctionalSequence()+ "</td><td>" + item.getName() + "</td><td>" + item.getBudgetProgram().getCode() + "</td><td>" + item.getProductBudgetProgram().getCode()+ "</td><td>" + item.getFunction() + "</td><td>" + item.getFunctionalDivision()+ "</td><td>" + item.getFunctionalGroup() + "</td><td>" + item.getGoal() + "</td><td>" + item.getUom().getCode() + "</td></tr>";
                    percentLoad = (i * 50) / totalRecords;
                    list.add(item);
                }
                state = ImportUtils.State.PROCESS;
                int tempPercent = percentLoad;
                for (int i = 0; i < list.size(); i++) {
                    percentLoad = tempPercent + (((i + 1) * 50) / totalRecords);
                    getActivityBudgetProgramService().saveOrUpdate(list.get(i));
                }
                state = ImportUtils.State.SUCCESS;
                message = "Resultado satisfactorio";
            } catch (ImportException e) {
                state = ImportUtils.State.ERROR;
            }
        }

        private String rowAsError(Object[] o) {
            return "<tr class='danger animated fadeInRight'><td><span class='fa fa-times text-danger'></span></td><td>" + o[0] + "</td><td>" + o[1] + "</td><td>" + o[2] + "</td><td>" + o[3] + "</td><td>" + o[4] + "</td><td>" + o[5] + "</td><td>" + o[6] + "</td><td>" + o[7] + "</td><td>" + o[8] + "</td><td>" + o[9] + "</td></tr>";
        }

        /**
         * Gets render content.
         *
         * @return the render content
         */
        public String getRenderContent() {
            String s = content;
            content = "";
            return s;
        }

        /**
         * Gets file.
         *
         * @return the file
         */
        public Part getFile() {
            return file;
        }

        /**
         * Sets file.
         *
         * @param file the file
         */
        public void setFile(Part file) {
            this.file = file;
        }

        /**
         * Gets percent load.
         *
         * @return the percent load
         */
        public Integer getPercentLoad() {
            return percentLoad;
        }

        /**
         * Sets percent load.
         *
         * @param percentLoad the percent load
         */
        public void setPercentLoad(Integer percentLoad) {
            this.percentLoad = percentLoad;
        }

        /**
         * Gets message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets message.
         *
         * @param message the message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Gets state.
         *
         * @return the state
         */
        public ImportUtils.State getState() {
            return state;
        }

        /**
         * Sets state.
         *
         * @param state the state
         */
        public void setState(ImportUtils.State state) {
            this.state = state;
        }

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
                InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/ActivityBudgetProgram.jasper");
                param.put("UNAS", servletContext.getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                param.put("active", allowDropped);
                param.put("printHeader", activeHeader);
              
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, PGSqlUtil.getJdbcPostGresSQL().getConnection());
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType(contentType.getMimeType());
                response.addHeader("Content-disposition", "attachment; filename=Activiades_presupuestales." + contentType.name().toLowerCase());
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

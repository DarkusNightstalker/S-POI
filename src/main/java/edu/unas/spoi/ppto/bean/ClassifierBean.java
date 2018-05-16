/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.bean;

import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.exception.ImportException;
import edu.unas.spoi.oei.bean.BudgetProgramBean;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.IClassifierTypeService;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.ErrorMessage;
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
import org.hibernate.criterion.Order;

/**
 * The type Classifier bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ClassifierBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{classifierTypeService}")
    private IClassifierTypeService classifierTypeService;

    private Import import_;
    private Export export;
    private Long id;
    
    private List<Object[]> types;
    private Pagination<Object[]> pagination;
    private String path;
    private String name;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        import_ = new Import();
        export = new Export();
        pagination = new Pagination(classifierService);
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
        path = "";
        name = "";
        search();
    }

    /**
     * Search.
     */
    public void search() {
        path = path.trim();
        name = name.trim();
        types = classifierTypeService.getForClassifiers(sessionBean.getOperationYear());
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("path"))
                .add(Projections.property("name"));
        OrderList orderList = new OrderList();
        orderList.add(Order.asc("path"));
        CriterionList criterionList = new CriterionList();
        if (path.length() != 0) {
            criterionList.add(Restrictions.like("path", path, MatchMode.START));
        }
        if (name.length() != 0) {
            criterionList.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        pagination.search(1, projectionList, criterionList, orderList);
    }

    /**
     * Local code string.
     *
     * @param indexClassifier the index classifier
     * @param indexType       the index type
     * @return the string
     */
    public String localCode(Integer indexClassifier, Integer indexType) {
        Object[] classifier = pagination.getData().get(indexClassifier);
        Object[] type = types.get(indexType);

        try {
            String path = (String) classifier[1];
            Short length = (Short) type[2];

            String code = path.substring(0, length).trim();
            path = path.substring(length, path.length());

            classifier[1] = path;

            return code;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
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
     * Gets classifier service.
     *
     * @return the classifierService
     */
    public IClassifierService getClassifierService() {
        return classifierService;
    }

    /**
     * Sets classifier service.
     *
     * @param classifierService the classifierService to set
     */
    public void setClassifierService(IClassifierService classifierService) {
        this.classifierService = classifierService;
    }

    /**
     * Gets classifier type service.
     *
     * @return the classifierTypeService
     */
    public IClassifierTypeService getClassifierTypeService() {
        return classifierTypeService;
    }

    /**
     * Sets classifier type service.
     *
     * @param classifierTypeService the classifierTypeService to set
     */
    public void setClassifierTypeService(IClassifierTypeService classifierTypeService) {
        this.classifierTypeService = classifierTypeService;
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
     * Gets types.
     *
     * @return the types
     */
    public List<Object[]> getTypes() {
        return types;
    }

    /**
     * Sets types.
     *
     * @param types the types to set
     */
    public void setTypes(List<Object[]> types) {
        this.types = types;
    }


    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
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
            List<Classifier> list = new ArrayList();
            System.out.println("Comenzando");
            try {
                Object file = null;
                try {
                    file = ImportUtils.readFile(this.file);
                } catch (Exception e) {
                    throw new ImportException("ERROR : Formato de archivo desconocido", "Se ha producido un error al leer el archivo");
                }
                System.out.println("Archivo Leido");
                state = ImportUtils.State.READING;
                totalRecords = ImportUtils.countRows(file);
                for (int i = 1; i <= totalRecords; i++) {
                    Object[] o = null;
                    try {
                        o = ImportUtils.readRow(file, i, 3);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Contenido no legible", e.getMessage());
                    }
                    Classifier parent = null;
                    String code = null;
                    try {
                        try {
                            code = new Double(o[0].toString()).intValue() + "";
                        } catch (NumberFormatException e) {
                            code = o[0].toString().trim();
                        }
                        ErrorMessage errorMessage = ClassifierBean.this.getClassifierService().verifyCode(code.trim(), getSessionBean().getOperationYear(), -1L);
                        if (errorMessage != null) {
                            if (errorMessage.getCode().equalsIgnoreCase("CG-0004")) {
                                for (Classifier item : list) {
                                    if (item.getPath().equalsIgnoreCase(errorMessage.getOptions()[0].toString())) {
                                        parent = item;
                                        break;
                                    }
                                }
                            }
                            if (parent == null) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", errorMessage.getContent());
                            }
                        }
                        for (Classifier item : list) {
                            if (item.getPath().equalsIgnoreCase(code)) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", errorMessage.getContent());
                            }
                        }
                    } catch (NullPointerException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Codigo Invalido", "El codigo no puede ser nulo o vacio");
                    }

                    Classifier item = new Classifier();
                    item.setPath(code);
                    item.setClassifierType(ClassifierBean.this.getClassifierTypeService().getByDigits(item.getPath().length()));
                    item.setCreateDate(Calendar.getInstance().getTime());
                    item.setCreateUser(getSessionBean().getCurrentUser());
                    try {
                        String desc = o[2].toString();
                        if (desc.trim().length() == 0) {
                            desc = null;
                        }
                        item.setDescription(desc);
                    } catch (Exception e) {
                    }
                    try {
                        item.setName((String) o[1]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    }
                    if (o[1] == null || "".equalsIgnoreCase((String) o[1])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre no puede ser nulo o vacio");
                    }
                    item.setName((String) o[1]);
                    if (parent == null) {
                        ClassifierBean.this.getClassifierService().getParent(item.getPath());
                    }
                    item.setParent(parent);
                    item.setCode(null);
                    content += "<tr class='animated fadeInRight'><td><span class='fa fa-check text-success'></span></td><td>" + item.getPath() + "</td><td>" + item.getName() + "</td><td>" + item.getDescription() + "</td></tr>";
                    percentLoad = (i * 50) / totalRecords;
                    list.add(item);
                }
                state = ImportUtils.State.PROCESS;
                int tempPercent = percentLoad;
                for (int i = 0; i < list.size(); i++) {
                    percentLoad = tempPercent+(((i+1) * 50) / totalRecords);
                    ClassifierBean.this.getClassifierService().saveOrUpdate(list.get(i));
                }
                state = ImportUtils.State.SUCCESS;
                message="Resultado satisfactorio";
            } catch (ImportException e) {
                state = ImportUtils.State.ERROR;
            }
        }

        private String rowAsError(Object[] o) {
            return "<tr class='danger animated fadeInRight'><td><span class='fa fa-times text-danger'></span></td><td>" + o[0] + "</td><td>" + o[1] + "</td><td>" + o[2] + "</td></tr>";

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
         * @param file the file to set
         */
        public void setFile(Part file) {
            this.file = file;
        }

        /**
         * Gets percent load.
         *
         * @return the percentLoad
         */
        public Integer getPercentLoad() {
            return percentLoad;
        }

        /**
         * Sets percent load.
         *
         * @param percentLoad the percentLoad to set
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
         * @param message the message to set
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
         * @param state the state to set
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
                InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/Classifier.jasper");
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

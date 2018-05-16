/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.bean;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.exception.ImportException;
import edu.unas.spoi.oei.bean.BudgetProgramBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.INecessaryItemService;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.ErrorMessage;
import edu.unas.spoi.util.ImportUtils;
import edu.unas.spoi.util.SmartMessage;
import edu.unas.spoi.dao.util.PGSqlUtil;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderFactory;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.Pagination;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
 * The type Necessary item bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class NecessaryItemBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{necessaryItemService}")
    private INecessaryItemService necessaryItemService;
    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;

    private Pagination<Object[]> pagination;

    private GenericClassifierSearcher genericClassifierSearcher;

    private Integer id;
    private Import import_;
    private Export export;
    private List<Object[]> data;
    private String code;
    private String name;
    private String genericClassifier;
    private OrderFactory orderFactory;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        orderFactory = new OrderFactory(new OrderList());
        orderFactory.setDefaultOrder(Order.asc("code"));
        import_ = new Import();
        genericClassifierSearcher = new GenericClassifierSearcher();
        export = new Export();
        pagination = new Pagination(necessaryItemService);
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
        genericClassifier = "";
        search();
    }

    /**
     * Change order.
     */
    public void changeOrder() {
        orderFactory.changeFromRequest();
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
                .add(Projections.property("c.path"))
                .add(Projections.property("correlative"))
                .add(Projections.property("name"))
                .add(Projections.property("unitPrice"));
        AliasList aliasList = new AliasList();
        aliasList.add("classifier", "c");
        CriterionList criterionList = new CriterionList();
        criterionList.add(Restrictions.eq("active", true));
        if (code.length() != 0) {
            criterionList.add(Restrictions.like("code", code, MatchMode.START));
        }
        if (name.length() != 0) {
            criterionList.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        if (genericClassifier.length() != 0) {
            criterionList.add(Restrictions.like("code", genericClassifier, MatchMode.START).ignoreCase());
        }
        pagination.search(1, projectionList, criterionList, orderFactory.make(), aliasList);

        //data = necessaryItemService.addRestrictionsVariant(Arrays.asList(projectionList, criterionList, orderList, aliasList));
    }

    /**
     * Gets necessary item service.
     *
     * @return the necessaryItemService
     */
    public INecessaryItemService getNecessaryItemService() {
        return necessaryItemService;
    }

    /**
     * Sets necessary item service.
     *
     * @param necessaryItemService the necessaryItemService to set
     */
    public void setNecessaryItemService(INecessaryItemService necessaryItemService) {
        this.necessaryItemService = necessaryItemService;
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
     * Gets generic classifier searcher.
     *
     * @return the genericClassifierSearcher
     */
    public GenericClassifierSearcher getGenericClassifierSearcher() {
        return genericClassifierSearcher;
    }

    /**
     * Sets generic classifier searcher.
     *
     * @param genericClassifierSearcher the genericClassifierSearcher to set
     */
    public void setGenericClassifierSearcher(GenericClassifierSearcher genericClassifierSearcher) {
        this.genericClassifierSearcher = genericClassifierSearcher;
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
     * Gets generic classifier.
     *
     * @return the genericClassifier
     */
    public String getGenericClassifier() {
        return genericClassifier;
    }

    /**
     * Sets generic classifier.
     *
     * @param genericClassifier the genericClassifier to set
     */
    public void setGenericClassifier(String genericClassifier) {
        this.genericClassifier = genericClassifier;
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
     * The type Generic classifier searcher.
     */
    public class GenericClassifierSearcher implements java.io.Serializable {

        private List<Object[]> data;

        /**
         * Update.
         */
        public void update() {
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("id"))
                    .add(Projections.property("path"))
                    .add(Projections.property("name"));
            AliasList aliasList = new AliasList();
            aliasList.add("classifierType", "c");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            criterionList.add(Restrictions.eq("c.codeDigit", new Short("2")));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("name"));
            data = getClassifierService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList, orderList));
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
                InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/NecessaryItem.jasper");
                param.put("UNAS", servletContext.getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                param.put("active", allowDropped);
                param.put("printHeader", activeHeader);
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, PGSqlUtil.getJdbcPostGresSQL().getConnection());
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType(contentType.getMimeType());
                response.addHeader("Content-disposition", "attachment; filename=Items_de_necesidad." + contentType.name().toLowerCase());
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
            List<NecessaryItem> list = new ArrayList();
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
                        o = ImportUtils.readRow(file, i, 6);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Contenido no legible", e.getMessage());
                    }
                    String code = null;
                    try {
                        code = o[1].toString().trim();
                        boolean exist = getNecessaryItemService().existCode(code);
                        if (exist) {
                            content += rowAsError(o);
                            throw new ImportException("ERROR : Codigo Invalido", "El codigo ya existe");
                        }
                        for (NecessaryItem item : list) {
                            if (item.getCode().equalsIgnoreCase(code)) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", "El código se repite en el documento");
                            }
                        }
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Codigo Invalido", "El codigo no puede ser nulo o vacio");
                    }

                    NecessaryItem item = new NecessaryItem();
                    item.setCode(code);
                    item.setCreateDate(Calendar.getInstance().getTime());
                    item.setCreateUser(getSessionBean().getCurrentUser());
                    String path = (String) o[0];
                    try {
                        Classifier classifier = (Classifier) getClassifierService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("path", path))).get(0);
                        item.setClassifier(classifier);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Clasificador Invalido", "El codigo de clasificador no existe");
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

                    item.setUom((String) o[3]);
                    try {
                        item.setUnitPrice(new BigDecimal((Double) o[4], new MathContext(2, RoundingMode.HALF_UP)));
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre debe ser un texto");
                    }
                    if (o[4] == null) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Nombre Invalido", "El nombre no puede ser nulo o vacio");
                    }
                    if (o[5] != null && o[5].toString().length() != 0) {
                        if (o[5] instanceof Number) {
                            o[5] = ((Number) o[5]).intValue();
                        }
                        String[] abps = o[5].toString().trim().split(",");
                        CriterionList criterionList = new CriterionList();
                        criterionList.add(Restrictions.in("code", Arrays.asList(abps)));
                        List<ActivityBudgetProgram> listABP = getActivityBudgetProgramService().addRestrictions(criterionList);
                        item.setActivityBudgetPrograms(listABP);
                    }
                    item.setCorrelative(item.getCode().replaceFirst(item.getClassifier().getPath(), ""));
                    content += "<tr class='animated fadeInRight'><td><span class='fa fa-check text-success'></span></td><td>" + item.getCode() + "</td><td>" + item.getName() + "</td></tr>";
                    percentLoad = (i * 50) / totalRecords;
                    list.add(item);
                }
                state = ImportUtils.State.PROCESS;
                int tempPercent = percentLoad;
                for (int i = 0; i < list.size(); i++) {
                    percentLoad = tempPercent + (((i + 1) * 50) / totalRecords);
                    getNecessaryItemService().saveOrUpdate(list.get(i));
                }
                state = ImportUtils.State.SUCCESS;
                message = "Resultado satisfactorio";
            } catch (ImportException e) {
                state = ImportUtils.State.ERROR;
            }
        }

        private String rowAsError(Object[] o) {
            return "<tr class='danger animated fadeInRight'><td><span class='fa fa-times text-danger'></span></td><td>" + o[0] + "</td><td>" + o[1] + "</td></tr>";

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
}

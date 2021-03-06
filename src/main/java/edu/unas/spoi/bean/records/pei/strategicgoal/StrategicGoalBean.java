/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.pei.strategicgoal;

import dn.core3.util.Pagination;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.exception.ImportException;
import edu.unas.spoi.oei.model.StrategicAxis;
import edu.unas.spoi.oei.model.StrategicGoal;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
import edu.unas.spoi.oei.service.interfac.IStrategicGoalService;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.ImportUtils;
import edu.unas.spoi.util.SmartMessage;
import edu.unas.spoi.dao.util.PGSqlUtil;
import edu.unas.spoi.oei.bean.BudgetProgramBean;
import edu.unas.spoi.util.SimpleSearcher;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
import org.hibernate.criterion.Restrictions;

/**
 * The type Strategic goal bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
public class StrategicGoalBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{strategicGoalService}")
    private IStrategicGoalService strategicGoalService;
    @ManagedProperty(value = "#{strategicAxisService}")
    private IStrategicAxisService strategicAxisService;
    @ManagedProperty(value = "#{strategicPlanService}")
    private IStrategicPlanService strategicPlanService;

    private SimpleSearcher strategicAxisSearcher;

    private Pagination pagination;
    private Integer id;
    private Import import_;
    private Export export;
    private Integer idStrategicAxis;
    private String code;
    private StrategicPlan strategicPlan;
    private String name;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        import_ = new Import();
        export = new Export();
        pagination = new Pagination() {
            @Override
            protected Long countTotalRecords() {
                return strategicGoalService.countListForMainView(
                        strategicPlan.getId(),
                        code,
                        name,
                        idStrategicAxis,
                        true
                );
            }

            @Override
            protected List searchRecords(int page, int recordsPerPage) {
                return strategicGoalService.getListLazyForMainView(
                        page,
                        recordsPerPage,
                        strategicPlan.getId(),
                        code,
                        name,
                        idStrategicAxis,
                        true,
                        null
                );
            }
        };
        strategicAxisSearcher = new SimpleSearcher() {
            @Override
            public void update() {
                if (strategicPlan == null) {
                    data = Collections.EMPTY_LIST;
                    return;
                }
                data = strategicAxisService.getListBasicData(strategicPlan.getId());
            }
        };
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        strategicPlan = strategicPlanService.getBy(sessionBean.getOperationYear());
        strategicAxisSearcher.update();
        refresh();
    }

    /**
     * Refresh.
     */
    public void refresh() {
        idStrategicAxis = null;
        code = "";
        name = "";
        search();
    }

    /**
     * Search.
     */
    public void search() {
        if (strategicPlan == null) {
            pagination.setData(Collections.EMPTY_LIST);          
            return;
        }
        pagination.search(1);
    }

    /**
     * The type Import.
     */
//<editor-fold defaultstate="collapsed" desc="Import">
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
            List<StrategicGoal> list = new ArrayList();
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
                        o = ImportUtils.readRow(file, i, 3);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Contenido no legible", e.getMessage());
                    }
                    String code = null;
                    try {
                        code = o[1].toString().trim();
                        boolean exist = getStrategicAxisService().validateCode(code, getStrategicPlan().getId(), null);
                        if (exist) {
                            content += rowAsError(o);
                            throw new ImportException("ERROR : Codigo Invalido", "El codigo ya existe");
                        }
                        for (StrategicGoal item : list) {
                            if (item.getCode().equalsIgnoreCase(code)) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", "El código se repite en el documento");
                            }
                        }
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Codigo Invalido", "El codigo no puede ser nulo o vacio");
                    }

                    StrategicGoal item = new StrategicGoal();
                    item.setCode(code);

                    item.setCreateDate(Calendar.getInstance().getTime());
                    item.setCreateUser(getSessionBean().getCurrentUser());
                    try {
                        item.setName((String) o[2]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro invalido", "El nombre debe ser un texto");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro invalido", "El nombre debe ser un texto");
                    }
                    if (o[2] == null || "".equalsIgnoreCase((String) o[2])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro invalido", "El nombre no puede ser nulo o vacio");
                    }
                    String strategicAxisCode = (String) o[0];
                    try {
                        StrategicAxis strategicAxis = (StrategicAxis) getStrategicAxisService().addRestrictions(Arrays.asList((Criterion) Restrictions.eq("code", strategicAxisCode))).get(0);
                        item.setStrategicAxis(strategicAxis);
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro invalido", "El código de eje estratégico es incorrecto");

                    }

                    content += "<tr class='animated fadeInRight'><td><span class='fa fa-check text-success'></span></td><td>" + item.getStrategicAxis().getCode() + "</td><td>" + item.getCode() + "</td><td>" + item.getName() + "</td></tr>";
                    percentLoad = (i * 50) / totalRecords;
                    list.add(item);
                }
                state = ImportUtils.State.PROCESS;
                int tempPercent = percentLoad;
                for (int i = 0; i < list.size(); i++) {
                    percentLoad = tempPercent + (((i + 1) * 50) / totalRecords);
                    getStrategicGoalService().saveOrUpdate(list.get(i));
                }
                state = ImportUtils.State.SUCCESS;
                message = "Resultado satisfactorio";
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Export">
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
                InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/StrategicGoal.jasper");
                param.put("UNAS", servletContext.getResourceAsStream("/1258488425132154132154214536/images/unas.png"));
                param.put("active", allowDropped);
                param.put("printHeader", activeHeader);
                param.put("strategic_plan", getStrategicPlan().getId());
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, PGSqlUtil.getJdbcPostGresSQL().getConnection());
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType(contentType.getMimeType());
                response.addHeader("Content-disposition", "attachment; filename=Objetivos_Estratégicos." + contentType.name().toLowerCase());
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
    
//</editor-fold>
}

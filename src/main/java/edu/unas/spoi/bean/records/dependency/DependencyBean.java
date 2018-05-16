/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.records.dependency;

import dn.core3.hibernate.AliasList;
import dn.core3.hibernate.OrderFactory;
import dn.core3.util.Pagination;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
import edu.unas.spoi.bne.service.interfac.IBNeScheduleService;
import edu.unas.spoi.dao.util.PGSqlUtil;
import edu.unas.spoi.exception.ImportException;
import edu.unas.spoi.oei.bean.BudgetProgramBean;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.poi.model.Periodicity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.poi.service.interfac.IPOIScheduleService;
import edu.unas.spoi.poi.service.interfac.IPOIService;
import edu.unas.spoi.poi.service.interfac.IPeriodicityItemService;
import edu.unas.spoi.poi.service.interfac.IPeriodicityService;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.report.StaticReport;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.ImportUtils;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.CriterionList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.WebReportForm;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
import lombok.Data;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Dependency bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
@Data
public class DependencyBean implements java.io.Serializable, ILoadable {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{dependencyCopyBean}")
    private DependencyCopyBean dependencyCopyBean;
    @ManagedProperty(value = "#{poiService}")
    private IPOIService poiService;
    @ManagedProperty(value = "#{poiScheduleService}")
    private IPOIScheduleService poiScheduleService;
    @ManagedProperty(value = "#{bneItemService}")
    private IBNeItemService bneItemService;
    @ManagedProperty(value = "#{bneScheduleService}")
    private IBNeScheduleService bneScheduleService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{periodicityService}")
    private IPeriodicityService periodicityService;
    @ManagedProperty(value = "#{periodicityItemService}")
    private IPeriodicityItemService periodicityItemService;
    @ManagedProperty(value = "#{activityBudgetProgramService}")
    private IActivityBudgetProgramService activityBudgetProgramService;
    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;

    private OrderFactory orderFactory;
    private Pagination pagination;
    private WebReportForm webReportForm;

    private String path;
    private String name;
    private Integer id;
    private Boolean operational;
    private Import import_;
    private Export export;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        //<editor-fold defaultstate="collapsed" desc="Report Config">

        webReportForm = new WebReportForm();
        StaticReport report = new StaticReport("1", "Financiamiento de las dependencias", "Dependency_annual_info.jasper");

        webReportForm.getStaticReports().add(report);

        report = new StaticReport("2", "Financiamiento de las dependencias x clasificador genérico", "Dependency_classifier_ceiling.jasper");

        final AliasList aliasList = new AliasList();
        aliasList.add("classifierType", "ct");
        final CriterionList criterionList = new CriterionList()._add(Restrictions.eq("ct.id", 2));
        report.addField(new StaticReport.FieldParameter() {
            @Override
            public String makeHtml() {
                List<Classifier> classifiers = getClassifierService().addRestrictionsVariant(aliasList, criterionList);
                String html = ""
                        + "<div class='form-group'>"
                        + "<label class='control-label col-md-2'>Clasificador</label>"
                        + "<div class='col-md-10'>"
                        + "<select id='id_classifier' name='id_classifier' class='form-control parameter-report'>"
                        + "<option />";
                for (Classifier classifier : classifiers) {
                    html += "<option value='" + classifier.getId() + "'>" + classifier.getPath() + " " + classifier.getName() + "</option>";
                }
                html += "</select>"
                        + "</div>"
                        + "</div>";
                return html;
            }

            @Override
            public String makeJavascript() {
                return "";
            }
        });
        webReportForm.getStaticReports().add(report);
        report = new StaticReport("3", "Financiamiento de las dependencias x clasificadores especificos", "BoxNeed_consolidate.jasper");
        webReportForm.getStaticReports().add(report);
//</editor-fold>

        import_ = new Import();
        export = new Export();
        pagination = new Pagination() {
            @Override
            protected Long countTotalRecords() {
                return dependencyService.countListForMainView(
                        path,
                        name,
                        operational,
                        sessionBean.getOperationYear(),
                        !sessionBean.authorize("R_TDP") ? true : null);
            }

            @Override
            protected List searchRecords(int page, int recordsPerPage) {
                return dependencyService.getListLazyForMainView(
                        page,
                        recordsPerPage,
                        path,
                        name,
                        operational,
                        sessionBean.getOperationYear(),
                        !sessionBean.authorize("R_TDP") ? true : null,
                        null);
            }
        };
    }

    /**
     * Export.
     */
    public void export() {
        Map<String, Object> map = new HashMap();
        map.put("year", sessionBean.getOperationYear());
        map.put("operational", null);
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getContext();
        InputStream is = servletContext.getResourceAsStream("/1258488425132154132154214536/SUB_dai_budget.jasper");
        map.put("subreport", is);
        webReportForm.execute(map, sessionBean);
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        dependencyCopyBean.refresh();
        refresh();
    }

    /**
     * Refresh.
     */
    public void refresh() {
        path = "";
        name = "";
        operational = null;
        search();
    }

    /**
     * Search.
     */
    public void search() {
        pagination.search(1);
    }

    /**
     * Generate full export string.
     *
     * @return the string
     * @throws IOException the io exception
     */
    public String generateFullExport() throws IOException {
        Short periodicityId = sessionBean.getOperationYear() <= 2017 ? (short) 1 : 2;
        Periodicity periodicity = periodicityService.getById(periodicityId);
        List<PeriodicityItem> periodicityItems = periodicityItemService.getListBy(periodicity);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        XSSFRow rowHeader, rowTableHeader, row;
        int rowNumber,columnCount,tableHeaderCount;
        List<Map<String, Object>> data;
        //<editor-fold defaultstate="collapsed" desc="Actividades Operativas">
        
        rowNumber = -1;
        sheet = workbook.createSheet("Actividades operativas");
        rowTableHeader = sheet.createRow(++rowNumber);
        rowHeader = sheet.createRow(++rowNumber);
        columnCount = 0;
        tableHeaderCount = 0;
        //<editor-fold defaultstate="collapsed" desc="Cabecera">
        
        rowTableHeader.createCell(0).setCellValue("Dependencia");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(0).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        tableHeaderCount++;
        rowTableHeader.createCell(++columnCount).setCellValue("Año");
        sheet.addMergedRegion(new CellRangeAddress(0, 1, tableHeaderCount, tableHeaderCount));

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Actividad del POI");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 2));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");
        rowHeader.createCell(++columnCount).setCellValue("Prioridad");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Programa Presupuestal");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Unidad del POI");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Plan Estrategico institucional");
        if (sessionBean.getOperationYear() <= 2017) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 7));
            rowHeader.createCell(++columnCount).setCellValue("Cod. Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. esp.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. esp.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Eje. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Eje. estr.");
        } else {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 5));
            rowHeader.createCell(++columnCount).setCellValue("Cod. Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Eje. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Eje. estr.");
        }

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("CRONOGRAMA DE ACTIVIDAD");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += periodicityItems.size()));
        for (PeriodicityItem item : periodicityItems) {
            rowHeader.createCell(++columnCount).setCellValue(item.getAbbreviation());
        }
        rowHeader.createCell(++columnCount).setCellValue("Total");

//</editor-fold>
        data = poiService.getListFullData(sessionBean.getOperationYear());
        data.forEach(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> item) {
                item.put("schedules", item.get("poiActivityId") == null ? Collections.EMPTY_LIST : poiScheduleService.getListPoiShedulesBasicData((Long) item.get("poiActivityId")));
            }
        });
        for (Map<String, Object> item : data) {
            row = sheet.createRow(++rowNumber);
            columnCount = -1;
            row.createCell(++columnCount).setCellValue((String) item.get("dependencyPath"));
            row.createCell(++columnCount).setCellValue((String) item.get("dependencyName"));
            row.createCell(++columnCount).setCellValue((Integer) item.get("year"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiActivityCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiActivityName"));
            if (item.get("poiActivityPriority") != null) {
                row.createCell(++columnCount).setCellValue((Short) item.get("poiActivityPriority"));
            } else {
                row.createCell(++columnCount);
            }
            row.createCell(++columnCount).setCellValue((String) item.get("activityBudgetProgramCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("activityBudgetProgramName"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiUnityCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiUnityName"));
            if (sessionBean.getOperationYear() <= 2017) {
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityName"));
                row.createCell(++columnCount).setCellValue((String) item.get("specificGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("specificGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisName"));
            } else {
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisName"));
            }
            List<Map<String, Object>> poiSchedules = (List<Map<String, Object>>) item.get("schedules") ;
            Double total = 0.0;
            for (PeriodicityItem periodicityItem : periodicityItems) {
                boolean exist = false;
                for (Map<String, Object> schedule : poiSchedules) {
                    if (periodicityItem.getId().intValue() == (Integer) schedule.get("periodicityItemId")) {
                        total += ((Number) schedule.get("quantity")).doubleValue();
                        row.createCell(++columnCount).setCellValue(((Number) schedule.get("quantity")).doubleValue());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    row.createCell(++columnCount);
                }
            }
            row.createCell(++columnCount).setCellValue(total); 
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Cuadro de necesidades">
        
        rowNumber = -1;
        sheet = workbook.createSheet("Cuadro de necesidades");
        rowTableHeader = sheet.createRow(++rowNumber);
        rowHeader = sheet.createRow(++rowNumber);
        columnCount = 0;
        tableHeaderCount = 0;
        //<editor-fold defaultstate="collapsed" desc="Cabecera">
        
        rowTableHeader.createCell(0).setCellValue("Dependencia");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(0).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        tableHeaderCount++;
        rowTableHeader.createCell(++columnCount).setCellValue("Año");
        sheet.addMergedRegion(new CellRangeAddress(0, 1, tableHeaderCount, tableHeaderCount));

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Actividad del POI");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 2));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");
        rowHeader.createCell(++columnCount).setCellValue("Prioridad");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Programa Presupuestal");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Unidad del POI");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Plan Estrategico institucional");
        if (sessionBean.getOperationYear() <= 2017) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 7));
            rowHeader.createCell(++columnCount).setCellValue("Cod. Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. esp.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. esp.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Eje. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Eje. estr.");
        } else {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 5));
            rowHeader.createCell(++columnCount).setCellValue("Cod. Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Act. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Obj. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Cod. Eje. estr.");
            rowHeader.createCell(++columnCount).setCellValue("Nombre Eje. estr.");
        }

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Fte. financiamiento");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Especifica de Gasto");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, ++tableHeaderCount));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("Producto");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += 2));
        rowHeader.createCell(++columnCount).setCellValue("Codigo");
        rowHeader.createCell(++columnCount).setCellValue("Nombre");
        rowHeader.createCell(++columnCount).setCellValue("Prec. Unit");

        rowTableHeader.createCell(++tableHeaderCount).setCellValue("CRONOGRAMA DE NECESIDAD");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, tableHeaderCount, tableHeaderCount += periodicityItems.size()));
        for (Month item : Month.values()) {
            rowHeader.createCell(++columnCount).setCellValue(item.getAbbr());
        }
        rowHeader.createCell(++columnCount).setCellValue("Total");

//</editor-fold>
        data = bneItemService.getListFullData(sessionBean.getOperationYear());
        data.forEach(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> item) {
                item.put("schedules", item.get("bneItemId") == null ? Collections.EMPTY_LIST : bneScheduleService.getListBneSchedulesBasicData((Long) item.get("bneItemId")));
            }
        });
        for (Map<String, Object> item : data) {
            row = sheet.createRow(++rowNumber);
            columnCount = -1;
            row.createCell(++columnCount).setCellValue((String) item.get("dependencyPath"));
            row.createCell(++columnCount).setCellValue((String) item.get("dependencyName"));
            row.createCell(++columnCount).setCellValue((Integer) item.get("year"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiActivityCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiActivityName"));
            if (item.get("poiActivityPriority") != null) {
                row.createCell(++columnCount).setCellValue((Short) item.get("poiActivityPriority"));
            } else {
                row.createCell(++columnCount);
            }
            row.createCell(++columnCount).setCellValue((String) item.get("activityBudgetProgramCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("activityBudgetProgramName"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiUnityCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("poiUnityName"));
            if (sessionBean.getOperationYear() <= 2017) {
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityName"));
                row.createCell(++columnCount).setCellValue((String) item.get("specificGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("specificGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisName"));
            } else {
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicActivityName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicGoalName"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisCode"));
                row.createCell(++columnCount).setCellValue((String) item.get("strategicAxisName"));
            }
            row.createCell(++columnCount).setCellValue((String) item.get("fundingSourceCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("fundingSourceName"));

            row.createCell(++columnCount).setCellValue((String) item.get("classifierCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("classifierName"));

            row.createCell(++columnCount).setCellValue((String) item.get("necessaryItemCode"));
            row.createCell(++columnCount).setCellValue((String) item.get("necessaryItemName"));
            if (item.get("necessaryItemUnitPrice") != null) {
                row.createCell(++columnCount).setCellValue(((BigDecimal) item.get("necessaryItemUnitPrice")).doubleValue());
            } else {
                row.createCell(++columnCount);
            }
            List<Map<String, Object>> bneShedules = (List<Map<String, Object>>) item.get("schedules");
            Double total = 0.0;
            for (Month month : Month.values()) {
                boolean exist = false;
                for (Map<String, Object> schedule : bneShedules) {
                    if (month == (Month) schedule.get("month")) {
                        total += ((Number) schedule.get("quantity")).doubleValue();
                        row.createCell(++columnCount).setCellValue(((Number) schedule.get("quantity")).doubleValue());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    row.createCell(++columnCount);
                }
            }
            row.createCell(++columnCount).setCellValue(total);
        }
//</editor-fold>
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType(ContentType.XLSX.getMimeType());
        response.addHeader("Content-disposition", "attachment; filename=\"Todos los datos Dependencia.xlsx\"");
        workbook.write(response.getOutputStream());
        FacesContext.getCurrentInstance().responseComplete();

        return "index?faces-redirect=true";

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
            List<Dependency> list = new ArrayList();
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
                        o = ImportUtils.readRow(file, i, 4);
                    } catch (Exception e) {
                        throw new ImportException("ERROR : Contenido no legible", e.getMessage());
                    }
                    String path = null;
                    Object[] d = null;
                    try {
                        path = o[0].toString().trim();
                        d = validateCode(path);
                        boolean exist = !(boolean) d[1];
                        if (exist) {
                            content += rowAsError(o);
                            throw new ImportException("ERROR : Codigo Invalido", "El codigo ya existe");
                        }
                        int length = 0;
                        for (Dependency item : list) {
                            if (path.startsWith(item.getPath()) && item.getPath().length() > length) {
                                d[0] = item;
                                length = item.getPath().length();
                            }
                            if (path.equalsIgnoreCase(item.getPath()) || item.getPath().startsWith(path)) {
                                content += rowAsError(o);
                                throw new ImportException("ERROR : Codigo Invalido", "El código se repite en el documento");
                            }
                        }
                    } catch (Exception e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Codigo Invalido", "El codigo no puede ser nulo o vacio");
                    }

                    Dependency item = new Dependency();
                    item.setPath(path);
                    item.setParent((Dependency) d[0]);
                    item.setCreateDate(Calendar.getInstance().getTime());
                    item.setCreateUser(getSessionBean().getCurrentUser());
                    try {
                        item.setName((String) o[1]);
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "Formato inadecuado de campo 'nombre'");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "El nombre debe ser un texto");
                    }
                    if (o[1] == null || "".equalsIgnoreCase((String) o[1])) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "El nombre no puede ser nulo o vacio");
                    }
                    try {
                        item.setOperational(o[2].toString().equalsIgnoreCase("o") || o[2].toString().equalsIgnoreCase("Si"));
                    } catch (ClassCastException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "Formato inadecuado de campo 'operacional'");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "Lista de parametros muy pequeño");
                    }
                    if (o[2] == null) {
                        content += rowAsError(o);
                        throw new ImportException("ERROR : Parametro Invalido", "Valor 'operacional' vacio");
                    }
                    item.setCode(item.getParent() == null ? item.getPath() : item.getPath().replaceFirst(item.getParent().getPath(), ""));
                    if (o[3] != null && o[3].toString().length() != 0) {
                        if (o[3] instanceof Number) {
                            o[3] = ((Number) o[3]).intValue();
                        }
                        String[] abps = o[3].toString().trim().split(",");
                        CriterionList criterionList = new CriterionList();
                        criterionList.add(Restrictions.in("code", Arrays.asList(abps)));
                        List<ActivityBudgetProgram> listABP = getActivityBudgetProgramService().addRestrictions(criterionList);
                        item.setActivityBudgetPrograms(listABP);
                    }
                    content += "<tr class='animated fadeInRight'><td><span class='fa fa-check text-success'></span></td><td>" + item.getPath() + "</td><td>" + item.getName() + "</td><td>" + item.getOperational() + "</td><td>" + o[3] + "</td></tr>";
                    percentLoad = (i * 50) / totalRecords;
                    list.add(item);
                }
                state = ImportUtils.State.PROCESS;
                int tempPercent = percentLoad;
                for (int i = 0; i < list.size(); i++) {
                    percentLoad = tempPercent + (((i + 1) * 50) / totalRecords);
                    Dependency dependency = list.get(i);
                    getDependencyService().saveOrUpdate(dependency);
                }
                state = ImportUtils.State.SUCCESS;
                message = "Resultado satisfactorio";
            } catch (ImportException e) {
                state = ImportUtils.State.ERROR;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Validate code object [ ].
         *
         * @param path the path
         * @return the object [ ]
         */
        public Object[] validateCode(String path) {
            CriterionList criterionList = new CriterionList();
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.count("id"));
            criterionList.add(Restrictions.like("path", path.trim(), MatchMode.START));

            criterionList.add(Restrictions.eq("active", true));
            int count = ((Number) dependencyService.getListByRestrictions(projectionList, criterionList).get(0)).intValue();
            if (count == 0) {
                String tempPath = path.trim();
                String code = "";
                projectionList = Projections.projectionList()
                        .add(Projections.id())
                        .add(Projections.property("path"))
                        .add(Projections.property("name"));
                Dependency parent = null;
                for (int i = path.length(); i > 3; i--) {
                    criterionList.clear();
                    code += tempPath.charAt(tempPath.length() - 1);
                    tempPath = tempPath.substring(0, tempPath.length() - 1);
                    criterionList.add(Restrictions.like("path", tempPath, MatchMode.EXACT));
                    try {
                        List<Dependency> dependencys = getDependencyService().getListByRestrictions(new Object[]{criterionList});
                        parent = (Dependency) dependencys.get(0);
                        if (parent != null) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
                return new Object[]{parent, true};
            } else {
                return new Object[]{null, false};
            }
        }

        private String rowAsError(Object[] o) {
            return "<tr class='danger animated fadeInRight'><td><span class='fa fa-times text-danger'></span></td><td>" + o[0] + "</td><td>" + o[1] + "</td><td>" + o[2] + "</td><td>" + o[3] + "</td></tr>";

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

//</editor-fold>
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.report.StaticReport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.sf.jasperreports.engine.JRExporter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * The type Web report form.
 *
 * @author CTIC
 */
public class WebReportForm implements java.io.Serializable {

    private List<StaticReport> staticReports;
    private StaticReport selected;
    private String parameters;
    private edu.unas.spoi.report.enumerated.ContentType contentType;

    /**
     * Instantiates a new Web report form.
     */
    public WebReportForm() {
        staticReports = new ArrayList<>();
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
     * @param map         the map
     * @param sessionBean the session bean
     * @return the string
     */
    public String execute(Map<String, Object> map, SessionBean sessionBean) {
        String[] maps = parameters.split(";");
        for (String m : maps) {
            String[] entry = m.split("=");
            if (entry.length == 1) {
                map.put(entry[0], null);
            } else {
                map.put(entry[0], entry[1]);
            }
        }
        return selected.execute((JRExporter) contentType.getExporter(), map, contentType, sessionBean);
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
            contentType = edu.unas.spoi.report.enumerated.ContentType.valueOf(c.toUpperCase());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report;

import edu.unas.spoi.dao.util.PGSqlUtil;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * The type Report.
 *
 * @author Darkus Nightmare
 */
public class Report implements java.io.Serializable {

    private Supplier<Connection> supplierConnection;
    private JasperReport jasperReport;
    private Map<String, Object> primigenicParameters;
    private String packageLocation;
    private String name;

    /**
     * Instantiates a new Report.
     */
    public Report() {
        supplierConnection = new Supplier<Connection>() {
            @Override
            public Connection get() {
                return PGSqlUtil.getJdbcPostGresSQL().getConnection();
            }
        };
    }

    /**
     * Instantiates a new Report.
     *
     * @param packageLocation the package location
     * @param name            the name
     */
    public Report(String packageLocation, String name) {
        this();
        this.packageLocation = packageLocation;
        this.name = name;
    }

    /**
     * Instantiates a new Report.
     *
     * @param name the name
     */
    public Report(String name) {
        this(null, name);
    }

    /**
     * With connection report.
     *
     * @param connection the connection
     * @return the report
     */
    public Report withConnection(Supplier<Connection> connection) {
        supplierConnection = connection;
        return this;
    }

    /**
     * Location report.
     *
     * @param name the name
     * @return the report
     */
    public Report location(String name) {
        this.name = name;
        return this;
    }

    /**
     * Location report.
     *
     * @param packageLocation the package location
     * @param name            the name
     * @return the report
     */
    public Report location(String packageLocation, String name) {
        this.packageLocation = packageLocation;
        this.name = name;
        return this;
    }

    /**
     * Add parameter report.
     *
     * @param name  the name
     * @param value the value
     * @return the report
     */
    public Report addParameter(String name, Object value) {
        primigenicParameters.put(name, value);
        return this;
    }

    private Map buildParameters() {
        final Map<String, Object> parameters = new HashMap();
        primigenicParameters.forEach(new BiConsumer<String, Object>() {
            @Override
            public void accept(String key, Object value) {
                if (value instanceof Supplier) {
                    parameters.put(key, ((Supplier) value).get());
                } else {
                    parameters.put(key, value);
                }
            }
        });
        return parameters;
    }

    private JasperReport getJasperReport() {

//            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            InputStream is = servletContext.getResourceAsStream(path);
        return this.jasperReport;
    }

    /**
     * Export.
     *
     * @param exporter the exporter
     * @param os       the os
     * @throws JRException the jr exception
     */
    public void export(JRExporter exporter, OutputStream os) throws JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(getJasperReport(), buildParameters(), supplierConnection.get());
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
        exporter.exportReport();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report.enumerated;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 * The enum Content type.
 *
 * @author Jhoan Brayam
 */
public enum ContentType {

    /**
     * The Docx.
     */
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", new JRDocxExporter()),
    /**
     * The Xlsx.
     */
    XLSX("application/vnd.ms-excel", new JRXlsExporter()),
    /**
     * The Xls.
     */
    XLS("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new JRXlsxExporter()),
    /**
     * The Pdf.
     */
    PDF("application/pdf", new JRPdfExporter()),
    /**
     * The Txt.
     */
    TXT("text/plain", new JRTextExporter()),
    /**
     * Sybase content type.
     */
    SYBASE("",null),
    /**
     * The Html.
     */
    HTML("text/html",new JRHtmlExporter());

    private final String mimeType;
    private final Object exporter;

    private ContentType(String mimeType, Object exporter) {
        this.mimeType = mimeType;
        this.exporter = exporter;
    }

    /**
     * Gets mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }


    /**
     * Get exporter object.
     *
     * @return the object
     */
    public Object getExporter(){
        try {
            return exporter.getClass().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ContentType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ContentType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

}

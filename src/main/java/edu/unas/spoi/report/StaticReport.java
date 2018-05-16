/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report;

import com.fiis.sppp.util.Faces;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.bean.BudgetProgramBean;
import edu.unas.spoi.report.enumerated.ContentType;
import edu.unas.spoi.util.SmartMessage;
import edu.unas.spoi.dao.util.PGSqlUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.FileUtils;

/**
 * The type Static report.
 *
 * @author Jhoan Brayam
 */
public class StaticReport implements java.io.Serializable {

    /**
     * Load from web pages jasper report.
     *
     * @param path the path
     * @return the jasper report
     */
    public static JasperReport loadFromWebPages(String path) {
        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
        try {
            return (JasperReport) JRLoader.loadObject(((ServletContext) Faces.getExternalContext().getContext()).getResourceAsStream("/1258488425132154132154214536/"+path));
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String code;
    private String name;
    private String path;
    private String fileName;
    private Map<String, Object> initialParameterMap;
    private List<FieldParameter> parameters;

    /**
     * Instantiates a new Static report.
     */
    public StaticReport() {
        parameters = new ArrayList();
        initialParameterMap = new HashMap();
    }

    /**
     * Gets basic parameters.
     *
     * @return the basic parameters
     */
    public Map<String, Object> getBasicParameters() {
        return new HashMap(initialParameterMap);
    }

    /**
     * Instantiates a new Static report.
     *
     * @param code     the code
     * @param name     the name
     * @param fileName the file name
     */
//    public StaticReport(String code, String name, String fileName, boolean withSubreports) {
//        this();
//        this.code = code;
//        this.name = name;
//
//        if (fileName.startsWith("/")) {
//            fileName = fileName.replaceFirst("/", "");
//        }
//        this.path = "/1258488425132154132154214536/" + fileName;
//        if (withSubreports) {
////            try {
//////                loadSubreports();
////            } catch (JRException ex) {
////                Logger.getLogger(StaticReport.class.getName()).log(Level.SEVERE, null, ex);
////            }
//        }
//        this.fileName = fileName;
//    }
    public StaticReport(String code, String name, String fileName) {
        this();
        this.code = code;
        this.name = name;
        if (fileName.startsWith("/")) {
            fileName = fileName.replaceFirst("/", "");
        }
        this.path = "/1258488425132154132154214536/" + fileName;
        this.fileName = fileName;
    }

    private void loadSubreports() throws JRException {
        try {
            URL url = ((ServletContext) Faces.getExternalContext().getContext()).getResource(this.path);
            String resourcePath = url.getPath();
            File verifyPath = new File(resourcePath);
            if (verifyPath.exists()) {
                System.out.println("EXISTE**********************");
            } else {
                System.out.println("NO EXISTE**********************");
            }
            resourcePath = resourcePath.substring(1);
            String absolutePathRemove = resourcePath.replace(this.path, "");
            File resourceFileToGetDirectory = new File(resourcePath);
            String resourceDirectoryPath = resourceFileToGetDirectory.getParent() + File.separator; //"c:/Users/xuser/Documents/NetBeansProjects/BOneBApp/target/classes/"
            System.out.println("PATH ::" + resourceDirectoryPath);
            File resourceDirectory = new File(resourceDirectoryPath);
            Collection<File> files = FileUtils.listFiles(resourceDirectory, new String[]{"jasper"}, true);
            String[] array = this.path.split("/");
            String fileName = array[array.length - 1].replace(".jasper", "");
            for (File file : files) {
                if (file.getName().equalsIgnoreCase(fileName + ".jasper")) {
                    continue;
                }
                String subreportPath = file.getAbsolutePath().replace("\\", "/").replace(absolutePathRemove, "");

                initialParameterMap.put(
                        file.getName().replace(fileName + "-", "").replace(".jasper", "").toUpperCase(),
                        JRLoader.loadObject(((ServletContext) Faces.getExternalContext().getContext()).getResourceAsStream(subreportPath))
                );
            }
        } catch (Exception ex) {
            Logger.getLogger(StaticReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add field.
     *
     * @param parameter the parameter
     */
    public void addField(FieldParameter parameter) {
        parameters.add(parameter);
    }

    /**
     * Execute string.
     *
     * @param exporter    the exporter
     * @param parameters  the parameters
     * @param contentType the content type
     * @param sessionBean the session bean
     * @return the string
     */
    public String execute(JRExporter exporter, Map<String, Object> parameters, ContentType contentType, SessionBean sessionBean) {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            InputStream is = servletContext.getResourceAsStream(path);
            JasperPrint jasperPrint = null;
            if (fileName.endsWith(".jrxml")) {
                JasperDesign jd = JRXmlLoader.load(is);
                JasperCompileManager.compileReportToFile(jd, "D:\\" + fileName.replace(".jrxml", ".jasper"));
                JasperReport jr = JasperCompileManager.compileReport(jd);
                jasperPrint = JasperFillManager.fillReport(jr, parameters, PGSqlUtil.getJdbcPostGresSQL().getConnection());
            } else if (fileName.endsWith(".jasper")) {

                jasperPrint = JasperFillManager.fillReport(is, parameters, PGSqlUtil.getJdbcPostGresSQL().getConnection());
            } else {
                throw new Exception("Error de Archivo");
            }
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType(contentType.getMimeType());
            String ext = contentType.name().toLowerCase();
            if (ext.equalsIgnoreCase("xlsx")) {
                ext = "xls";
            }
            response.addHeader("Content-disposition", "attachment; filename=" + this.name + "." + ext);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception ex) {
            sessionBean.getMessages().add(new SmartMessage("ERROR EN REPORTE!", ex.toString(), SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shaked animated"));
            Logger.getLogger(BudgetProgramBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index?faces-redirect=true";
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
     * Gets parameters.
     *
     * @return the parameters
     */
    public List<FieldParameter> getParameters() {
        return parameters;
    }

    /**
     * The interface Field parameter.
     */
    public interface FieldParameter extends java.io.Serializable {

        /**
         * Make html string.
         *
         * @return the string
         */
        public String makeHtml();

        /**
         * Make javascript string.
         *
         * @return the string
         */
        public String makeJavascript();
    }
}

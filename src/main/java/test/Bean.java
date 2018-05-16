/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 * The type Bean.
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class Bean  implements  Serializable{

    private Part file;
    private String fileContent;

    /**
     * Upload.
     */
    public void upload() {
        try {
            fileContent = new Scanner(file.getInputStream())
                    .useDelimiter("\\A").next();
        } catch (IOException e) {
            // Error handling
        }
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
     * Validate file.
     *
     * @param ctx   the ctx
     * @param comp  the comp
     * @param value the value
     */
    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > (1024*1024*1204)) {
//            UtilMessage.addMessage("Advertencia", "El archivo es muy grande", UtilMessage.MessageType.WARNING, "fa fa-exclamation-triangle");
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
//            UtilMessage.addMessage("Advertencia", "El archivo no es un .txt", UtilMessage.MessageType.WARNING, "fa fa-exclamation-triangle");
            msgs.add(new FacesMessage("not a text file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    /**
     * Gets file content.
     *
     * @return the fileContent
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * Sets file content.
     *
     * @param fileContent the fileContent to set
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}

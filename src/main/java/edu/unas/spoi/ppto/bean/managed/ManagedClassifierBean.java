/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.bean.managed;

import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.ClassifierType;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.IClassifierTypeService;
import edu.unas.spoi.util.ErrorMessage;
import edu.unas.spoi.util.SmartMessage;
import java.util.Stack;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * The type Managed classifier bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class ManagedClassifierBean extends AManagedBean<Classifier, IClassifierService> implements ILoadable {

    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService mainService;
    @ManagedProperty(value = "#{classifierTypeService}")
    private IClassifierTypeService classifierTypeService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private ValidateCode validateCode;

    private String path;
    private String name;
    private String description;
    private Classifier parent;
    private ClassifierType classifierType;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        validateCode = new ValidateCode();
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        validateCode.verify();
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado un clasificador" : "Se ha creado un clasificador";
        boolean result =super.save(); //To change body of generated methods, choose Tools | Templates.
        new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        return result;
    }

    @Override
    protected void fillFields() {
        path = getSelected().getPath();
        name = getSelected().getName();
        description = getSelected().getDescription();
        parent = getSelected().getParent();
        classifierType = getSelected().getClassifierType();
        onLoad(true);
        createAgain = true;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setPath(path);
        selected.setName(name);
        selected.setDescription(description.length() == 0 ? null : description);
        selected.setParent(parent);
        selected.setClassifierType(classifierType);
        Auditory.make(getSelected(), sessionBean.getCurrentUser());
    }

    /**
     * @return the mainService
     */
    @Override
    public IClassifierService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IClassifierService mainService) {
        this.mainService = mainService;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public Classifier getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param parent the parent to set
     */
    public void setParent(Classifier parent) {
        this.parent = parent;
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
     * Gets validate code.
     *
     * @return the validateCode
     */
    public ValidateCode getValidateCode() {
        return validateCode;
    }

    /**
     * Sets validate code.
     *
     * @param validateCode the validateCode to set
     */
    public void setValidateCode(ValidateCode validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * Gets classifier type.
     *
     * @return the classifierType
     */
    public ClassifierType getClassifierType() {
        return classifierType;
    }

    /**
     * Sets classifier type.
     *
     * @param classifierType the classifierType to set
     */
    public void setClassifierType(ClassifierType classifierType) {
        this.classifierType = classifierType;
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    /**
     * @param navigationBean the navigationBean to set
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * The type Validate code.
     */
    public class ValidateCode implements java.io.Serializable {

        private String html;
        private String errorMessage;
        private boolean valid;

        /**
         * Verify.
         */
        public void verify() {
            if (ManagedClassifierBean.this.path == null) {
                valid = true;
                html = null;
                errorMessage = null;
                return;
            }
            ManagedClassifierBean.this.setPath(ManagedClassifierBean.this.getPath().trim());
            if (ManagedClassifierBean.this.getPath().length() == 0) {
                valid = true;
                html = null;
                errorMessage = null;
            } else {
                ErrorMessage message = getMainService().verifyCode(ManagedClassifierBean.this.getPath(), getSessionBean().getOperationYear(), ManagedClassifierBean.this.getSelected().getId() == null ? -1L : ManagedClassifierBean.this.getSelected().getId());
                if (message != null) {
                    errorMessage = message.getContent();
                    valid = false;
                } else {
                    valid = true;
                    setParent(getMainService().getParent(ManagedClassifierBean.this.getPath()));
                    setClassifierType(getClassifierTypeService().getByDigits(getPath().length()));
                    Stack<String> htmlStack = new Stack();
                    makehtml(htmlStack, ManagedClassifierBean.this.getPath());
                    html = "";
                    while (!htmlStack.isEmpty()) {
                        html += htmlStack.pop();
                    }
                }
            }
        }

        private void makehtml(Stack<String> htmlStack, String path) {
            Object[] info = getMainService().getParentInfo(path);
            if (info != null) {
                htmlStack.push(
                        "<dl class='label bg-color-blueDark font-sm text-left pull-left margin-right-13' style='margin-bottom: 5px'>"
                        + "     <dt>" + info[2] + "</dt>"
                        + "     <dd style='font-weight: normal;'>" + info[3] + "</dd>"
                        + "</dl>");
                makehtml(htmlStack, (String) info[1]);
            }

        }

        /**
         * Gets html.
         *
         * @return the html
         */
        public String getHtml() {
            return html;
        }

        /**
         * Sets html.
         *
         * @param html the html to set
         */
        public void setHtml(String html) {
            this.html = html;
        }

        /**
         * Gets error message.
         *
         * @return the errorMessage
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * Sets error message.
         *
         * @param errorMessage the errorMessage to set
         */
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        /**
         * Is valid boolean.
         *
         * @return the valid
         */
        public boolean isValid() {
            return valid;
        }

        /**
         * Sets valid.
         *
         * @param valid the valid to set
         */
        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.bean.managed;

import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.INecessaryItemService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed necessary item bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ManagedNecessaryItemBean extends AManagedBean<NecessaryItem, INecessaryItemService> implements ILoadable {

    @ManagedProperty(value = "#{necessaryItemService}")
    private INecessaryItemService mainService;
    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private GenericClassifierSearcher genericClassifierSearcher;
    private SpecificClassifierSearcher specificClassifierSearcher;

    private String genericPath;
    private Classifier classifier;
    private String code;
    private String correlative;
    private String uom;
    private String name;
    private BigDecimal price;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        genericClassifierSearcher = new GenericClassifierSearcher();
        specificClassifierSearcher = new SpecificClassifierSearcher();
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado un item" : "Se ha creado un item";
        boolean result = super.save(); //To change body of generated methods, choose Tools | Templates.
        new SmartMessage("Datos guardados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        return result;
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        genericClassifierSearcher.update();
        specificClassifierSearcher.update();
    }

    @Override
    protected void fillFields() {
        code = selected.getCode();
        correlative = selected.getCorrelative();
        uom = selected.getUom();
        name = selected.getName();
        price = selected.getUnitPrice();
        createAgain = true;
        if (!keepManaged) {
            classifier = selected.getClassifier();
            try {
                genericPath = classifierService.getGeneric(classifier.getPath()).getPath();
            } catch (NullPointerException e) {
                genericPath = null;
            }
            if (selected.getId() == null) {
                specificClassifierSearcher.change();
            }
        }else{
         specificClassifierSearcher.change();
        }
        genericClassifierSearcher.update();
        specificClassifierSearcher.update();
    }

    @Override
    public void doSave(String page, ILoadable loadable) {
        if (!createAgain) {
            setKeepManaged(false);
        } else {
            setKeepManaged(true);
        }
        super.doSave(page, loadable);
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCorrelative(correlative.trim());
        selected.setUom(uom.trim());
        selected.setName(name.trim());
        selected.setUnitPrice(price.setScale(2, RoundingMode.HALF_UP));
        selected.setClassifier(classifier);
        selected.setCode(classifier.getPath() + selected.getCorrelative());
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * @return the mainService
     */
    @Override
    public INecessaryItemService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(INecessaryItemService mainService) {
        this.mainService = mainService;
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
     * Gets specific classifier searcher.
     *
     * @return the specificClassifierSearcher
     */
    public SpecificClassifierSearcher getSpecificClassifierSearcher() {
        return specificClassifierSearcher;
    }

    /**
     * Sets specific classifier searcher.
     *
     * @param specificClassifierSearcher the specificClassifierSearcher to set
     */
    public void setSpecificClassifierSearcher(SpecificClassifierSearcher specificClassifierSearcher) {
        this.specificClassifierSearcher = specificClassifierSearcher;
    }

    /**
     * Gets generic path.
     *
     * @return the genericPath
     */
    public String getGenericPath() {
        return genericPath;
    }

    /**
     * Sets generic path.
     *
     * @param genericPath the genericPath to set
     */
    public void setGenericPath(String genericPath) {
        this.genericPath = genericPath;
    }

    /**
     * Gets classifier.
     *
     * @return the classifier
     */
    public Classifier getClassifier() {
        return classifier;
    }

    /**
     * Sets classifier.
     *
     * @param classifier the classifier to set
     */
    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
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
     * Gets correlative.
     *
     * @return the correlative
     */
    public String getCorrelative() {
        return correlative;
    }

    /**
     * Sets correlative.
     *
     * @param correlative the correlative to set
     */
    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }

    /**
     * Gets uom.
     *
     * @return the uom
     */
    public String getUom() {
        return uom;
    }

    /**
     * Sets uom.
     *
     * @param uom the uom to set
     */
    public void setUom(String uom) {
        this.uom = uom;
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
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
         * Change.
         */
        public void change() {
            specificClassifierSearcher.update();
            classifier = null;
            specificClassifierSearcher.change();
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
     * The type Specific classifier searcher.
     */
    public class SpecificClassifierSearcher implements java.io.Serializable {

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
            criterionList.add(Restrictions.eq("c.codeDigit", new Short("9")));
            criterionList.add(Restrictions.like("path", getGenericPath(), MatchMode.START));
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("name"));
            data = getClassifierService().addRestrictionsVariant(Arrays.asList(aliasList, criterionList, projectionList, orderList));
        }

        /**
         * Instantiates a new Specific classifier searcher.
         */
        public SpecificClassifierSearcher() {
        }

        /**
         * Change.
         */
        public void change() {
            if (classifier == null) {
                correlative = null;
                code = null;
                return;
            } else if (Objects.equals(selected.getClassifier(), classifier)) {
                correlative = selected.getCorrelative();
                code = selected.getCode();
                return;
            }
            String newCode = mainService.getNextCode(classifier == null ? -1 : classifier.getId());
            correlative = newCode;
            code = classifier.getPath() + newCode;
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Long id) {
            try {
                classifier = classifierService.getById(id);
            } catch (Exception e) {
                classifier = null;
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Long getSelected() {
            try {
                return classifier.getId();
            } catch (Exception e) {
                return null;
            }
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
}

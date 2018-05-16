/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.security.model.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Necessary item.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "necessary_item", catalog = "public")
@XmlRootElement
public class NecessaryItem  implements Serializable, AuditoryEntity<Integer,User> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "correlative", nullable = false)
    private String correlative;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "uom", nullable = false)
    private String uom;
    @Column(name = "unit_price", nullable = false,scale = 2,length = 14,precision = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;
    
    @JoinColumn(name = "id_classifier", nullable = false)
    @ManyToOne
    private Classifier classifier;
    
    @JoinColumn(name = "create_uid", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "edit_uid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User editUser;
    @Column(name = "edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    @Column(name = "active", nullable = false)
    private  Boolean active = Boolean.TRUE;
    
    
    @ManyToMany
    @JoinTable(name = "necessary_item_has_abp", joinColumns = {
        @JoinColumn(name = "id_necessary_item", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_abp", nullable = false, updatable = false)}
    )
    private List<ActivityBudgetProgram> activityBudgetPrograms = new ArrayList();
    
    

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Gets unit price.
     *
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets unit price.
     *
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the createUser
     */
    public User getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the editUser
     */
    public User getEditUser() {
        return editUser;
    }

    /**
     * @param editUser the editUser to set
     */
    public void setEditUser(User editUser) {
        this.editUser = editUser;
    }

    /**
     * @return the editDate
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * @param editDate the editDate to set
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * Gets active.
     *
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
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
     * Gets activity budget programs.
     *
     * @return the activity budget programs
     */
    public List<ActivityBudgetProgram> getActivityBudgetPrograms() {
        return activityBudgetPrograms;
    }

    /**
     * Sets activity budget programs.
     *
     * @param activityBudgetPrograms the activity budget programs
     */
    public void setActivityBudgetPrograms(List<ActivityBudgetProgram> activityBudgetPrograms) {
        this.activityBudgetPrograms = activityBudgetPrograms;
    }

    
}

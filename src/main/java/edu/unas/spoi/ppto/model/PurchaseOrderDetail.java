/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.security.model.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Formula;

/**
 * The type Purchase order detail.
 *
 * @author CORE i7
 */
//@Entity
//@Table(name = "purchase_order_detail", catalog = "public")
//@XmlRootElement
public class PurchaseOrderDetail implements Serializable, AuditoryEntity<Long,User> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "detail", nullable = false)
    private String detail;
    @Column(name = "quantity", nullable = false)
    private Double quantity;
    @Column(name = "unitPrice", nullable = false)
    private Double unitPrice;
    @JoinColumn(name = "id_funding_source", nullable = false)
    @ManyToOne
    private FundingSource fundingSource;
    @JoinColumn(name = "id_dependency", nullable = false)
    @ManyToOne
    private Dependency dependency;
    @JoinColumn(name = "id_classifier", nullable = false)
    @ManyToOne
    private Classifier classifier;
    @JoinColumn(name = "id_necessary_item", nullable = false)
    @ManyToOne
    private NecessaryItem necessaryItem;
    @JoinColumn(name = "id_purchase_order", nullable = false)
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    
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
    
    @Formula("(quantity * unitPrice)")
    private Double subtotal;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets detail.
     *
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets unit price.
     *
     * @return the unitPrice
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets unit price.
     *
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets funding source.
     *
     * @return the fundingSource
     */
    public FundingSource getFundingSource() {
        return fundingSource;
    }

    /**
     * Sets funding source.
     *
     * @param fundingSource the fundingSource to set
     */
    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    /**
     * Gets dependency.
     *
     * @return the dependency
     */
    public Dependency getDependency() {
        return dependency;
    }

    /**
     * Sets dependency.
     *
     * @param dependency the dependency to set
     */
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
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
     * Gets necessary item.
     *
     * @return the necessaryItem
     */
    public NecessaryItem getNecessaryItem() {
        return necessaryItem;
    }

    /**
     * Sets necessary item.
     *
     * @param necessaryItem the necessaryItem to set
     */
    public void setNecessaryItem(NecessaryItem necessaryItem) {
        this.necessaryItem = necessaryItem;
    }

    /**
     * Gets purchase order.
     *
     * @return the purchaseOrder
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * Sets purchase order.
     *
     * @param purchaseOrder the purchaseOrder to set
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
     * Gets subtotal.
     *
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * Sets subtotal.
     *
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
}

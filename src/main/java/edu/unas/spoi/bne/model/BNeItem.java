/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.security.model.User;
import gkfire.model.interfac.EntityActivate;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type B ne item.
 *
 * @author User
 */
@Entity
@Table(name = "bne_item", catalog = "public")
@XmlRootElement
public class BNeItem  implements java.io.Serializable,AuditoryEntity<Long,User>,EntityActivate{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "id_poi_activity",nullable = true)
    @ManyToOne
    private POIActivity poiActivity;
    @JoinColumn(name = "id_funding_source",nullable = true)
    @ManyToOne
    private FundingSource fundingSource;
    @Column(name = "product_code", nullable = false)
    private String productCode;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @JoinColumn(name = "id_classifier",nullable = true)
    @ManyToOne
    private Classifier classifier;
    @JoinColumn(name = "id_necessary_item",nullable = true)
    @ManyToOne
    private NecessaryItem necessaryItem;
    @Column(name = "unit_price", nullable = false,length = 14,precision = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;
    
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

    @OneToMany(mappedBy = "bneItem")
    private List<BNeSchedule> bneSchedules;
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
     * Gets poi activity.
     *
     * @return the poiActivity
     */
    public POIActivity getPoiActivity() {
        return poiActivity;
    }

    /**
     * Sets poi activity.
     *
     * @param poiActivity the poiActivity to set
     */
    public void setPoiActivity(POIActivity poiActivity) {
        this.poiActivity = poiActivity;
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
     * Gets product code.
     *
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets product code.
     *
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Gets product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
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
    @Override
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the editUser
     */
    @Override
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
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets bne schedules.
     *
     * @return the bneSchedules
     */
    public List<BNeSchedule> getBneSchedules() {
        return bneSchedules;
    }

    /**
     * Sets bne schedules.
     *
     * @param bneSchedules the bneSchedules to set
     */
    public void setBneSchedules(List<BNeSchedule> bneSchedules) {
        this.bneSchedules = bneSchedules;
    }
}

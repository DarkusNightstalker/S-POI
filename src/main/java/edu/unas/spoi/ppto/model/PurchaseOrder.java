/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.security.model.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The type Purchase order.
 *
 * @author CORE i7
 */
public class PurchaseOrder  implements Serializable, AuditoryEntity<Long,User> {
    private Long id;
    private String businessName;
    private String ruc;
    private String address;
    private Integer deliveryDays;
    private Integer repoDays;
    private Integer garantyMonths;
    private Double total;
    
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
     * Gets business name.
     *
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Sets business name.
     *
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Gets ruc.
     *
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * Sets ruc.
     *
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets delivery days.
     *
     * @return the deliveryDays
     */
    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    /**
     * Sets delivery days.
     *
     * @param deliveryDays the deliveryDays to set
     */
    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    /**
     * Gets repo days.
     *
     * @return the repoDays
     */
    public Integer getRepoDays() {
        return repoDays;
    }

    /**
     * Sets repo days.
     *
     * @param repoDays the repoDays to set
     */
    public void setRepoDays(Integer repoDays) {
        this.repoDays = repoDays;
    }

    /**
     * Gets garanty months.
     *
     * @return the garantyMonths
     */
    public Integer getGarantyMonths() {
        return garantyMonths;
    }

    /**
     * Sets garanty months.
     *
     * @param garantyMonths the garantyMonths to set
     */
    public void setGarantyMonths(Integer garantyMonths) {
        this.garantyMonths = garantyMonths;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
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
}

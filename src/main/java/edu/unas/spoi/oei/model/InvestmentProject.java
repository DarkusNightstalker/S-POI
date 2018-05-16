/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import edu.unas.spoi.security.model.User;
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

/**
 * The type Investment project.
 *
 * @author Jhoan Brayam
 */
@Entity
@Table(name = "investment_project", catalog = "public")
@XmlRootElement
public class InvestmentProject implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "justification", nullable = false)
    private String justification;
    @Column(name = "cost", nullable = false)
    private Double cost;
    @Column(name = "approved", nullable = false)
    private Boolean approved;
    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;
    
    
    @JoinColumn(name = "id_parent",nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependency dependency;
    
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets justification.
     *
     * @return the justification
     */
    public String getJustification() {
        return justification;
    }

    /**
     * Sets justification.
     *
     * @param justification the justification to set
     */
    public void setJustification(String justification) {
        this.justification = justification;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost to set
     */
    public void setCost(Double cost) {
        this.cost = cost;
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
     * Gets create user.
     *
     * @return the createUser
     */
    public User getCreateUser() {
        return createUser;
    }

    /**
     * Sets create user.
     *
     * @param createUser the createUser to set
     */
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets create date.
     *
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets edit user.
     *
     * @return the editUser
     */
    public User getEditUser() {
        return editUser;
    }

    /**
     * Sets edit user.
     *
     * @param editUser the editUser to set
     */
    public void setEditUser(User editUser) {
        this.editUser = editUser;
    }

    /**
     * Gets edit date.
     *
     * @return the editDate
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * Sets edit date.
     *
     * @param editDate the editDate to set
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * Gets approved.
     *
     * @return the approved
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     * Sets approved.
     *
     * @param approved the approved to set
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
    
}

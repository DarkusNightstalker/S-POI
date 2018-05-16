/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.model;

import gkfire.auditory.AuditoryEntity;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
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
 * The type User.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "user",catalog = "public")
@XmlRootElement
public class User implements Serializable,AuditoryEntity<Integer,User>,EntityActivate{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", nullable = false,length = 20)
    private String username;
    @Column(name = "password", nullable = false,length = 15)
    private String password;
    @Column(name = "date_v")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @JoinColumn(name = "id_involved")
    @ManyToOne
    private Involved involved;
    
    @JoinColumn(name = "create_uid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "edit_uid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User editUser;
    @Column(name = "edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;
    
    @Column(name = "active",nullable = false)
    private Boolean active = Boolean.TRUE;
    
    
    @ManyToMany
    @JoinTable(name = "rol_has_user", joinColumns = {
        @JoinColumn(name = "id_user", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_rol", nullable = false, updatable = false)}
    )
    private List<Rol> rols = new ArrayList(0);

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets due date.
     *
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets involved.
     *
     * @return the involved
     */
    public Involved getInvolved() {
        return involved;
    }

    /**
     * Sets involved.
     *
     * @param involved the involved to set
     */
    public void setInvolved(Involved involved) {
        this.involved = involved;
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
     * Gets rols.
     *
     * @return the rols
     */
    public List<Rol> getRols() {
        return rols;
    }

    /**
     * Sets rols.
     *
     * @param rols the rols to set
     */
    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }
    
}

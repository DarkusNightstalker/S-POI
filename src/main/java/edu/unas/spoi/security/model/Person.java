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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Formula;

/**
 * The type Person.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "person",catalog = "public")
@XmlRootElement
public class Person implements Serializable, AuditoryEntity<Integer,User>,EntityActivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "dni", nullable = false,length = 8)
    private String dni;
    @Column(name = "name", nullable = false,length = 50)
    private String name;
    @Column(name = "pattern", nullable = false,length = 20)
    private String pattern;
    @Column(name = "mattern", nullable = false,length = 20)
    private String mattern;
    @Column(name = "email", nullable = false,length = 150)
    private String email;

    @JoinColumn(name = "create_uid",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;
    @Column(name = "create_date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "edit_uid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User editUser;
    @Column(name = "edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;

    @Formula("(name||' '||pattern||' '||mattern)")
    private String fullName;

    @OneToMany(mappedBy = "person")
    private List<Involved> involveds = new ArrayList(0);

    /**
     * Instantiates a new Person.
     *
     * @param id the id
     */
    public Person(Integer id) {
        this.id = id;
    }

    /**
     * Instantiates a new Person.
     */
    public Person() {
    }

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
     * Gets dni.
     *
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets dni.
     *
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
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
     * Gets pattern.
     *
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets pattern.
     *
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets mattern.
     *
     * @return the mattern
     */
    public String getMattern() {
        return mattern;
    }

    /**
     * Sets mattern.
     *
     * @param mattern the mattern to set
     */
    public void setMattern(String mattern) {
        this.mattern = mattern;
    }

    /**
     * @return the createUser
     */
    @Override
    public User getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    @Override
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
    @Override
    public void setEditUser(User editUser) {
        this.editUser = editUser;
    }

    /**
     * @return the editDate
     */
    @Override
    public Date getEditDate() {
        return editDate;
    }

    /**
     * @param editDate the editDate to set
     */
    @Override
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * Gets full name.
     *
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets involveds.
     *
     * @return the involveds
     */
    public List<Involved> getInvolveds() {
        return involveds;
    }

    /**
     * Sets involveds.
     *
     * @param involveds the involveds to set
     */
    public void setInvolveds(List<Involved> involveds) {
        this.involveds = involveds;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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

}

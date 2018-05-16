/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.oei.model.Dependency;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Involved.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "involved",catalog = "public")
@XmlRootElement
public class Involved  implements Serializable,AuditoryEntity<Integer,User>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "charge", nullable = false,length = 50)
    private String charge;
    
    @JoinColumn(name = "id_person",nullable = false)
    @ManyToOne
    private Person person;
    @JoinColumn(name = "id_dependency",nullable = false)
    @ManyToOne
    private Dependency dependency;
    
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
    
    @OneToMany(mappedBy = "involved")
    private List<User> users = new ArrayList(0);
    
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
     * Gets charge.
     *
     * @return the charge
     */
    public String getCharge() {
        return charge;
    }

    /**
     * Sets charge.
     *
     * @param charge the charge to set
     */
    public void setCharge(String charge) {
        this.charge = charge;
    }

    /**
     * Gets person.
     *
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets person.
     *
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

//    /**
//     * @return the dependency
//     */
//    public Dependency getDependency() {
//        return dependency;
//    }
//
//    /**
//     * @param dependency the dependency to set
//     */
//    public void setDependency(Dependency dependency) {
//        this.dependency = dependency;
//    }

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
     * Gets users.
     *
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
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
    
}

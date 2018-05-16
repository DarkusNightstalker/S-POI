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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The type Rol.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "rol", catalog = "public")
@XmlRootElement
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@ToString(of = {"id","code","name"})
public class Rol implements Serializable, AuditoryEntity<Integer,User>,EntityActivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false, length = 5)
    private String code;
    @Column(name = "name", nullable = false, length = 100)
    private String name;

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
    private Boolean active = Boolean.TRUE;
    
    @ManyToMany
    @JoinTable(name = "rol_has_user", joinColumns = {
        @JoinColumn(name = "id_rol", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_user", nullable = false, updatable = false)}
    )
    private List<User> users = new ArrayList(0);
    
    @ManyToMany
    @JoinTable(name = "permission_has_rol", joinColumns = {
        @JoinColumn(name = "id_rol", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_permission", nullable = false, updatable = false)}
    )
    private List<Permission> permissions = new ArrayList(0);

    /**
     * Instantiates a new Rol.
     *
     * @param id the id
     */
    public Rol(Integer id) {
        this.id = id;
    }

}

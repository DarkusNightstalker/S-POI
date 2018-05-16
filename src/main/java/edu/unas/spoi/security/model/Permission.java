/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.model;

import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Permission.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "permission", catalog = "public")
@XmlRootElement
public class Permission  implements Serializable,EntityActivate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false,length = 6)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "limited", nullable = false)
    private Boolean limited = Boolean.FALSE;

    
    @JoinColumn(name = "id_permission_category", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionCategory permissionCategory;
    
    
    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;
    
    @ManyToMany
    @JoinTable(name = "permission_has_rol", joinColumns = {
        @JoinColumn(name = "id_permission", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_rol", nullable = false, updatable = false)}
    )
    private List<Rol> rols = new ArrayList(0);

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
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
     * Gets limited.
     *
     * @return the limited
     */
    public Boolean getLimited() {
        return limited;
    }

    /**
     * Sets limited.
     *
     * @param limited the limited to set
     */
    public void setLimited(Boolean limited) {
        this.limited = limited;
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

    /**
     * Gets permission category.
     *
     * @return the permissionCategory
     */
    public PermissionCategory getPermissionCategory() {
        return permissionCategory;
    }

    /**
     * Sets permission category.
     *
     * @param permissionCategory the permissionCategory to set
     */
    public void setPermissionCategory(PermissionCategory permissionCategory) {
        this.permissionCategory = permissionCategory;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permission other = (Permission) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}

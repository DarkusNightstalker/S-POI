/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import dn.core3.model.interfac.AuditoryEntity;
import dn.core3.model.interfac.EntityActivate;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.User;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The type Dependency.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "dependency", catalog = "public")
@XmlRootElement
@Data
@ToString(of = {"id","path","name"})
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Dependency implements Serializable, AuditoryEntity<Integer, User>, EntityActivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "operation_year", nullable = false)
    private Integer operationYear;
    @Column(name = "operational", nullable = false)
    private Boolean operational = Boolean.FALSE;

    @JoinColumn(name = "id_previous_year_dependency")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependency previousYearDependency;
    @JoinColumn(name = "id_parent")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependency parent;

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

    @OneToMany(mappedBy = "parent")
    private List<Dependency> childrens = new ArrayList(0);
    @OneToMany(mappedBy = "dependency")
    private List<Involved> involveds = new ArrayList();
    @OneToMany(mappedBy = "dependency")
    private List<DependencyHasABP> dependencyHasABPs = new ArrayList(0);

    @ManyToMany
    @JoinTable(name = "dependency_has_abp", joinColumns = {
        @JoinColumn(name = "id_dependency", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_abp", nullable = false, updatable = false)}
    )
    private List<ActivityBudgetProgram> activityBudgetPrograms = new ArrayList();

    /**
     * Instantiates a new Dependency.
     *
     * @param id the id
     */
    public Dependency(Integer id) {
        this.id = id;
    }
}

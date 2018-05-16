/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.security.model.User;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Activity budget program.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "activity_budget_program", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of ={"id"})
public class ActivityBudgetProgram  implements Serializable, AuditoryEntity<Long,User>,EntityActivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "goal")
    private String goal;
    @Column(name = "function")
    private String function;
    @Column(name = "functional_division")
    private String functionalDivision;;
    @Column(name = "functional_group")
    private String functionalGroup;
    
    @Column(name = "functional_sequence_code")
    private String functionalSequence;
    @JoinColumn(name = "id_product_budget_program", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductBudgetProgram productBudgetProgram;
    @JoinColumn(name = "id_uom")
    @ManyToOne(fetch = FetchType.LAZY)
    private UoM uom;
    @JoinColumn(name = "id_budget_program", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private BudgetProgram budgetProgram;
    
    
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
    @JoinTable(name = "abp_has_strategic_activity", joinColumns = {
        @JoinColumn(name = "id_abp", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_strategic_activity", nullable = false, updatable = false)}
    )
    private List<SpecificActivity> strategicActivities = new ArrayList();

    @ManyToMany
    @JoinTable(name = "dependency_has_abp", joinColumns = {
        @JoinColumn(name = "id_abp", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_dependency", nullable = false, updatable = false)}
    )
    private List<Dependency> dependencies = new ArrayList();
    
    
    @ManyToMany
    @JoinTable(name = "necessary_item_has_abp", joinColumns = {
        @JoinColumn(name = "id_abp", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_necessary_item", nullable = false, updatable = false)}
    )
    private List<NecessaryItem> necessaryItems = new ArrayList();

    /**
     * Instantiates a new Activity budget program.
     *
     * @param id the id
     */
    public ActivityBudgetProgram(Long id) {
        this.id = id;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import dn.core3.model.interfac.AuditoryEntity;
import edu.unas.spoi.security.model.User;
import java.io.Serializable;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Institutional strategic plan stratum.
 *
 * @author Darkus Nightmare
 */
@Entity
@Table(name = "institutional_strategic_plan_stratum", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class InstitutionalStrategicPlanStratum implements AuditoryEntity<Integer, User>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "system_name")
    private String systemName;
    @Column(name = "name")
    private String name;
    @Column(name = "code_prefix")
    private String codePrefix;
    @Column(name = "hierarchy_level", nullable = false)
    private Short hierarchyLevel;
    @Column(name = "case_sensitive", nullable = false)
    private Boolean caseSensitive;
    
    @JoinColumn(name = "id_institutional_strategic_plan", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private StrategicPlan institutionalStrategicPlan;
    @JoinColumn(name = "id_parent")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitutionalStrategicPlanStratum parent;    
    
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
     * Instantiates a new Institutional strategic plan stratum.
     *
     * @param id the id
     */
    public InstitutionalStrategicPlanStratum(Integer id) {
        this.id = id;
    }
    
}

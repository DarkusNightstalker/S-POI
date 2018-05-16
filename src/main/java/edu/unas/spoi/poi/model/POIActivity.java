/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.SpecificActivity;
import edu.unas.spoi.security.model.User;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Poi activity.
 *
 * @author User
 */
@Entity
@Table(name = "poi_activity", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class POIActivity  implements Serializable, AuditoryEntity<Long,User>,EntityActivate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "detail", nullable = false)
    private String detail;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "priority", nullable = false)
    private Short priority;
    @JoinColumn(name = "id_poi", nullable = false)
    @ManyToOne
    private POI poi;
    @JoinColumn(name = "id_responsible_dependency", nullable = false)
    @ManyToOne
    private Dependency responsibleDependency;
    @JoinColumn(name = "id_poi_unity", nullable = false)
    @ManyToOne
    private POIUnity poiUnity;
    @JoinColumn(name = "id_specific_activity", nullable = false)
    @ManyToOne
    private SpecificActivity  specificActivity;
    @JoinColumn(name = "id_activity_budget_program", nullable = false)
    @ManyToOne
    private ActivityBudgetProgram  activityBudgetProgram;
    
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

    @OneToMany(mappedBy = "poiActivity")
    private List<POISchedule> poiSchedules;

    /**
     * Instantiates a new Poi activity.
     *
     * @param id the id
     */
    public POIActivity(Long id) {
        this.id = id;
    }
    
    
}

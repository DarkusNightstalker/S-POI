/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import dn.core3.model.interfac.AuditoryEntity;
import dn.core3.model.interfac.EntityActivate;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Institutional strategic plan stratum item.
 *
 * @author Darkus Nightmare
 */
@Entity
@Table(name = "institutional_strategic_plan_stratum_item", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class InstitutionalStrategicPlanStratumItem implements AuditoryEntity<Integer, User>, EntityActivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "id_institutional_strategic_plan_stratum", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitutionalStrategicPlanStratum institutionalStrategicPlanStratum;

    @JoinColumn(name = "id_parent")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitutionalStrategicPlanStratumItem parent;

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

    /**
     * Instantiates a new Institutional strategic plan stratum item.
     *
     * @param id the id
     */
    public InstitutionalStrategicPlanStratumItem(Integer id) {
        this.id = id;
    }
}

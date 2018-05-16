/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import gkfire.auditory.AuditoryEntity;
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
 * The type Strategic axis.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "strategic_axis", catalog = "public")
@XmlRootElement
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class StrategicAxis implements Serializable, AuditoryEntity<Integer,User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    
    @JoinColumn(name = "id_strategic_plan", nullable = false)
    @ManyToOne
    private StrategicPlan strategicPlan;
    
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
     * Instantiates a new Strategic axis.
     *
     * @param id the id
     */
    public StrategicAxis(Integer id) {
        this.id = id;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.oei.model.Dependency;
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
 * The type Poi.
 *
 * @author Jhoan Brayam
 */
@Entity
@Table(name = "poi", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class POI implements java.io.Serializable,AuditoryEntity<Long,User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "approved", nullable = false)
    private Boolean approved = Boolean.FALSE;
    @JoinColumn(name = "id_dependency", nullable = false)
    @ManyToOne
    private Dependency dependency;
    @JoinColumn(name = "id_periodicity", nullable = false)
    @ManyToOne
    private Periodicity  periodicity;
    
    
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
     * Instantiates a new Poi.
     *
     * @param id the id
     */
    public POI(Long id) {
        this.id = id;
    }

    
}

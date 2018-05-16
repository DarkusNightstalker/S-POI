/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.security.model.User;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Funding source.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "funding_source", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class FundingSource implements Serializable, AuditoryEntity<Integer,User>,EntityActivate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "abbr", nullable = false)
    private String abbr;
    
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
    private  Boolean active = Boolean.TRUE;

    /**
     * Instantiates a new Funding source.
     *
     * @param id the id
     */
    public FundingSource(Integer id) {
        this.id = id;
    }  
}

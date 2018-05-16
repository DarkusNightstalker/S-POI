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
 * The type Classifier.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "classifier", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of ={"id"})
public class Classifier  implements Serializable, AuditoryEntity<Long,User>,EntityActivate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "id_classifier_type",nullable = false)
    @ManyToOne
    private ClassifierType classifierType;
    @JoinColumn(name = "id_parent")
    @ManyToOne
    private Classifier parent;
    
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

    @OneToMany(mappedBy = "classifier")
    private List<NecessaryItem> necessaryItems = new ArrayList(0);

    /**
     * Instantiates a new Classifier.
     *
     * @param id the id
     */
    public Classifier(Long id) {
        this.id = id;
    }
    
}

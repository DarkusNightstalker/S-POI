/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Periodicity item.
 *
 * @author Darkus Nightmare
 */
@Entity
@Table(name = "periodicity_item", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PeriodicityItem implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "ordinal", nullable = false)
    private Short ordinal;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "abbreviation")
    private String abbreviation;
    @JoinColumn(name = "id_periodicity", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Periodicity periodicity;

    /**
     * Instantiates a new Periodicity item.
     *
     * @param id the id
     */
    public PeriodicityItem(Integer id) {
        this.id = id;
    }   
}

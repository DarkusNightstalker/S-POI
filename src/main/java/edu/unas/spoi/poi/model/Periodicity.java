/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Periodicity.
 *
 * @author Darkus Nightmare
 */
@Entity
@Table(name = "periodicity", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Periodicity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Short id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "maximum_anually", nullable = false)
    private Short maximumAnually;

    /**
     * Instantiates a new Periodicity.
     *
     * @param id the id
     */
    public Periodicity(Short id) {
        this.id = id;
    }
}

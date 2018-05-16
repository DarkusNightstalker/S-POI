/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.model;

import java.io.Serializable;
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
 * The type Poi schedule.
 *
 * @author User
 */
@Entity
@Table(name = "poi_schedule", catalog = "public")
@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class POISchedule implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;
    @JoinColumn(name = "id_poi_activity", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private POIActivity poiActivity;
    @JoinColumn(name = "id_periodicity_item")
    @ManyToOne
    private PeriodicityItem periodicityItem;

    /**
     * Instantiates a new Poi schedule.
     *
     * @param id the id
     */
    public POISchedule(Long id) {
        this.id = id;
    }   
}

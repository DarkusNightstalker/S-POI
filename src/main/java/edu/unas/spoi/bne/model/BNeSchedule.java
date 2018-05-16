/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.model;

import edu.unas.spoi.bne.model.enumerated.Month;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type B ne schedule.
 *
 * @author Jhoan Brayam
 */
@Entity
@Table(name = "bne_schedule", catalog = "public")
@XmlRootElement
public class BNeSchedule implements java.io.Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "id_bne_item",nullable = true)
    @ManyToOne
    private BNeItem bneItem;
    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;
    @Column(name = "quantity", nullable = false)
    private Double quantity = 0.0;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets bne item.
     *
     * @return the bneItem
     */
    public BNeItem getBneItem() {
        return bneItem;
    }

    /**
     * Sets bne item.
     *
     * @param bneItem the bneItem to set
     */
    public void setBneItem(BNeItem bneItem) {
        this.bneItem = bneItem;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
}

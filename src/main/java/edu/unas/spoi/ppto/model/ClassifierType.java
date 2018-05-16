/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

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

/**
 * The type Classifier type.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "classifier_type", catalog = "public")
@XmlRootElement
public class ClassifierType implements java.io.Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "code_digit", nullable = false)
    private Short codeDigit;
    @Column(name = "part_digit", nullable = false)
    private Short partDigit;
    
    @JoinColumn(name = "id_parent")
    @ManyToOne(fetch = FetchType.LAZY)
    private ClassifierType parent;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets parent.
     *
     * @return the parent
     */
    public ClassifierType getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param parent the parent to set
     */
    public void setParent(ClassifierType parent) {
        this.parent = parent;
    }

    /**
     * Gets code digit.
     *
     * @return the codeDigit
     */
    public Short getCodeDigit() {
        return codeDigit;
    }

    /**
     * Sets code digit.
     *
     * @param codeDigit the codeDigit to set
     */
    public void setCodeDigit(Short codeDigit) {
        this.codeDigit = codeDigit;
    }

    /**
     * Gets part digit.
     *
     * @return the partDigit
     */
    public Short getPartDigit() {
        return partDigit;
    }

    /**
     * Sets part digit.
     *
     * @param partDigit the partDigit to set
     */
    public void setPartDigit(Short partDigit) {
        this.partDigit = partDigit;
    }
    
    
}

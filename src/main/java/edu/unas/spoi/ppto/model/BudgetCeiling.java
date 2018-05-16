/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import edu.unas.spoi.oei.model.Dependency;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * The type Budget ceiling.
 *
 * @author Jhoan Brayam
 */
@Entity
@Table(name = "budget_ceiling", catalog = "public")
@XmlRootElement
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class BudgetCeiling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "id_dependency", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependency dependency;
    @JoinColumn(name = "id_funding_source", nullable = false)
    @ManyToOne()
    private FundingSource fundingSource;
    @JoinColumn(name = "id_classifier", nullable = false)
    @ManyToOne
    private Classifier classifier;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO;

    /**
     * Instantiates a new Budget ceiling.
     *
     * @param id the id
     */
    public BudgetCeiling(Long id) {
        this.id = id;
    }

    
}

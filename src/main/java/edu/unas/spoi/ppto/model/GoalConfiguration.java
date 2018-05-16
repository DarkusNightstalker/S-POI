/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.model;

import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Goal configuration.
 *
 * @author CORE i7
 */
@Entity
@Table(name = "goal_configuration", catalog = "public")
@XmlRootElement
public class GoalConfiguration implements  java.io.Serializable{
    
    @Id
    @JoinColumn(name = "id_functional_sequence",nullable = false)
    @ManyToOne
    private ActivityBudgetProgram activityBudgetProgram;
    
    @Id
    @JoinColumn(name = "id_classifier",nullable = false)
    @ManyToOne
    private Classifier classifier;
    
    @Id
    @JoinColumn(name = "id_funding_source",nullable = false)
    @ManyToOne
    private FundingSource fundingSource;


    /**
     * Gets classifier.
     *
     * @return the classifier
     */
    public Classifier getClassifier() {
        return classifier;
    }

    /**
     * Sets classifier.
     *
     * @param classifier the classifier to set
     */
    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    /**
     * Gets funding source.
     *
     * @return the fundingSource
     */
    public FundingSource getFundingSource() {
        return fundingSource;
    }

    /**
     * Sets funding source.
     *
     * @param fundingSource the fundingSource to set
     */
    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    /**
     * Gets activity budget program.
     *
     * @return the activityBudgetProgram
     */
    public ActivityBudgetProgram getActivityBudgetProgram() {
        return activityBudgetProgram;
    }

    /**
     * Sets activity budget program.
     *
     * @param activityBudgetProgram the activityBudgetProgram to set
     */
    public void setActivityBudgetProgram(ActivityBudgetProgram activityBudgetProgram) {
        this.activityBudgetProgram = activityBudgetProgram;
    }
    
    
}

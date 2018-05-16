/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Dependency has abp.
 *
 * @author Jhoan Brayam
 */
@Entity
@Table(name = "dependency_has_abp", catalog = "public")
@XmlRootElement
public class DependencyHasABP implements java.io.Serializable{
    
    @Id
    @JoinColumn(name = "id_dependency")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependency dependency;
    @Id
    @JoinColumn(name = "id_abp")
    @ManyToOne
    private ActivityBudgetProgram activityBudgetProgram;

    /**
     * Gets dependency.
     *
     * @return the dependency
     */
    public Dependency getDependency() {
        return dependency;
    }

    /**
     * Sets dependency.
     *
     * @param dependency the dependency to set
     */
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    /**
     * Gets activity budget program.
     *
     * @return the specificGoal
     */
    public ActivityBudgetProgram getActivityBudgetProgram() {
        return activityBudgetProgram;
    }

    /**
     * Sets activity budget program.
     *
     * @param activityBudgetProgram the specificGoal to set
     */
    public void setActivityBudgetProgram(ActivityBudgetProgram activityBudgetProgram) {
        this.activityBudgetProgram = activityBudgetProgram;
    }
    
    
    
}

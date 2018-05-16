/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.model.enumerated;

/**
 * The enum Budget category.
 *
 * @author User
 */
public enum BudgetCategory {
    /**
     * The Budget project.
     */
    BUDGET_PROJECT("Proyecto Presupuestal"),
    /**
     * The Budget program.
     */
    BUDGET_PROGRAM("Programa Presupuestal"),
    /**
     * The Core activity.
     */
    CORE_ACTIVITY("Acciones centrales"),
    /**
     * The Common activity.
     */
    COMMON_ACTIVITY("Acciones comunes"),
    /**
     * Apnop budget category.
     */
    APNOP("APNOP");
    private final String description;

    private BudgetCategory(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}

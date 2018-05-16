/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.model.enumerated;

/**
 * The enum Month.
 *
 * @author Jhoan Brayam
 */
public enum Month {
    /**
     * Enero month.
     */
    Enero("Ene"),
    /**
     * Febrero month.
     */
    Febrero("Feb"),
    /**
     * Marzo month.
     */
    Marzo("Mar"),
    /**
     * Abril month.
     */
    Abril("Abr"),
    /**
     * Mayo month.
     */
    Mayo("May"),
    /**
     * Junio month.
     */
    Junio("Jun"),
    /**
     * Julio month.
     */
    Julio("Jul"),
    /**
     * Agosto month.
     */
    Agosto("Ago"),
    /**
     * Septiembre month.
     */
    Septiembre("Sep"),
    /**
     * Octubre month.
     */
    Octubre("Oct"),
    /**
     * Noviembre month.
     */
    Noviembre("Nov"),
    /**
     * Diciembre month.
     */
    Diciembre("Dic");

    private final String abbr;

    private Month(String month) {
        this.abbr = month;
    }

    /**
     * Gets abbr.
     *
     * @return the abbr
     */
    public String getAbbr() {
        return abbr;
    }
    
    

}

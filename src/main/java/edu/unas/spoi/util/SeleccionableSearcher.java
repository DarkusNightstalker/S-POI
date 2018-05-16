/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

/**
 * The type Seleccionable searcher.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 * @author Darkus Nightmare
 */
public abstract class SeleccionableSearcher<T,S> extends SimpleSearcher<T>{

    /**
     * The Selected.
     */
    protected S selected;

    /**
     * Gets selected.
     *
     * @return the selected
     */
    public S getSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected to set
     */
    public void setSelected(S selected) {
        this.selected = selected;
    }
}

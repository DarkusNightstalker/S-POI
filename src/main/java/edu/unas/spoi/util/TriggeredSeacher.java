/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

/**
 * The type Triggered seacher.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 * @author Darkus Nightmare
 */
public abstract class TriggeredSeacher<T,S> extends SeleccionableSearcher<T,S> {

    /**
     * Change.
     */
    public abstract void change();

}

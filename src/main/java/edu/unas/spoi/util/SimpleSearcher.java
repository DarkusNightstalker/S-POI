/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

import java.util.List;

/**
 * The type Simple searcher.
 *
 * @param <T> the type parameter
 * @author Darkus Nightmare
 */
public abstract class SimpleSearcher<T> implements java.io.Serializable {

    /**
     * The Data.
     */
    protected List<T> data;

    /**
     * Update.
     */
    public abstract void update();

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

}

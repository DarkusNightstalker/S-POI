/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.util;

/**
 * The type Matematical util.
 *
 * @author Danny
 */
public class MatematicalUtil {

    /**
     * Round double.
     *
     * @param value  the value
     * @param places the places
     * @return the double
     */
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

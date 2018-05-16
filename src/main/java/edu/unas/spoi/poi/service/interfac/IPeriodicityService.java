/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service.interfac;

import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.poi.model.Periodicity;

/**
 * The interface Periodicity service.
 *
 * @author Darkus Nightmare
 */
public interface IPeriodicityService extends IGenericService<Periodicity, Short> {

    /**
     * Gets by.
     *
     * @param operationYear the operation year
     * @return the by
     */
    public Periodicity getBy(Integer operationYear);

}

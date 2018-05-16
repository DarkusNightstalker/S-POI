/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao.interfac;

import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.model.Periodicity;

/**
 * The interface Periodicity dao.
 *
 * @author Darkus Nightmare
 */
public interface IPeriodicityDao extends IGenericDao<Periodicity, Short>{

    /**
     * Gets by id.
     *
     * @param operationYear the operation year
     * @return the by id
     */
    public Periodicity getById(Integer operationYear);
    
}

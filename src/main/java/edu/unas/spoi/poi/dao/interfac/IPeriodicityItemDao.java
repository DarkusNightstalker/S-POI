/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao.interfac;

import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.model.Periodicity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import java.util.List;

/**
 * The interface Periodicity item dao.
 *
 * @author Darkus Nightmare
 */
public interface IPeriodicityItemDao extends IGenericDao<PeriodicityItem,Integer>{

    /**
     * Gets list by.
     *
     * @param periodicity the periodicity
     * @return the list by
     */
    public List<PeriodicityItem> getListBy(Periodicity periodicity);
    
}

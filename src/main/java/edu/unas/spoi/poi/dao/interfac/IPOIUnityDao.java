/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao.interfac;

import edu.unas.spoi.poi.model.POIUnity;
import gkfire.hibernate.generic.interfac.IGenericDao;

/**
 * The interface Ipoi unity dao.
 *
 * @author Jhoan Brayam
 */
public interface IPOIUnityDao extends IGenericDao<POIUnity, Integer>{

    /**
     * Exist code boolean.
     *
     * @param code the code
     * @param id   the id
     * @return the boolean
     */
    public boolean existCode(String code, Integer id);

    
}

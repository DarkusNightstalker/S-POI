/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service.interfac;

import edu.unas.spoi.poi.model.POIUnity;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.io.Serializable;

/**
 * The interface Ipoi unity service.
 *
 * @author Jhoan Brayam
 */
public interface IPOIUnityService extends IGenericService<POIUnity, Integer>{

    /**
     * Exist code boolean.
     *
     * @param code the code
     * @param id   the id
     * @return the boolean
     */
    public boolean existCode(String code, Integer id);
    
}

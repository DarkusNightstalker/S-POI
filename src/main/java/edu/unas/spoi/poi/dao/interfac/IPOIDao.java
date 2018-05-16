/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao.interfac;

import edu.unas.spoi.poi.model.POI;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.util.List;
import java.util.Map;

/**
 * The interface Ipoi dao.
 *
 * @author Jhoan Brayam
 */
public interface IPOIDao  extends IGenericDao<POI, Long>{

    /**
     * Gets by.
     *
     * @param operationYear the operation year
     * @param dependencyId  the dependency id
     * @return the by
     */
    public POI getBy(Integer operationYear, Integer dependencyId);

    /**
     * Gets list full data.
     *
     * @param operationYear the operation year
     * @return the list full data
     */
    public List<Map<String, Object>> getListFullData(Integer operationYear);
    
}

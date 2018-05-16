/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.dao.interfac;

import edu.unas.spoi.bne.model.BNeItem;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.util.List;
import java.util.Map;

/**
 * The interface Ib ne item dao.
 *
 * @author Jhoan Brayam
 */
public interface IBNeItemDao extends IGenericDao<BNeItem, Long>{


    /**
     * Gets list full data.
     *
     * @param operationYear the operation year
     * @return the list full data
     */
    public List<Map<String, Object>> getListFullData(Integer operationYear);

    /**
     * Gets scheduled amount.
     *
     * @param dependencyId    the dependency id
     * @param operationYear   the operation year
     * @param fundingSourceId the funding source id
     * @param classifierId    the classifier id
     * @return the scheduled amount
     */
    public List<Map<String, Object>> getScheduledAmount(Integer dependencyId, Integer operationYear, Integer fundingSourceId, Long classifierId);

    /**
     * Gets scheduled amount.
     *
     * @param dependencyId  the dependency id
     * @param operationYear the operation year
     * @param classifierId  the classifier id
     * @return the scheduled amount
     */
    public List<Map<String, Object>> getScheduledAmount(Integer dependencyId, Integer operationYear, Long classifierId);
    
}

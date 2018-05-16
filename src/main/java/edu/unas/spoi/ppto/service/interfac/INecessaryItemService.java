/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service.interfac;

import gkfire.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.ppto.model.NecessaryItem;

/**
 * The interface Necessary item service.
 *
 * @author CORE i7
 */
public interface INecessaryItemService  extends IGenericService<NecessaryItem, Integer>{
    /**
     * Gets next code.
     *
     * @param idClassifier the id classifier
     * @return the next code
     */
    public String  getNextCode(Long idClassifier);

    /**
     * Exist code boolean.
     *
     * @param code the code
     * @return the boolean
     */
    public boolean existCode(String code);
}

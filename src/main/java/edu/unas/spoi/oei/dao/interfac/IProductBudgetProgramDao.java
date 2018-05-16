/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.model.ProductBudgetProgram;

/**
 * The interface Product budget program dao.
 *
 * @author CORE i7
 */
public interface IProductBudgetProgramDao extends IGenericDao<ProductBudgetProgram, Long> {
    /**
     * Exist code boolean.
     *
     * @param code      the code
     * @param exception the exception
     * @return the boolean
     */
    public boolean existCode(String code,Long exception);
}

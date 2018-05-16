/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.ppto.model.FundingSource;
import java.util.List;

/**
 * The interface Funding source dao.
 *
 * @author CORE i7
 */
public interface IFundingSourceDao extends IGenericDao<FundingSource, Integer> {

    /**
     * Exist code boolean.
     *
     * @param code      the code
     * @param exception the exception
     * @return the boolean
     */
    public boolean existCode(String code, Integer exception);

    /**
     * Gets list basic data.
     *
     * @param idNotInclude the id not include
     * @return the list basic data
     */
    public List getListBasicData(List<Integer> idNotInclude);

    /**
     * Gets list basic data.
     *
     * @return the list basic data
     */
    public List getListBasicData();

    /**
     * Gets funding sources.
     *
     * @return the funding sources
     */
    public List<FundingSource> getFundingSources();
}

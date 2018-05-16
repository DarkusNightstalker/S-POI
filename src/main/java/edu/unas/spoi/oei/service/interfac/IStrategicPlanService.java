/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service.interfac;

import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.oei.model.StrategicPlan;
import java.util.List;

/**
 * The interface Strategic plan service.
 *
 * @author CORE i7
 */
public interface IStrategicPlanService extends IGenericService<StrategicPlan, Integer> {

    /**
     * Gets by.
     *
     * @param year the year
     * @return the by
     */
    public StrategicPlan getBy(Integer year);

    /**
     * Gets list lazy for main view.
     *
     * @param page the page
     * @param rows the rows
     * @param year the year
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(int page, int rows, Integer year);

    /**
     * Count list lazy for main view long.
     *
     * @param year the year
     * @return the long
     */
    public Long countListLazyForMainView(Integer year);

    /**
     * Validate year boolean.
     *
     * @param operationYear the operation year
     * @param currentId     the current id
     * @return the boolean
     */
    public boolean validateYear(Integer operationYear,Integer currentId);
}

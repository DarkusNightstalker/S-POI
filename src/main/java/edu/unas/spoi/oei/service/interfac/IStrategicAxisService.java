/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service.interfac;

import dn.core3.hibernate.OrderFactory;
import gkfire.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.oei.model.StrategicAxis;
import java.util.List;

/**
 * The interface Strategic axis service.
 *
 * @author CORE i7
 */
public interface IStrategicAxisService  extends IGenericService<StrategicAxis, Integer>{

    /**
     * Validate code boolean.
     *
     * @param code            the code
     * @param idStrategicPlan the id strategic plan
     * @param currentId       the current id
     * @return the boolean
     */
    public boolean validateCode(String code, Integer idStrategicPlan, Integer currentId);

    /**
     * Gets list lazy for main view.
     *
     * @param page         the page
     * @param rows         the rows
     * @param year         the year
     * @param code         the code
     * @param name         the name
     * @param orderFactory the order factory
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(int page, int rows, Integer year, String code, String name, OrderFactory orderFactory);

    /**
     * Gets list basic data.
     *
     * @param strategicPlanId the strategic plan id
     * @return the list basic data
     */
    public List getListBasicData(Integer strategicPlanId);
    
}

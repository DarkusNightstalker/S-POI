/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service.interfac;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.oei.model.StrategicGoal;
import java.util.List;

/**
 * The interface Strategic goal service.
 *
 * @author Jhoan Brayam
 */
public interface IStrategicGoalService extends IGenericService<StrategicGoal, Integer>{

    /**
     * Validate code boolean.
     *
     * @param code            the code
     * @param idStrategicPlan the id strategic plan
     * @param id              the id
     * @return the boolean
     */
    public boolean validateCode(String code, Integer idStrategicPlan, Integer id);

    /**
     * Gets list lazy for main view.
     *
     * @param page            the page
     * @param recordsPerPage  the records per page
     * @param strategicPlanId the strategic plan id
     * @param code            the code
     * @param name            the name
     * @param strategicAxisId the strategic axis id
     * @param active          the active
     * @param orderFactory    the order factory
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(int page, int recordsPerPage, Integer strategicPlanId, String code, String name, Integer strategicAxisId,Boolean active, OrderFactory orderFactory);

    /**
     * Count list for main view long.
     *
     * @param strategicPlanId the strategic plan id
     * @param code            the code
     * @param name            the name
     * @param strategicAxisId the strategic axis id
     * @param active          the active
     * @return the long
     */
    public Long countListForMainView(Integer strategicPlanId, String code, String name, Integer strategicAxisId,Boolean active);
}

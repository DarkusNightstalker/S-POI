/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao.interfac;

import dn.core3.hibernate.OrderFactory;
import gkfire.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.model.SpecificActivity;
import java.util.List;

/**
 * The interface Specific activity dao.
 *
 * @author CORE i7
 */
public interface ISpecificActivityDao extends IGenericDao<SpecificActivity, Integer> {

    /**
     * Validate code boolean.
     *
     * @param code           the code
     * @param idSpecificGoal the id specific goal
     * @param id             the id
     * @return the boolean
     */
    public boolean validateCode(String code, Integer idSpecificGoal, Integer id);

    /**
     * Count list for main view long.
     *
     * @param strategicPlanId the strategic plan id
     * @param code            the code
     * @param name            the name
     * @param strategicAxisId the strategic axis id
     * @param strategicGoalId the strategic goal id
     * @param active          the active
     * @return the long
     */
    public Long countListForMainView(Integer strategicPlanId, String code, String name, Integer strategicAxisId, Integer strategicGoalId, Boolean active);

    /**
     * Gets list lazy for main view.
     *
     * @param page            the page
     * @param recordsPerPage  the records per page
     * @param strategicPlanId the strategic plan id
     * @param code            the code
     * @param name            the name
     * @param strategicAxisId the strategic axis id
     * @param strategicGoalId the strategic goal id
     * @param active          the active
     * @param orderFactory    the order factory
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(int page, int recordsPerPage, Integer strategicPlanId, String code, String name, Integer strategicAxisId, Integer strategicGoalId, Boolean active, OrderFactory orderFactory);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service.interfac;

import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.DependencyHasABP;
import edu.unas.spoi.security.model.User;
import gkfire.hibernate.OrderFactory;
import java.util.List;
import java.util.Map;

/**
 * The interface Dependency service.
 *
 * @author CORE i7
 */
public interface IDependencyService extends IGenericService<Dependency, Integer> {

    /**
     * Save or update.
     *
     * @param dependencyHasSpecificGoal the dependency has specific goal
     */
    public void saveOrUpdate(DependencyHasABP dependencyHasSpecificGoal);

    /**
     * Delete.
     *
     * @param dependencyHasSpecificGoal the dependency has specific goal
     */
    public void delete(DependencyHasABP dependencyHasSpecificGoal);

    /**
     * Gets specific goal in year.
     *
     * @param dependencyId the dependency id
     * @param year         the year
     * @return the specific goal in year
     */
    public List<DependencyHasABP> getSpecificGoalInYear(Integer dependencyId, Integer year);

    /**
     * Gets parent.
     *
     * @param dependencyId the dependency id
     * @return the parent
     */
    public Dependency getParent(Integer dependencyId);

    /**
     * Gets list lazy for main view.
     *
     * @param page          the page
     * @param rows          the rows
     * @param path          the path
     * @param name          the name
     * @param operational   the operational
     * @param operationYear the operation year
     * @param active        the active
     * @param orderObject   the order object
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(
            int page,
            int rows,
            String path,
            String name,
            Boolean operational,
            Integer operationYear,
            Boolean active,
            OrderFactory orderObject);

    /**
     * Count list for main view long.
     *
     * @param path          the path
     * @param name          the name
     * @param operational   the operational
     * @param operationYear the operation year
     * @param active        the active
     * @return the long
     */
    public Long countListForMainView(
            String path,
            String name,
            Boolean operational,
            Integer operationYear,
            Boolean active);

    /**
     * Gets list for user assigment.
     *
     * @param personId            the person id
     * @param currentDependencyId the current dependency id
     * @param operationYear       the operation year
     * @return the list for user assigment
     */
    public List getListForUserAssigment(Integer personId, Integer currentDependencyId,Integer operationYear);

    /**
     * Gets list for copy previous year.
     *
     * @param operationYear the operation year
     * @return the list for copy previous year
     */
    public List getListForCopyPreviousYear(Integer operationYear);

    /**
     * Copy data to year.
     *
     * @param data          the data
     * @param operationYear the operation year
     * @param currentUser   the current user
     */
    public void copyDataToYear(List<Map<String, Object>> data, Integer operationYear, User currentUser);

    /**
     * Gets list operational basic data.
     *
     * @param operationYear the operation year
     * @return the list operational basic data
     */
    public List getListOperationalBasicData(Integer operationYear);

    /**
     * Validate path boolean.
     *
     * @param path          the path
     * @param operationYear the operation year
     * @param currentId     the current id
     * @return the boolean
     */
    public boolean validatePath(String path, Integer operationYear, Integer currentId);

    /**
     * Gets by.
     *
     * @param path          the path
     * @param operationYear the operation year
     * @return the by
     */
    public Dependency getBy(String path, Integer operationYear);

    /**
     * Save or update.
     *
     * @param selected       the selected
     * @param budgetCeilings the budget ceilings
     */
    public void saveOrUpdate(Dependency selected, Map<Integer, List<Map<String, Object>>> budgetCeilings);

    /**
     * Gets allow copy.
     *
     * @param operationYear the operation year
     * @return the allow copy
     */
    public boolean getAllowCopy(Integer operationYear);

    /**
     * Gets children dependencies basic data.
     *
     * @param path          the path
     * @param operationYear the operation year
     * @return the children dependencies basic data
     */
    public List getChildrenDependenciesBasicData(String path, Integer operationYear);


}

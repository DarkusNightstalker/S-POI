/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao.interfac;

import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.DependencyHasABP;
import gkfire.hibernate.OrderFactory;
import java.util.List;
import java.util.Map;

/**
 * The interface Dependency dao.
 *
 * @author CORE i7
 */
public interface IDependencyDao extends IGenericDao<Dependency, Integer> {

    /**
     * Save or update.
     *
     * @param dependencyHasSpecificGoal the dependency has specific goal
     */
    public void saveOrUpdate(DependencyHasABP dependencyHasSpecificGoal);

    /**
     * Gets abp in year.
     *
     * @param dependencyId the dependency id
     * @param year         the year
     * @return the abp in year
     */
    public List<DependencyHasABP> getABPInYear(Integer dependencyId, Integer year);

    /**
     * Gets parent.
     *
     * @param dependencyId the dependency id
     * @return the parent
     */
    public Dependency getParent(Integer dependencyId);

    /**
     * Delete.
     *
     * @param dependencyHasSpecificGoal the dependency has specific goal
     */
    public void delete(DependencyHasABP dependencyHasSpecificGoal);

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
    public List getListLazyForMainView(int page, int rows, String path, String name, Boolean operational, Integer operationYear, Boolean active, OrderFactory orderObject);

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
    public Long countListForMainView(String path, String name, Boolean operational, Integer operationYear, Boolean active);

    /**
     * Gets list for user assigment.
     *
     * @param excludeIds          the exclude ids
     * @param currentDependencyId the current dependency id
     * @param operationYear       the operation year
     * @return the list for user assigment
     */
    public List getListForUserAssigment(List<Integer> excludeIds, Integer currentDependencyId,Integer operationYear);

    /**
     * Gets list for copy previous year.
     *
     * @param operationYear the operation year
     * @return the list for copy previous year
     */
    public List getListForCopyPreviousYear(Integer operationYear);

    /**
     * Gets activity budget programs ids.
     *
     * @param dependencyId the dependency id
     * @return the activity budget programs ids
     */
    public List<ActivityBudgetProgram> getActivityBudgetProgramsIds(Integer dependencyId);

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

    /**
     * Delete parent.
     *
     * @param id the id
     */
    public void deleteParent(Integer id);
}

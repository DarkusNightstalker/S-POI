/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import java.util.List;
import java.util.Map;

/**
 * The interface Activity budget program dao.
 *
 * @author CORE i7
 */
public interface IActivityBudgetProgramDao extends IGenericDao<ActivityBudgetProgram, Long> {

    /**
     * Exist code boolean.
     *
     * @param code the code
     * @param id   the id
     * @return the boolean
     */
    public boolean existCode(String code, Long id);

    /**
     * Gets list for assigment strategic activity.
     *
     * @param strategicActivityId the strategic activity id
     * @return the list for assigment strategic activity
     */
    public List<Map<String, Object>> getListForAssigmentStrategicActivity(Integer strategicActivityId);

    /**
     * Gets list for give strategic activity.
     *
     * @param idsForExclude the ids for exclude
     * @return the list for give strategic activity
     */
    public List getListForGiveStrategicActivity(List<Long> idsForExclude);

    /**
     * Gets list for give strategic activity.
     *
     * @return the list for give strategic activity
     */
    public List getListForGiveStrategicActivity();

    /**
     * Gets list for assigment dependency.
     *
     * @param dependencyId the dependency id
     * @return the list for assigment dependency
     */
    public List getListForAssigmentDependency(Integer dependencyId);

    /**
     * Gets list for give dependency.
     *
     * @param idsForExclude the ids for exclude
     * @return the list for give dependency
     */
    public List getListForGiveDependency(List<Long> idsForExclude);

    /**
     * Gets list for give dependency.
     *
     * @return the list for give dependency
     */
    public List getListForGiveDependency();
}

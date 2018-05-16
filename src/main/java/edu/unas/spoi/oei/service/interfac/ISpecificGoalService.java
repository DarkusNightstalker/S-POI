/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service.interfac;

import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.oei.model.SpecificGoal;
import java.util.List;

/**
 * The interface Specific goal service.
 *
 * @author CORE i7
 */
public interface ISpecificGoalService extends IGenericService<SpecificGoal, Integer>{

    /**
     * Exist code boolean.
     *
     * @param code            the code
     * @param idStrategicAxis the id strategic axis
     * @param id              the id
     * @return the boolean
     */
    public boolean existCode(String code, Integer idStrategicAxis, Integer id);

    /**
     * Gets list basic data by strategic axis.
     *
     * @param strategicAxisId the strategic axis id
     * @return the list basic data by strategic axis
     */
    public List getListBasicDataByStrategicAxis(Integer strategicAxisId);
    
}

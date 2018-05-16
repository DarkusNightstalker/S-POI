/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.ppto.dao.interfac.IGoalConfigurationDao;
import edu.unas.spoi.ppto.model.GoalConfiguration;
import java.io.Serializable;

/**
 * The type Goal configuration dao.
 *
 * @author CORE i7
 */
public class GoalConfigurationDao extends GenericDao<GoalConfiguration, Serializable> implements IGoalConfigurationDao {
    
}

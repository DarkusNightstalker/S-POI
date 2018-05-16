/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.dao.interfac.ISpecificGoalDao;
import edu.unas.spoi.oei.model.SpecificGoal;
import edu.unas.spoi.oei.service.interfac.ISpecificGoalService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Specific goal service.
 *
 * @author CORE i7
 */
@Service("specificGoalService")
public class SpecificGoalService extends GenericService<SpecificGoal, Integer> implements ISpecificGoalService {

    @Autowired
    @Qualifier("specificGoalDao")
    private ISpecificGoalDao specificGoalDao;

    @Override
    public boolean existCode(String code, Integer idStrategicAxis, Integer id) {
        return specificGoalDao.existCode(code, idStrategicAxis, id);
    }

    @Override
    protected IGenericDao<SpecificGoal, Integer> getBasicDao() {
        return specificGoalDao;
    }

    @Override
    public List getListBasicDataByStrategicAxis(Integer strategicAxisId) {
        return specificGoalDao.getListBasicDataByStrategicAxis(strategicAxisId);
    }

}

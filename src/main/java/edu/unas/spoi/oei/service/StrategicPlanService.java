/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.oei.dao.interfac.IStrategicPlanDao;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Strategic plan service.
 *
 * @author CORE i7
 */
@Service("strategicPlanService")
public class StrategicPlanService extends GenericService<StrategicPlan, Integer> implements IStrategicPlanService {

    @Autowired
    @Qualifier("strategicPlanDao")
    private IStrategicPlanDao strategicPlanDao;

    @Override
    public StrategicPlan getBy(Integer year) {
        return strategicPlanDao.getBy(year);
    }

    @Override
    protected IGenericDao<StrategicPlan, Integer> getBasicDao() {
        return strategicPlanDao;
    }

    @Override
    public List getListLazyForMainView(int page, int rows, Integer year) {
        return strategicPlanDao.getListLazyForMainView(page, rows, year);
    }

    @Override
    public Long countListLazyForMainView(Integer year) {
        return strategicPlanDao.countListLazyForMainView(year);
    }

    @Override
    public boolean validateYear(Integer operationYear,Integer currentId) {
        return strategicPlanDao.validateYear(operationYear,currentId);
    }
}

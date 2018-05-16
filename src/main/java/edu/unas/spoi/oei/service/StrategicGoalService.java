/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import dn.core3.model.util.Auditory;
import edu.unas.spoi.oei.dao.interfac.ISpecificGoalDao;
import edu.unas.spoi.oei.dao.interfac.IStrategicGoalDao;
import edu.unas.spoi.oei.model.SpecificGoal;
import edu.unas.spoi.oei.model.StrategicGoal;
import edu.unas.spoi.oei.service.interfac.IStrategicGoalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Strategic goal service.
 *
 * @author Jhoan Brayam
 */
@Service("strategicGoalService")
public class StrategicGoalService extends GenericService<StrategicGoal, Integer> implements IStrategicGoalService {

    @Autowired
    @Qualifier("strategicGoalDao")
    private IStrategicGoalDao strategicGoalDao;
    @Autowired
    @Qualifier("specificGoalDao")
    private ISpecificGoalDao specificGoalDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpdate(StrategicGoal object) {
        super.saveOrUpdate(object); 
        SpecificGoal specificGoal = specificGoalDao.getByCode(object.getCode(),object.getId());
        if(specificGoal == null ) specificGoal = new SpecificGoal();
        specificGoal.setCode(object.getCode());
        specificGoal.setName(object.getName());
        specificGoal.setStrategicGoal(object);
        specificGoal.setActive(Boolean.TRUE);
        Auditory.make(specificGoal, object.getEditUser() == null ? object.getCreateUser() : object.getEditUser());
        specificGoalDao.saveOrUpdate(specificGoal);
    }

    @Override
    public boolean validateCode(String code, Integer idStrategicPlan, Integer id) {
        return strategicGoalDao.validateCode(code, idStrategicPlan, id);
    }

    @Override
    public List getListLazyForMainView(int page, int recordsPerPage, Integer strategicPlanId, String code, String name, Integer strategicAxisId, Boolean active, OrderFactory orderFactory) {
        return strategicGoalDao.getListLazyForMainView(page, recordsPerPage, strategicPlanId, code, name, strategicAxisId, active, orderFactory);
    }

    @Override
    public Long countListForMainView(Integer strategicPlanId, String code, String name, Integer strategicAxisId, Boolean active) {
        return strategicGoalDao.countListForMainView(strategicPlanId, code, name, strategicAxisId, active);
    }

    @Override
    protected IGenericDao<StrategicGoal, Integer> getBasicDao() {
        return strategicGoalDao;
    }
}

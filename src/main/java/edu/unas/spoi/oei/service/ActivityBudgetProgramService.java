/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import edu.unas.spoi.oei.dao.interfac.IActivityBudgetProgramDao;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.service.interfac.IActivityBudgetProgramService;
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
 * The type Activity budget program service.
 *
 * @author CORE i7
 */
@Service("activityBudgetProgramService")
public class ActivityBudgetProgramService implements IActivityBudgetProgramService{
    
    @Autowired
    @Qualifier("activityBudgetProgramDao")
    private IActivityBudgetProgramDao activityBudgetProgramDao;

    @Override
    public Serializable save(ActivityBudgetProgram object) {
        return activityBudgetProgramDao.save(object);
    }

    @Override
    public void update(ActivityBudgetProgram object) {
        activityBudgetProgramDao.update(object);
    }

    @Override
    public void saveOrUpdate(ActivityBudgetProgram object) {
        activityBudgetProgramDao.saveOrUpdate(object);
    }

    @Override
    public void delete(ActivityBudgetProgram object) {
        activityBudgetProgramDao.delete(object);
    }

    @Override
    public List list() {
        return activityBudgetProgramDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return activityBudgetProgramDao.listHQL(hql);
    }
 
    @Override
    public ActivityBudgetProgram getById(Long id) {
        return activityBudgetProgramDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return activityBudgetProgramDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return activityBudgetProgramDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return activityBudgetProgramDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return activityBudgetProgramDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return activityBudgetProgramDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return activityBudgetProgramDao.countRestrictions(listCriterion);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictionsVariant(List variant) {
        return activityBudgetProgramDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code,Long exception) {
        return activityBudgetProgramDao.existCode(code,exception);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return activityBudgetProgramDao.addRestrictionsVariant(variant);
    }

    @Override
    public Long nextId(Long id, String idName,boolean withDisabled) {
        return activityBudgetProgramDao.nextId(id,idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName,boolean withDisabled) {
        return activityBudgetProgramDao.previousId(id,idName, withDisabled);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return activityBudgetProgramDao.rowNumber(id, withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return activityBudgetProgramDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return activityBudgetProgramDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return activityBudgetProgramDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return activityBudgetProgramDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return activityBudgetProgramDao.getByHQL(hql,parameters);
    }

    @Override
    public List getListForAssigmentStrategicActivity(Integer strategicActivityId) {
        return activityBudgetProgramDao.getListForAssigmentStrategicActivity(strategicActivityId);    
    }

    @Override
    public List getListForGiveStrategicActivity(List<Long> idsForExclude) {
       return activityBudgetProgramDao.getListForGiveStrategicActivity(idsForExclude);    
    }

    @Override
    public List getListForAssigmentDependency(Integer dependencyId) {
       return activityBudgetProgramDao.getListForAssigmentDependency(dependencyId);    
    }

    @Override
    public List getListForGiveDependency(List<Long> idsForExclude) {
       return activityBudgetProgramDao.getListForGiveDependency(idsForExclude);    
    }

}


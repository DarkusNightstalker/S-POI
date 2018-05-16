/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.IGoalConfigurationDao;
import edu.unas.spoi.ppto.model.GoalConfiguration;
import edu.unas.spoi.ppto.service.interfac.IGoalConfigurationService;
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
 * The type Goal configuration service.
 *
 * @author CORE i7
 */
public class GoalConfigurationService implements IGoalConfigurationService {

    @Autowired
    @Qualifier("goalConfigurationDao")
    private IGoalConfigurationDao goalConfigurationDao;

    @Override
    public Serializable save(GoalConfiguration object) {
        return goalConfigurationDao.save(object);
    }

    @Override
    public void update(GoalConfiguration object) {
        goalConfigurationDao.update(object);
    }

    @Override
    public void saveOrUpdate(GoalConfiguration object) {
        goalConfigurationDao.saveOrUpdate(object);
    }

    @Override
    public void delete(GoalConfiguration object) {
        goalConfigurationDao.delete(object);
    }

    @Override
    public List list() {
        return goalConfigurationDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return goalConfigurationDao.listHQL(hql);
    }

    @Override
    public GoalConfiguration getById(Serializable id) {
        return goalConfigurationDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return goalConfigurationDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return goalConfigurationDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return goalConfigurationDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return goalConfigurationDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return goalConfigurationDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return goalConfigurationDao.countRestrictions(listCriterion);
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
        return goalConfigurationDao.addRestrictionsVariant(variant);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return goalConfigurationDao.addRestrictionsVariant(variant);
    }

    @Override
    public Serializable nextId(Serializable id, String idName,boolean withDisabled) {
        return goalConfigurationDao.nextId(id, idName,withDisabled);
    }

    @Override
    public Serializable previousId(Serializable id, String idName,boolean withDisabled) {
        return goalConfigurationDao.previousId(id, idName,withDisabled);
    }
    @Override
    public Number rowNumber(Serializable id, boolean withDisabled) {
        return goalConfigurationDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return goalConfigurationDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return goalConfigurationDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return goalConfigurationDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return goalConfigurationDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return goalConfigurationDao.getByHQL(hql,parameters);
    }
}


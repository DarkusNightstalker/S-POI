/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import edu.unas.spoi.oei.dao.interfac.IBudgetProgramDao;
import edu.unas.spoi.oei.model.BudgetProgram;
import edu.unas.spoi.oei.service.interfac.IBudgetProgramService;
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
 * The type Budget program service.
 *
 * @author CORE i7
 */
@Service("budgetProgramService")
public class BudgetProgramService implements IBudgetProgramService {

    @Autowired
    @Qualifier("budgetProgramDao")
    private IBudgetProgramDao budgetProgramDao;

    @Override
    public Serializable save(BudgetProgram object) {
        return budgetProgramDao.save(object);
    }

    @Override
    public void update(BudgetProgram object) {
        budgetProgramDao.update(object);
    }

    @Override
    public void saveOrUpdate(BudgetProgram object) {
        budgetProgramDao.saveOrUpdate(object);
    }

    @Override
    public void delete(BudgetProgram object) {
        budgetProgramDao.delete(object);
    }

    @Override
    public List list() {
        return budgetProgramDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return budgetProgramDao.listHQL(hql);
    }

    @Override
    public BudgetProgram getById(Integer id) {
        return budgetProgramDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return budgetProgramDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return budgetProgramDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return budgetProgramDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return budgetProgramDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return budgetProgramDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return budgetProgramDao.countRestrictions(listCriterion);
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
        return budgetProgramDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code, Integer exception) {
        return budgetProgramDao.existCode(code, exception);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return budgetProgramDao.addRestrictionsVariant(variant);
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return budgetProgramDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return budgetProgramDao.previousId(id, idName, withDisabled);
    }

    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return budgetProgramDao.rowNumber(id, withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return budgetProgramDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return budgetProgramDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return budgetProgramDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return budgetProgramDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return budgetProgramDao.getByHQL(hql,parameters);
    }
}

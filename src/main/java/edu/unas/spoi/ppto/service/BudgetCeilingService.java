/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.IBudgetCeilingDao;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Budget ceiling service.
 *
 * @author Jhoan Brayam
 */
@Service("budgetCeilingService")
public class BudgetCeilingService implements IBudgetCeilingService {

    @Autowired
    @Qualifier("budgetCeilingDao")
    private IBudgetCeilingDao budgetCeilingDao;

    @Override
    public Serializable save(BudgetCeiling object) {
        return budgetCeilingDao.save(object);
    }

    @Override
    public void update(BudgetCeiling object) {
        budgetCeilingDao.update(object);
    }

    @Override
    public void saveOrUpdate(BudgetCeiling object) {
        budgetCeilingDao.saveOrUpdate(object);
    }

    @Override
    public void delete(BudgetCeiling object) {
        budgetCeilingDao.delete(object);
    }

    @Override
    public List list() {
        return budgetCeilingDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return budgetCeilingDao.listHQL(hql);
    }

    @Override
    public BudgetCeiling getById(Long id) {
        return budgetCeilingDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return budgetCeilingDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return budgetCeilingDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return budgetCeilingDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return budgetCeilingDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return budgetCeilingDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return budgetCeilingDao.countRestrictions(listCriterion);
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
        return budgetCeilingDao.addRestrictionsVariant(variant);
    }

    @Override
    public List<BudgetCeiling> getBy(Integer dependencyId, Integer year) {
        return budgetCeilingDao.getBy(dependencyId, year);
    }

    @Override
    public Long nextId(Long id, String idName, boolean withDisabled) {
        return budgetCeilingDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName, boolean withDisabled) {
        return budgetCeilingDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return budgetCeilingDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return budgetCeilingDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return budgetCeilingDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return budgetCeilingDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return budgetCeilingDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return budgetCeilingDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return budgetCeilingDao.getByHQL(hql, parameters);
    }

    @Override
    public List getListNotEmptyFundingSourceBasicData(Integer dependencyId, Integer operationYear) {
        return budgetCeilingDao.getListNotEmptyFundingSourceBasicData(dependencyId, operationYear);
    }

    @Override
    public List<Map<String, Object>> getListBasicData(Integer dependencyId, Integer year) {
        return budgetCeilingDao.getListBasicData(dependencyId, year);
   }

    @Override
    public List<Classifier> getGenericClassifiers(Integer dependencyId, boolean withEmpty) {
       return budgetCeilingDao.getGenericClassifiers(dependencyId, withEmpty);
    }

}

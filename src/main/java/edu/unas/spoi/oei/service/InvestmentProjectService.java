/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import edu.unas.spoi.oei.dao.interfac.IInvestmentProjectDao;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.DependencyHasABP;
import edu.unas.spoi.oei.model.InvestmentProject;
import edu.unas.spoi.oei.service.interfac.IInvestmentProjectService;
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
 * The type Investment project service.
 *
 * @author Jhoan Brayam
 */
@Service("investmentProjectService")
public class InvestmentProjectService implements IInvestmentProjectService {

    @Autowired
    @Qualifier("investmentProjectDao")
    private IInvestmentProjectDao investmentProjectDao;

    @Override
    public Serializable save(InvestmentProject object) {
        return investmentProjectDao.save(object);
    }

    @Override
    public void update(InvestmentProject object) {
        investmentProjectDao.update(object);
    }

    @Override
    public void saveOrUpdate(InvestmentProject object) {
        investmentProjectDao.saveOrUpdate(object);
    }

    @Override
    public void delete(InvestmentProject object) {
        investmentProjectDao.delete(object);
    }

    @Override
    public List list() {
        return investmentProjectDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return investmentProjectDao.listHQL(hql);
    }

    @Override
    public InvestmentProject getById(Long id) {
        return investmentProjectDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return investmentProjectDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return investmentProjectDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return investmentProjectDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return investmentProjectDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return investmentProjectDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return investmentProjectDao.countRestrictions(listCriterion);
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
        return investmentProjectDao.addRestrictionsVariant(variant);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return investmentProjectDao.addRestrictionsVariant(variant);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return investmentProjectDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return investmentProjectDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public Long nextId(Long id, String idName, boolean withDisabled) {
        return  investmentProjectDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName, boolean withDisabled) {
        return  investmentProjectDao.previousId(id, idName, withDisabled);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return investmentProjectDao.rowNumber(id, withDisabled);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return investmentProjectDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return investmentProjectDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return investmentProjectDao.getByHQL(hql,parameters);
    }

}
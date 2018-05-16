/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.OrderFactory;
import edu.unas.spoi.oei.dao.interfac.ISpecificActivityDao;
import edu.unas.spoi.oei.model.SpecificActivity;
import edu.unas.spoi.oei.service.interfac.ISpecificActivityService;
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
 * The type Specific activity service.
 *
 * @author CORE i7
 */
@Service("specificActivityService")
public class SpecificActivityService implements ISpecificActivityService {

    @Autowired
    @Qualifier("specificActivityDao")
    private ISpecificActivityDao specificActivityDao;

    @Override
    public Serializable save(SpecificActivity object) {
        return specificActivityDao.save(object);
    }

    @Override
    public void update(SpecificActivity object) {
        specificActivityDao.update(object);
    }

    @Override
    public void saveOrUpdate(SpecificActivity object) {
        specificActivityDao.saveOrUpdate(object);
    }

    @Override
    public void delete(SpecificActivity object) {
        specificActivityDao.delete(object);
    }

    @Override
    public List list() {
        return specificActivityDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return specificActivityDao.listHQL(hql);
    }

    @Override
    public SpecificActivity getById(Integer id) {
        return specificActivityDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return specificActivityDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return specificActivityDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return specificActivityDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return specificActivityDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return specificActivityDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return specificActivityDao.countRestrictions(listCriterion);
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
        return specificActivityDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean validateCode(String code, Integer idSpecificGoal, Integer id) {
        return specificActivityDao.validateCode(code, idSpecificGoal, id);
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return specificActivityDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return specificActivityDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return specificActivityDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return specificActivityDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return specificActivityDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return specificActivityDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return specificActivityDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return specificActivityDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return specificActivityDao.getByHQL(hql, parameters);
    }

    @Override
    public Long countListForMainView(Integer strategicPlanId, String code, String name, Integer strategicAxisId, Integer strategicGoalId, Boolean active) {
        return specificActivityDao.countListForMainView(strategicPlanId, code, name, strategicAxisId, strategicGoalId, active);
    }

    @Override
    public List getListLazyForMainView(int page, int recordsPerPage, Integer strategicPlanId, String code, String name, Integer strategicAxisId, Integer strategicGoalId, Boolean active, OrderFactory orderFactory) {
        return specificActivityDao.getListLazyForMainView(page, recordsPerPage, strategicPlanId, code, name, strategicAxisId, strategicGoalId, active, orderFactory);
    }
}

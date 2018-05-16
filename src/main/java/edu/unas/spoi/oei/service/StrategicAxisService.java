/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.OrderFactory;
import edu.unas.spoi.oei.dao.interfac.IStrategicAxisDao;
import edu.unas.spoi.oei.model.StrategicAxis;
import edu.unas.spoi.oei.model.StrategicPlan;
import edu.unas.spoi.oei.service.interfac.IStrategicAxisService;
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
 * The type Strategic axis service.
 *
 * @author CORE i7
 */
@Service("strategicAxisService")
public class StrategicAxisService implements IStrategicAxisService {

    @Autowired
    @Qualifier("strategicAxisDao")
    private IStrategicAxisDao strategicAxisDao;

    @Override
    public Serializable save(StrategicAxis object) {
        return strategicAxisDao.save(object);
    }

    @Override
    public void update(StrategicAxis object) {
        strategicAxisDao.update(object);
    }

    @Override
    public void saveOrUpdate(StrategicAxis object) {
        strategicAxisDao.saveOrUpdate(object);
    }

    @Override
    public void delete(StrategicAxis object) {
        strategicAxisDao.delete(object);
    }

    @Override
    public List list() {
        return strategicAxisDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return strategicAxisDao.listHQL(hql);
    }

    @Override
    public StrategicAxis getById(Integer id) {
        return strategicAxisDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return strategicAxisDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return strategicAxisDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return strategicAxisDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return strategicAxisDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return strategicAxisDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return strategicAxisDao.countRestrictions(listCriterion);
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
        return strategicAxisDao.addRestrictionsVariant(variant);
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return strategicAxisDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return strategicAxisDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return strategicAxisDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return strategicAxisDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return strategicAxisDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return strategicAxisDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return strategicAxisDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return strategicAxisDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return strategicAxisDao.getByHQL(hql, parameters);
    }

    @Override
    public boolean validateCode(String code, Integer idStrategicPlan, Integer id) {
        return strategicAxisDao.validateCode(code, idStrategicPlan, id);
    }

    @Override
    public List getListLazyForMainView(int page, int rows, Integer year, String code, String name, OrderFactory orderFactory) {
         return strategicAxisDao.getListLazyForMainView(page,rows,year,code,name,orderFactory);
    }

    @Override
    public List getListBasicData(Integer strategicPlanId) {
        return strategicAxisDao.getListBasicData(strategicPlanId);
    }

}

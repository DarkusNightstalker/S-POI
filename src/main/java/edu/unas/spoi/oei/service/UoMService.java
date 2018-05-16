/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import edu.unas.spoi.oei.dao.interfac.IUoMDao;
import edu.unas.spoi.oei.model.UoM;
import edu.unas.spoi.oei.service.interfac.IUoMService;
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
 * The type Uo m service.
 *
 * @author CORE i7
 */
@Service("uomService")
public class UoMService implements IUoMService {

    @Autowired
    @Qualifier("uomDao")
    private IUoMDao uomDao;

    @Override
    public Serializable save(UoM object) {
        return uomDao.save(object);
    }

    @Override
    public void update(UoM object) {
        uomDao.update(object);
    }

    @Override
    public void saveOrUpdate(UoM object) {
        uomDao.saveOrUpdate(object);
    }

    @Override
    public void delete(UoM object) {
        uomDao.delete(object);
    }

    @Override
    public List list() {
        return uomDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return uomDao.listHQL(hql);
    }

    @Override
    public UoM getById(Integer id) {
        return uomDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return uomDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return uomDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return uomDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return uomDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return uomDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return uomDao.countRestrictions(listCriterion);
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
        return uomDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code, Integer exception) {
        return uomDao.existCode(code, exception);
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return uomDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return uomDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return uomDao.addRestrictionsVariant(variant);
    }
    
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return uomDao.rowNumber(id, withDisabled);
    }
    
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return uomDao.addRestrictionsVariant(rows,page,variant);
    }
    
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return uomDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return uomDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return uomDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return uomDao.getByHQL(hql,parameters);
    }
}

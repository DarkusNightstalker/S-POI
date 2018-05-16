/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.service;

import edu.unas.spoi.bne.dao.interfac.IBNeItemDao;
import edu.unas.spoi.bne.model.BNeItem;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
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
 * The type B ne item service.
 *
 * @author Jhoan Brayam
 */
@Service("bneItemService")
public class BNeItemService implements IBNeItemService {

    @Autowired
    @Qualifier("bneItemDao")
    private IBNeItemDao bneItemDao;

    @Override
    public Serializable save(BNeItem object) {
        return bneItemDao.save(object);
    }

    @Override
    public void update(BNeItem object) {
        bneItemDao.update(object);
    }

    @Override
    public void saveOrUpdate(BNeItem object) {
        bneItemDao.saveOrUpdate(object);
    }

    @Override
    public void delete(BNeItem object) {
        bneItemDao.delete(object);
    }

    @Override
    public List list() {
        return bneItemDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return bneItemDao.listHQL(hql);
    }

    @Override
    public BNeItem getById(Long id) {
        return bneItemDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return bneItemDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return bneItemDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return bneItemDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return bneItemDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return bneItemDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return bneItemDao.countRestrictions(listCriterion);
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
        return bneItemDao.addRestrictionsVariant(variant);
    }

    @Override
    public Long nextId(Long id, String idName, boolean withDisabled) {
        return bneItemDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName, boolean withDisabled) {
        return bneItemDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return bneItemDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return bneItemDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return bneItemDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return bneItemDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return bneItemDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return bneItemDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return bneItemDao.getByHQL(hql, parameters);
    }

    @Override
    public List<Map<String, Object>> getListFullData(Integer operationYear) {
        return bneItemDao.getListFullData(operationYear);
    }

    @Override
    public List<Map<String, Object>> getScheduledAmount(Integer dependencyId, Integer operationYear, Integer fundingSourceId, Long classifierId) {
        return bneItemDao.getScheduledAmount(dependencyId, operationYear, fundingSourceId, classifierId);
    }

    @Override
    public List<Map<String, Object>> getFundingSourceAmount(Integer dependencyId, Integer operationYear, Long classifierId) {
         return bneItemDao.getScheduledAmount(dependencyId, operationYear,  classifierId);
    }

}

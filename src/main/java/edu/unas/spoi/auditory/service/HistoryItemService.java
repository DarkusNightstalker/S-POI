/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.auditory.service;

import edu.unas.spoi.auditory.dao.interfac.IHistoryItemDao;
import edu.unas.spoi.auditory.model.HistoryItem;
import edu.unas.spoi.auditory.service.interfac.IHistoryItemService;
import edu.unas.spoi.bne.model.BNeItem;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The type History item service.
 *
 * @author CTIC
 */
public class HistoryItemService implements IHistoryItemService{
    
    @Autowired
    @Qualifier("historyItemDao")
    private IHistoryItemDao historyItemDao;

    @Override
    public Serializable save(HistoryItem object) {
        return historyItemDao.save(object);
    }

    @Override
    public void update(HistoryItem object) {
        historyItemDao.update(object);
    }

    @Override
    public void saveOrUpdate(HistoryItem object) {
        historyItemDao.saveOrUpdate(object);
    }

    @Override
    public void delete(HistoryItem object) {
        historyItemDao.delete(object);
    }

    @Override
    public List list() {
        return historyItemDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return historyItemDao.listHQL(hql);
    }
 
    @Override
    public HistoryItem getById(Long id) {
        return historyItemDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return historyItemDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return historyItemDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return historyItemDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return historyItemDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return historyItemDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return historyItemDao.countRestrictions(listCriterion);
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
        return historyItemDao.addRestrictionsVariant(variant);
    }
    @Override
    public Long nextId(Long id, String idName,boolean withDisabled) {
        return historyItemDao.nextId(id,idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName,boolean withDisabled) {
        return historyItemDao.previousId(id,idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return historyItemDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return historyItemDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return historyItemDao.addRestrictionsVariant(rows,page,variant);
    }


    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return historyItemDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return historyItemDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return historyItemDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return historyItemDao.getByHQL(hql,parameters);
    }
}

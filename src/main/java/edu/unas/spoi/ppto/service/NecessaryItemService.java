/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.INecessaryItemDao;
import edu.unas.spoi.ppto.model.NecessaryItem;
import edu.unas.spoi.ppto.service.interfac.INecessaryItemService;
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
 * The type Necessary item service.
 *
 * @author CORE i7
 */
@Service("necessaryItemService")
public class NecessaryItemService implements INecessaryItemService {

    @Autowired
    @Qualifier("necessaryItemDao")
    private INecessaryItemDao necessaryItemDao;

    @Override
    public Serializable save(NecessaryItem object) {
        return necessaryItemDao.save(object);
    }

    @Override
    public void update(NecessaryItem object) {
        necessaryItemDao.update(object);
    }

    @Override
    public void saveOrUpdate(NecessaryItem object) {
        necessaryItemDao.saveOrUpdate(object);
    }

    @Override
    public void delete(NecessaryItem object) {
        necessaryItemDao.delete(object);
    }

    @Override
    public List list() {
        return necessaryItemDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return necessaryItemDao.listHQL(hql);
    }

    @Override
    public NecessaryItem getById(Integer id) {
        return necessaryItemDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return necessaryItemDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return necessaryItemDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return necessaryItemDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return necessaryItemDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return necessaryItemDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return necessaryItemDao.countRestrictions(listCriterion);
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
        return necessaryItemDao.addRestrictionsVariant(variant);
    }

    @Override
    public String getNextCode(Long idClassifier) {
        return necessaryItemDao.getNextCode(idClassifier);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return necessaryItemDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return necessaryItemDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return necessaryItemDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return necessaryItemDao.rowNumber(id,withDisabled);
    }

    @Override
    public boolean existCode(String code) {
        return necessaryItemDao.existCode(code);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return necessaryItemDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return necessaryItemDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return necessaryItemDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return necessaryItemDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return necessaryItemDao.getByHQL(hql,parameters);
    }

}

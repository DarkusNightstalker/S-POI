/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.IClassifierDao;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.util.ErrorMessage;
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
 * The type Classifier service.
 *
 * @author CORE i7
 */
@Service("classifierService")
public class ClassifierService implements IClassifierService {

    @Autowired
    @Qualifier("classifierDao")
    private IClassifierDao classifierDao;

    @Override
    public Serializable save(Classifier object) {
        return classifierDao.save(object);
    }

    @Override
    public void update(Classifier object) {
        classifierDao.update(object);
    }

    @Override
    public void saveOrUpdate(Classifier object) {
        classifierDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Classifier object) {
        classifierDao.delete(object);
    }

    @Override
    public List list() {
        return classifierDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return classifierDao.listHQL(hql);
    }

    @Override
    public Classifier getById(Long id) {
        return classifierDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return classifierDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return classifierDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return classifierDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return classifierDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return classifierDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return classifierDao.countRestrictions(listCriterion);
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
        return classifierDao.addRestrictionsVariant(variant);
    }

    @Override
    public ErrorMessage verifyCode(String code, Integer year, Long exception) {
        return classifierDao.verifyCode(code, year, exception);
    }

    @Override
    public Classifier getByPath(String path) {
        return classifierDao.getByPath(path);
    }

    @Override
    public Classifier getParent(String path) {
        return classifierDao.getParent(path);
    }

    @Override
    public Object[] getParentInfo(String path) {
        return classifierDao.getParentInfo(path);
    }

    @Override
    public Classifier getGeneric(String path) {
        return classifierDao.getGeneric(path);
    }

    @Override
    public Long nextId(Long id, String idName, boolean withDisabled) {
        return classifierDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName, boolean withDisabled) {
        return classifierDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return classifierDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return classifierDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return classifierDao.addRestrictionsVariant(rows,page,variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return classifierDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return classifierDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return classifierDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return classifierDao.getByHQL(hql,parameters);
    }

    @Override
    public List<Classifier> getGenericClassifiers() {
        return classifierDao.getGenericClassifiers();
    }
}

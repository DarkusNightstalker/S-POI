/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.IClassifierTypeDao;
import edu.unas.spoi.ppto.model.ClassifierType;
import edu.unas.spoi.ppto.service.interfac.IClassifierTypeService;
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
 * The type Classifier type service.
 *
 * @author User
 */
@Service("classifierTypeService")
public class ClassifierTypeService implements IClassifierTypeService{
    @Autowired
    @Qualifier("classifierTypeDao")
    private IClassifierTypeDao classifierTypeDao;

    @Override
    public Serializable save(ClassifierType object) {
        return classifierTypeDao.save(object);
    }

    @Override
    public void update(ClassifierType object) {
        classifierTypeDao.update(object);
    }

    @Override
    public void saveOrUpdate(ClassifierType object) {
        classifierTypeDao.saveOrUpdate(object);
    }

    @Override
    public void delete(ClassifierType object) {
        classifierTypeDao.delete(object);
    }

    @Override
    public List list() {
        return classifierTypeDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return classifierTypeDao.listHQL(hql);
    }
 
    @Override
    public ClassifierType getById(Integer id) {
        return classifierTypeDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return classifierTypeDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return classifierTypeDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return classifierTypeDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return classifierTypeDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return classifierTypeDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return classifierTypeDao.countRestrictions(listCriterion);
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
        return classifierTypeDao.addRestrictionsVariant(variant);
    }

    @Override
    public List<Object[]> getForClassifiers(Integer year) {
        return   classifierTypeDao.getForClassifiers(year);
    }

    @Override
    public Integer getDigits(Integer id) {
        return classifierTypeDao.getDigits(id);
    }

    @Override
    public ClassifierType getByDigits(Integer codeDigit) {
        return classifierTypeDao.getByDigits(codeDigit);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return classifierTypeDao.nextId(id,idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return classifierTypeDao.previousId(id,idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return classifierTypeDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return classifierTypeDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return classifierTypeDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return classifierTypeDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return classifierTypeDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return classifierTypeDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return classifierTypeDao.getByHQL(hql,parameters);
    }
}

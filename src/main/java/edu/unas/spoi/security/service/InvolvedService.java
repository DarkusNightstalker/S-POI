/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IInvolvedDao;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.Person;
import edu.unas.spoi.security.model.User;
import edu.unas.spoi.security.service.interfac.IInvolvedService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Involved service.
 *
 * @author Jhoan Brayam
 */
@Service("involvedService")
public class InvolvedService implements IInvolvedService{
    
    @Autowired
    @Qualifier("involvedDao")
    private IInvolvedDao involvedDao;

    @Override
    public Integer save(Involved object) {
        return involvedDao.save(object);
    }

    @Override
    public void update(Involved object) {
        involvedDao.update(object);
    }

    @Override
    public void saveOrUpdate(Involved object) {
        involvedDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Involved object) {
        involvedDao.delete(object);
    }

    @Override
    public List list() {
        return involvedDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return involvedDao.listHQL(hql);
    }
 
    @Override
    public Involved getById(Integer id) {
        return involvedDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return involvedDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return involvedDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return involvedDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return involvedDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return involvedDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return involvedDao.countRestrictions(listCriterion);
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
        return involvedDao.addRestrictionsVariant(variant);
    }

    @Override
    public Involved getBy(Person person, Integer dependency) {
        return involvedDao.getBy(person, dependency);
    }

    @Override
    public List<Integer> getDependenciesAvailable(Person person) {
        return involvedDao.getDependenciesAvailable(person);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return involvedDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return involvedDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return involvedDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return involvedDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return involvedDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return involvedDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return involvedDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return involvedDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return involvedDao.getByHQL(hql,parameters);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IPersonDao;
import edu.unas.spoi.security.model.Person;
import edu.unas.spoi.security.service.interfac.IPersonService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Person service.
 *
 * @author Jhoan Brayam
 */
@Service("personService")
public class PersonService implements IPersonService{
    
    @Autowired
    @Qualifier("personDao")
    private IPersonDao personDao;

    @Override
    public Integer save(Person object) {
        return personDao.save(object);
    }

    @Override
    public void update(Person object) {
        personDao.update(object);
    }

    @Override
    public void saveOrUpdate(Person object) {
        personDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Person object) {
        personDao.delete(object);
    }

    @Override
    public List list() {
        return personDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return personDao.listHQL(hql);
    }
 
    @Override
    public Person getById(Integer id) {
        return personDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return personDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return personDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return personDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return personDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return personDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return personDao.countRestrictions(listCriterion);
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
        return personDao.addRestrictionsVariant(variant);
    }

    @Override
    public Person getBy(String dni) {
        return personDao.getBy(dni);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return personDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return personDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return personDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return personDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return personDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return personDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return personDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return personDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return personDao.getByHQL(hql,parameters);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IPermissionCategoryDao;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.PermissionCategory;
import edu.unas.spoi.security.model.Person;
import edu.unas.spoi.security.service.interfac.IPermissionCategoryService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Permission category service.
 *
 * @author Jhoan Brayam
 */
@Service("permissionCategoryService")
public class PermissionCategoryService implements IPermissionCategoryService{
    
    @Autowired
    @Qualifier("permissionCategoryDao")
    private IPermissionCategoryDao permissionCategoryDao;

    @Override
    public Integer save(PermissionCategory object) {
        return permissionCategoryDao.save(object);
    }

    @Override
    public void update(PermissionCategory object) {
        permissionCategoryDao.update(object);
    }

    @Override
    public void saveOrUpdate(PermissionCategory object) {
        permissionCategoryDao.saveOrUpdate(object);
    }

    @Override
    public void delete(PermissionCategory object) {
        permissionCategoryDao.delete(object);
    }

    @Override
    public List list() {
        return permissionCategoryDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return permissionCategoryDao.listHQL(hql);
    }
 
    @Override
    public PermissionCategory getById(Integer id) {
        return permissionCategoryDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return permissionCategoryDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return permissionCategoryDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return permissionCategoryDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return permissionCategoryDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return permissionCategoryDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return permissionCategoryDao.countRestrictions(listCriterion);
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
        return permissionCategoryDao.addRestrictionsVariant(variant);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return permissionCategoryDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return permissionCategoryDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return permissionCategoryDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return permissionCategoryDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return permissionCategoryDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return permissionCategoryDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return permissionCategoryDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return permissionCategoryDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return permissionCategoryDao.getByHQL(hql,parameters);
    }
}

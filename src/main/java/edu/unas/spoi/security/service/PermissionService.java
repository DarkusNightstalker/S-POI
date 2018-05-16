/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IPermissionDao;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import edu.unas.spoi.security.service.interfac.IPermissionService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Permission service.
 *
 * @author Jhoan Brayam
 */
@Service("permissionService")
public class PermissionService implements IPermissionService{
    
    @Autowired
    @Qualifier("permissionDao")
    private IPermissionDao permissionDao;

    @Override
    public Integer save(Permission object) {
        return permissionDao.save(object);
    }

    @Override
    public void update(Permission object) {
        permissionDao.update(object);
    }

    @Override
    public void saveOrUpdate(Permission object) {
        permissionDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Permission object) {
        permissionDao.delete(object);
    }

    @Override
    public List list() {
        return permissionDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return permissionDao.listHQL(hql);
    }
 
    @Override
    public Permission getById(Integer id) {
        return permissionDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return permissionDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return permissionDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return permissionDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return permissionDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return permissionDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return permissionDao.countRestrictions(listCriterion);
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
        return permissionDao.addRestrictionsVariant(variant);
    }

    @Override
    public List<Permission> getBy(PermissionCategory category) {
        return permissionDao.getBy(category);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return permissionDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return permissionDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return permissionDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return permissionDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return permissionDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return permissionDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return permissionDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return permissionDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return permissionDao.getByHQL(hql,parameters);
    }
}

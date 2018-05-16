/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IRolDao;
import edu.unas.spoi.security.model.Rol;
import edu.unas.spoi.security.service.interfac.IRolService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Rol service.
 *
 * @author Jhoan Brayam
 */
@Service("rolService")
public class RolService implements IRolService{
    
    @Autowired
    @Qualifier("rolDao")
    private IRolDao rolDao;

    @Override
    public Integer save(Rol object) {
        return rolDao.save(object);
    }

    @Override
    public void update(Rol object) {
        rolDao.update(object);
    }

    @Override
    public void saveOrUpdate(Rol object) {
        rolDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Rol object) {
        rolDao.delete(object);
    }

    @Override
    public List list() {
        return rolDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return rolDao.listHQL(hql);
    }
 
    @Override
    public Rol getById(Integer id) {
        return rolDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return rolDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return rolDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return rolDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return rolDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return rolDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return rolDao.countRestrictions(listCriterion);
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
        return rolDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean validateCode(String code,Integer id) {
         return rolDao.validateCode(code,id) ;
    }

    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return rolDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return rolDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return rolDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return rolDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return rolDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return rolDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return rolDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return rolDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return rolDao.getByHQL(hql,parameters);
    }

    @Override
    public List<Map<String, Object>> getPermissionCategoriesBasicData(Integer rolId) {
        return rolDao.getPermissionCategoriesBasicData(rolId);
    }

    @Override
    public List<Map<String, Object>> getPermissionsBasicData(Integer permissionCategoryId, Integer rolId) {
        return rolDao.getPermissionsBasicData(permissionCategoryId,rolId);
    }
}

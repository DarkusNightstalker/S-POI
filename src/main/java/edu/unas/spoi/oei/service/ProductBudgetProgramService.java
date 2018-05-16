/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import edu.unas.spoi.oei.dao.interfac.IProductBudgetProgramDao;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import edu.unas.spoi.oei.service.interfac.IProductBudgetProgramService;
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
 * The type Product budget program service.
 *
 * @author CORE i7
 */
@Service("productBudgetProgramService")
public class ProductBudgetProgramService implements IProductBudgetProgramService{
    
    @Autowired
    @Qualifier("productBudgetProgramDao")
    private IProductBudgetProgramDao productBudgetProgramDao;

    @Override
    public Serializable save(ProductBudgetProgram object) {
        return productBudgetProgramDao.save(object);
    }

    @Override
    public void update(ProductBudgetProgram object) {
        productBudgetProgramDao.update(object);
    }

    @Override
    public void saveOrUpdate(ProductBudgetProgram object) {
        productBudgetProgramDao.saveOrUpdate(object);
    }

    @Override
    public void delete(ProductBudgetProgram object) {
        productBudgetProgramDao.delete(object);
    }

    @Override
    public List list() {
        return productBudgetProgramDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return productBudgetProgramDao.listHQL(hql);
    }
 
    @Override
    public ProductBudgetProgram getById(Long id) {
        return productBudgetProgramDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return productBudgetProgramDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return productBudgetProgramDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return productBudgetProgramDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return productBudgetProgramDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return productBudgetProgramDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return productBudgetProgramDao.countRestrictions(listCriterion);
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
        return productBudgetProgramDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code,Long exception) {
        return productBudgetProgramDao.existCode(code,exception);
    }

    @Override
    public Long nextId(Long id, String idName,boolean withDisabled) {
        return productBudgetProgramDao.nextId(id,idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName,boolean withDisabled) {
        return productBudgetProgramDao.previousId(id,idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return productBudgetProgramDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return productBudgetProgramDao.rowNumber(id, withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return productBudgetProgramDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return productBudgetProgramDao.countRestrictions(criterionList,aliasList);
    }
    @Override
    public List listHQL(String hql, Object... parameters) {
        return productBudgetProgramDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return productBudgetProgramDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return productBudgetProgramDao.getByHQL(hql,parameters);
    }
}
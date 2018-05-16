/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import edu.unas.spoi.poi.dao.interfac.IPOIDao;
import edu.unas.spoi.poi.dao.interfac.IPOIScheduleDao;
import edu.unas.spoi.poi.model.POI;
import edu.unas.spoi.poi.service.interfac.IPOIService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Poi service.
 *
 * @author Jhoan Brayam
 */
@Service("poiService")
public class POIService implements IPOIService {

    @Autowired
    @Qualifier("poiDao")
    private IPOIDao poiDao;
    @Autowired
    @Qualifier("poiScheduleDao")
    private IPOIScheduleDao poiScheduleDao;

    @Override
    public Serializable save(POI object) {
        return poiDao.save(object);
    }

    @Override
    public void update(POI object) {
        poiDao.update(object);
    }

    @Override
    public void saveOrUpdate(POI object) {
        poiDao.saveOrUpdate(object);
    }

    @Override
    public void delete(POI object) {
        poiDao.delete(object);
    }

    @Override
    public List list() {
        return poiDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return poiDao.listHQL(hql);
    }

    @Override
    public POI getById(Long id) {
        return poiDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return poiDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return poiDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return poiDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return poiDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return poiDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return poiDao.countRestrictions(listCriterion);
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
        return poiDao.addRestrictionsVariant(variant);
    }

    @Override
    public Long nextId(Long id, String idName, boolean withDisabled) {
        return poiDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName, boolean withDisabled) {
        return poiDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return poiDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return poiDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return poiDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return poiDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return poiDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return poiDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return poiDao.getByHQL(hql, parameters);
    }

    @Override
    public POI getBy(Integer operationYear, Integer dependencyId) {
        return poiDao.getBy(operationYear, dependencyId);
    }

    @Override
    public List<Map<String, Object>> getListFullData(Integer operationYear) {
        List<Map<String, Object>> result = poiDao.getListFullData(operationYear);
        return result;
    }
}

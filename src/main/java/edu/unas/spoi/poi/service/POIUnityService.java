/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import edu.unas.spoi.poi.dao.interfac.IPOIDao;
import edu.unas.spoi.poi.dao.interfac.IPOIUnityDao;
import edu.unas.spoi.poi.model.POI;
import edu.unas.spoi.poi.model.POIUnity;
import edu.unas.spoi.poi.service.interfac.IPOIUnityService;
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
 * The type Poi unity service.
 *
 * @author Jhoan Brayam
 */
@Service("poiUnityService")
public class POIUnityService implements IPOIUnityService {

    @Autowired
    @Qualifier("poiUnityDao")
    private IPOIUnityDao poiUnityDao;

    @Override
    public Serializable save(POIUnity object) {
        return poiUnityDao.save(object);
    }

    @Override
    public void update(POIUnity object) {
        poiUnityDao.update(object);
    }

    @Override
    public void saveOrUpdate(POIUnity object) {
        poiUnityDao.saveOrUpdate(object);
    }

    @Override
    public void delete(POIUnity object) {
        poiUnityDao.delete(object);
    }

    @Override
    public List list() {
        return poiUnityDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return poiUnityDao.listHQL(hql);
    }

    @Override
    public POIUnity getById(Integer id) {
        return poiUnityDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return poiUnityDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return poiUnityDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return poiUnityDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return poiUnityDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return poiUnityDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return poiUnityDao.countRestrictions(listCriterion);
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
        return poiUnityDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code, Integer id) {
        return poiUnityDao.existCode(code,id);
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return poiUnityDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return poiUnityDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return poiUnityDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return poiUnityDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return poiUnityDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return poiUnityDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return poiUnityDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return poiUnityDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return poiUnityDao.getByHQL(hql,parameters);
    }
}

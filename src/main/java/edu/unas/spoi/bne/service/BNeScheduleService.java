/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.service;

import edu.unas.spoi.bne.dao.interfac.IBNeScheduleDao;
import edu.unas.spoi.bne.model.BNeSchedule;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeScheduleService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type B ne schedule service.
 *
 * @author Jhoan Brayam
 */
@Service("bneScheduleService")
public class BNeScheduleService implements IBNeScheduleService{
    
    @Autowired
    @Qualifier("bneScheduleDao")
    private IBNeScheduleDao bneScheduleDao;

    @Override
    public Serializable save(BNeSchedule object) {
        return bneScheduleDao.save(object);
    }

    @Override
    public void update(BNeSchedule object) {
        bneScheduleDao.update(object);
    }

    @Override
    public void saveOrUpdate(BNeSchedule object) {
        bneScheduleDao.saveOrUpdate(object);
    }

    @Override
    public void delete(BNeSchedule object) {
        bneScheduleDao.delete(object);
    }

    @Override
    public List list() {
        return bneScheduleDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return bneScheduleDao.listHQL(hql);
    }
 
    @Override
    public BNeSchedule getById(Long id) {
        return bneScheduleDao.getById(id);
    }
    
    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return bneScheduleDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return bneScheduleDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return bneScheduleDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return bneScheduleDao.updateHQL(hql);
    }
    
    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return bneScheduleDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return bneScheduleDao.countRestrictions(listCriterion);
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
        return bneScheduleDao.addRestrictionsVariant(variant);
    }
    @Override
    public Long nextId(Long id, String idName,boolean withDisabled) {
        return bneScheduleDao.nextId(id,idName, withDisabled);
    }

    @Override
    public Long previousId(Long id, String idName,boolean withDisabled) {
        return bneScheduleDao.previousId(id,idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return bneScheduleDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Long id, boolean withDisabled) {
        return bneScheduleDao.rowNumber(id, withDisabled);
    }

    @Override
    public BNeSchedule getBy(Month month, Long id) {
        return bneScheduleDao.getBy(month, id);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return bneScheduleDao.addRestrictionsVariant(rows,page,variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return bneScheduleDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return bneScheduleDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return bneScheduleDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return bneScheduleDao.getByHQL(hql,parameters);
    }

    public List<Map<String, Object>> getListBneSchedulesBasicData(Long bneItemId) {
         return bneScheduleDao.getListBneSchedulesBasicData(bneItemId);
    }
}
